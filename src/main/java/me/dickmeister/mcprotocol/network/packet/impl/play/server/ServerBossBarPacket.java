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
    public void read(PacketBuffer in) throws Exception {
        this.uuid = in.readUuid();
        this.action = BossBarAction.values()[in.readVarIntFromBuffer()];

        if(this.action == BossBarAction.ADD || action == BossBarAction.UPDATE_TITLE){
            this.title = ComponentSerializer.parse(in.readStringFromBuffer(32767));
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_HEALTH) {
            this.health = in.readFloat();
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_STYLE) {
            this.color = BossBarColor.values()[in.readVarIntFromBuffer()];
            this.division = BossBarDivision.values()[in.readVarIntFromBuffer()];
        }

        if (action == BossBarAction.ADD || action == BossBarAction.UPDATE_FLAGS) {
            var flags = in.readByte();
            darkenSky = (flags & 0x1) > 0;
            dragonBar = (flags & 0x2) > 0;
        }

    }
}
