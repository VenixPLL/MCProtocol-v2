package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.chat.MessagePosition;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Data
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerChatPacket extends Packet {
    {
        this.setId(0x0F);
    }

    public ServerChatPacket(String message) {
        this(message, MessagePosition.CHATBOX);
    }

    public ServerChatPacket(BaseComponent[] components, MessagePosition position) {
        this.message = components;
        this.position = position;
    }

    public ServerChatPacket(String message, MessagePosition position) {
        this.message = new BaseComponent[] { new TextComponent(message) };
        this.position = position;
    }

    private BaseComponent[] message;
    private MessagePosition position;

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(ComponentSerializer.toString(message));
        out.writeByte(position.getId());
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        message = ComponentSerializer.parse(in.readStringFromBuffer(Short.MAX_VALUE));
        position = MessagePosition.getById(in.readByte());
    }

    public String getMessage(){
        return message.length > 0 ? message[0].toPlainText() : "unknown reason";
    }
}
