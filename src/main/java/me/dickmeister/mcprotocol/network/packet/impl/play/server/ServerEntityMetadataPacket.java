package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.datasync.EntityDataManager;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import java.util.List;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ServerEntityMetadataPacket extends Packet
{
    {
        this.setId(0x3C);
    }

    private int entityId;
    private List< EntityDataManager.DataEntry<?>> dataManagerEntries;

    public ServerEntityMetadataPacket(int entityId, EntityDataManager dataManager, boolean sendAll) {
        this.entityId = entityId;

        if (sendAll) {
            this.dataManagerEntries = dataManager.getAll();
            dataManager.setClean();
        } else {
            this.dataManagerEntries = dataManager.getDirty();
        }
    }

    @Override
    public void read(PacketBuffer buffer) throws Exception {
        entityId = buffer.readVarIntFromBuffer();
        dataManagerEntries = EntityDataManager.readEntries(buffer);
    }

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        EntityDataManager.writeEntries(dataManagerEntries, buffer);
    }
}