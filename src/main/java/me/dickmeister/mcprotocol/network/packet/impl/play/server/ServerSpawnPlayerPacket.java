package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.auth.GameProfile;
import me.dickmeister.mcprotocol.minecraft.datasync.Entity;
import me.dickmeister.mcprotocol.minecraft.datasync.EntityDataManager;
import me.dickmeister.mcprotocol.minecraft.item.ItemStack;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import nl.matsv.viabackwards.api.entities.storage.EntityData;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ServerSpawnPlayerPacket extends Packet {

    {
        this.setId(0x05);
    }

    private List< EntityDataManager.DataEntry<?>> dataManagerEntries;

    private Entity entity;
    private int entityId;
    private UUID playerId;
    private double x, y, z;
    private byte yaw, pitch;
    private int currentItem;

    public ServerSpawnPlayerPacket(int entityID, GameProfile gameProfile, double posX, double posY, double posZ, float rotationYaw, float rotationPitch, ItemStack itemInHand) {
        entity = new Entity();
        entityId = entityID;
        playerId = gameProfile.getUuid();
        x = posX * 32.0D;
        y = posY * 32.0D;
        z = posZ * 32.0D;
        yaw = (byte)((int)(rotationYaw * 256.0F / 360.0F));
        pitch = (byte)((int)(rotationPitch * 256.0F / 360.0F));
        currentItem = Objects.isNull(itemInHand) ? 0 : itemInHand.getId();
        entity.init();
        dataManagerEntries = entity.getDataManager().getDirty();
    }

    @Override
    public void read(PacketBuffer buffer) throws Exception {}

    @Override
    public void write(PacketBuffer buffer) throws Exception {
        buffer.writeVarIntToBuffer(entityId);
        buffer.writeUuid(playerId);
        buffer.writeDouble(x / 32.0D);
        buffer.writeDouble(y / 32.0D);
        buffer.writeDouble(z / 32.0D);
        buffer.writeByte((byte)(yaw * 360 / 256.0F));
        buffer.writeByte((byte) (pitch * 360 / 256.0F));

        EntityDataManager.writeEntries(dataManagerEntries, buffer);
    }
}