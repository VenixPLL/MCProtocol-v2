/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.dickmeister.mcprotocol.network.objects;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import me.dickmeister.mcprotocol.MCProtocol;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.codec.NettyCompressionCodec;
import me.dickmeister.mcprotocol.network.netty.codec.NettyEncryptionCodec;
import me.dickmeister.mcprotocol.network.netty.codec.NettyPacketCodec;
import me.dickmeister.mcprotocol.network.netty.encryption.CryptManager;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.network.packet.impl.login.server.ServerLoginDisconnectPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.client.ClientChatPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.server.ServerChatPacket;
import me.dickmeister.mcprotocol.network.packet.impl.play.server.ServerDisconnectPacket;
import me.dickmeister.mcprotocol.util.StringUtil;
import me.dickmeister.viaversion.IOPipelineName;
import me.dickmeister.viaversion.ViaClient;
import me.dickmeister.viaversion.managers.ViaHostnameProtocol;
import me.dickmeister.viaversion.netty.VRClientSideUserConnection;
import me.dickmeister.viaversion.netty.client.IOViaDecode;
import me.dickmeister.viaversion.netty.client.IOViaEncode;
import me.dickmeister.viaversion.netty.server.IOViaServerDecode;
import me.dickmeister.viaversion.netty.server.IOViaServerEncode;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.protocol.ProtocolPipeline;

import javax.crypto.SecretKey;
import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
public class Session {

    /**
     * Made to help patch future Errors and bugs.
     * If debug is disabled there is no reason to print entire StackTrace of an Exceptions.
     */
    private final ChannelFutureListener futureListener = MCProtocol.DEBUG ?
            ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE : ChannelFutureListener.CLOSE_ON_FAILURE;

    private final Channel channel;
    private ViaClient client;
    private String nickName;
    private UserConnection info;

    /**
     * Changing compression threshold or enabling compression if not enabled.
     * Wihout changing CompressionThreshold you cannot "Play" on Servers with enabled compression
     * <p>
     * When enabling compression on Client the server will send ServerLoginSetCompressionPacket with threshold.
     * When enabling compression on Server you have to send ServerLoginSetCompressionPacket to target Session *(In LOGIN ConnectionState)
     *
     * @param threshold Maximum size of a packet before it is compressed.
     */
    public final void setCompressionThreshold(final int threshold) {
        if (Objects.isNull(channel.pipeline().get("compressionCodec"))) {

            if (this.channel.pipeline().get(IOPipelineName.VIA_HANDLER_ENCODER_NAME) == null) {
                this.channel.pipeline().addBefore(IOPipelineName.PACKET_CODEC, IOPipelineName.PACKET_COMPRESSION, new NettyCompressionCodec(threshold));
            } else {
                this.channel.pipeline().addBefore(IOPipelineName.VIA_HANDLER_ENCODER_NAME, IOPipelineName.PACKET_COMPRESSION, new NettyCompressionCodec(threshold));
            }

            return;
        }

        ((NettyCompressionCodec) channel.pipeline().get("compressionCodec"))
                .setCompressionThreshold(threshold);

    }

    /**
     * Enabling ViaVersion multiProtocol and crossversion support;
     * Permanent for Session.
     *
     * @param clientSide Direction of the Session.
     */
    public void enableViaVersion(final boolean clientSide) {
        if (!MCProtocol.VIA_ENABLED) {
            throw new IllegalStateException("ViaVersion was not initialized! Enable ViaVersion using McProtocol.initialize");
        }

        if (clientSide) {
            final UserConnection userConnection = new VRClientSideUserConnection(this.channel);

            if (this.client == null) {
                client = new ViaClient(userConnection.getId());
                client.initFabric();
            } else {
                client.updateID(userConnection.getId());
            }

            new ProtocolPipeline(userConnection).add(ViaHostnameProtocol.INSTANCE);
            this.channel.pipeline()
                    .addBefore(IOPipelineName.PACKET_CODEC, IOPipelineName.VIA_HANDLER_ENCODER_NAME, new IOViaEncode(userConnection))
                    .addBefore(IOPipelineName.PACKET_CODEC, IOPipelineName.VIA_HANDLER_DECODER_NAME, new IOViaDecode(userConnection));

            return;
        }

        info = new UserConnection(this.channel, false);
        new ProtocolPipeline(info);
        this.channel.pipeline().addBefore(IOPipelineName.PACKET_CODEC, IOPipelineName.VIA_HANDLER_ENCODER_NAME, new IOViaServerEncode(info));
        this.channel.pipeline().addBefore(IOPipelineName.PACKET_CODEC, IOPipelineName.VIA_HANDLER_DECODER_NAME, new IOViaServerDecode(info));
    }

