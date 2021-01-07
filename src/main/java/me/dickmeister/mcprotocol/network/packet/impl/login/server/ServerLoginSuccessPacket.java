package me.dickmeister.mcprotocol.network.packet.impl.login.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.LOGIN, packetDirection = PacketDirection.CLIENTBOUND)
public class ServerLoginSuccessPacket extends Packet {

    private UUID uuid;
    private String username;

    {
        this.setId(0x02);
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeString(uuid.toString());
        out.writeString(username);
    }

    @Override
    public void read(PacketBuffer in) throws Exception {
        uuid = UUID.fromString(in.readStringFromBuffer(86));
        username = in.readStringFromBuffer(32);
    }
}
