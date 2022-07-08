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