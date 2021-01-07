package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerPlayerListHeaderFooterPacket extends Packet
{
    {
        this.setId(0x4A);
    }

    private BaseComponent[] header, footer;

    public ServerPlayerListHeaderFooterPacket(String header, String footer){
        this.header = new BaseComponent[]{new TextComponent(header)};
        this.footer = new BaseComponent[]{new TextComponent(footer)};
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws Exception {
        packetBuffer.writeString(ComponentSerializer.toString(header));
        packetBuffer.writeString(ComponentSerializer.toString(footer));
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws Exception {
        header = ComponentSerializer.parse(packetBuffer.readStringFromBuffer(32767));
        footer = ComponentSerializer.parse(packetBuffer.readStringFromBuffer(32767));
    }
}
