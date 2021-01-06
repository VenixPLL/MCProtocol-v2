package me.dickmeister.mcprotocol.network.packet.impl.login.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.netty.encryption.CryptManager;
import me.dickmeister.mcprotocol.network.packet.Packet;

import java.security.PublicKey;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.LOGIN,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerLoginEncryptionRequestPacket extends Packet {

    {
        this.setId(0x01);
    }

    private String hashedServerId;
    private PublicKey publicKey;
    private byte[] verifyToken;

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(this.hashedServerId);
        out.writeByteArray(this.publicKey.getEncoded());
        out.writeByteArray(this.verifyToken);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        this.hashedServerId = in.readStringFromBuffer(20);
        this.publicKey = CryptManager.decodePublicKey(in.readByteArray());
        this.verifyToken = in.readByteArray();
    }
}
