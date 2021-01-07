package me.dickmeister.mcprotocol.network.packet.impl.play.server;

import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.bossbar.BossBarAction;
import me.dickmeister.mcprotocol.minecraft.bossbar.BossBarColor;
import me.dickmeister.mcprotocol.minecraft.bossbar.BossBarDivision;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import me.dickmeister.mcprotocol.util.StringUtil;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.UUID;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Data
@NoArgsConstructor
@Packet.PacketInfo(connectionState = ConnectionState.PLAY,packetDirection = PacketDirection.CLIENTBOUND)
public class ServerBossBarPacket extends Packet
{
    {
        this.setId(0x0C);
    }

    private UUID uuid;
    private BossBarAction action;

    private BaseComponent[] title;
    private float health;
    private BossBarColor color;
    private BossBarDivision division;
    private boolean darkenSky;
    private boolean dragonBar;

    public ServerBossBarPacket(UUID uuid, String title, float health, BossBarColor color, BossBarDivision division, boolean darkenSky, boolean dragonBar) {
        this.uuid = uuid;
        this.action = BossBarAction.ADD;

        this.title = new BaseComponent[] { new TextComponent(StringUtil.fixColor(title)) };
        this.health = health;
        this.color = color;
        this.division = division;
        this.darkenSky = darkenSky;
        this.dragonBar = dragonBar;
    }

    public ServerBossBarPacket(UUID uuid) {
        this.uuid = uuid;
        this.action = BossBarAction.REMOVE;
    }

    public ServerBossBarPacket(UUID uuid, float health) {
        this.uuid = uuid;
        this.action = BossBarAction.UPDATE_HEALTH;

        this.health = health;
    }

    public ServerBossBarPacket(UUID uuid, String title) {
        this.uuid = uuid;
        this.action = BossBarAction.UPDATE_TITLE;

        this.title = new BaseComponent[] { new TextComponent(StringUtil.fixColor(title)) };
    }

    public ServerBossBarPacket(UUID uuid, BossBarColor color, BossBarDivision division) {
        this.uuid = uuid;
        this.action = BossBarAction.UPDATE_STYLE;

        this.color = color;
        this.division = division;
    }

    public ServerBossBarPacket(UUID uuid, boolean darkenSky, boolean dragonBar) {
        this.uuid = uuid;
        this.action = BossBarAction.UPDATE_FLAGS;

        this.darkenSky = darkenSky;
        this.dragonBar = dragonBar;
    }

    @Override
    public void write(PacketBuffer out) throws Exception {
        out.writeUuid(this.uuid);
        out.writeVarIntToBuffer(action.getId());

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_TITLE) {
            out.writeString(ComponentSerializer.toString(title));
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_HEALTH) {
            out.writeFloat(health);
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_STYLE) {
            out.writeVarIntToBuffer(color.getId());
            out.writeVarIntToBuffer(division.getId());
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_FLAGS) {
            int flags = 0;
            if (darkenSky)
                flags |= 0x1;

            if (dragonBar)
                flags |= 0x2;

            out.writeByte(flags);
        }
    }

    @Override
    public void read(PacketBuffer in) throws Exception {}
}