    /**
     * Enabling encryption, used by Minecraft Premium sessions to Connect to Premium servers.
     *
     * @param key SecretKey provided by the Server
     */
    public final void enableEncryption(final SecretKey key) {
        channel.pipeline().addBefore("frameCodec", "encryptionDecoder", new NettyEncryptionCodec(
                CryptManager.createNetCipherInstance(1, key), CryptManager.createNetCipherInstance(2, key)));
    }

    /**
     * Getting active connectionState used by Session
     *
     * @return ConnectionState used by Session
     */
    public final ConnectionState getConnectionState() {
        return ((NettyPacketCodec) channel.pipeline().get("packetCodec"))
                .getConnectionState();
    }

    /**
     * Changing connectionState on active channel
     * Wihout changing ConnectionState you cannot receive packets outside base ConnectionState
     *
     * @param connectionState Connection state to set.
     */
    public final void setConnectionState(final ConnectionState connectionState) {
        ((NettyPacketCodec) channel.pipeline().get("packetCodec")).setConnectionState(connectionState);
    }

    /**
     * Getting PacketDirection on which the Session operates
     *
     * @return PacketDirection used by Session
     */
    public final PacketDirection getPacketDirection() {
        return ((NettyPacketCodec) channel.pipeline().get("packetCodec"))
                .getPacketDirection();
    }

    /**
     * Sending a packet to target Session
     * <p>
     * Session can be a Player or a Server
     * <p>
     * Ex: Client has a Session representation so Packets will flow like Session -> Server<p>
     * Ex: Server has a Session representation so Packets will flow like Session -> Player
     *
     * @param packet Packet to send
     */
    public final void sendPacket(@NonNull final Packet packet) {
        this.channel.writeAndFlush(packet).addListener(this.futureListener);
    }

    /**
     * Raw data send through Channel, bypassing all Netty Codecs except framing(sizer)
     *
     * @param buffer        Buffer with data to send
     * @param currentThread Thread to send if false it will send data with Channel eventLoop
     */
    public void sendRawPacket(final ByteBuf buffer, boolean currentThread) {
        if (currentThread) {
            this.channel.pipeline().context("frameCodec").writeAndFlush(buffer);
            return;
        }

        this.channel.eventLoop().submit(() -> {
            this.channel.pipeline().context("frameCodec").writeAndFlush(buffer);
        });
    }

    /**
     * Disconnecting Session with reason. only works if PacketDirection encoded in PacketCodec is SERVERBOUND
     *
     * @param reason Reason why client was disconnected. Leave blank if no reason
     */
    public void disconnect(String reason) {
        if (this.getPacketDirection() == PacketDirection.CLIENTBOUND)
            throw new IllegalStateException("Cannot send disconnect packet to Server. try close()");

        switch (this.getConnectionState()) {
            case LOGIN:
                sendPacket(new ServerLoginDisconnectPacket(reason));
                break;
            case PLAY:
                sendPacket(new ServerDisconnectPacket(reason));
                break;
            default:
                close();
        }
    }

    /**
     * Disconnecting Session without reason. only works if PacketDirection encoded in PacketCodec is SERVERBOUND
     */
    public void disconnect() {
        this.disconnect("");
    }

    public void sendMessage(String message) {
        if (this.getPacketDirection() == PacketDirection.CLIENTBOUND) {
            sendPacket(new ClientChatPacket(message));
            return;
        }

        sendPacket(new ServerChatPacket(StringUtil.fixColor(message))); // ChatUtil?
    }

    /**
     * Raw channel closing wihout any packets or information
     */
    public void close() {
        if (channel.isActive()) {
            channel.close();
        }
    }
}