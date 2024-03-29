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

package me.dickmeister.mcprotocol.network.packet.impl.status.server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.auth.GameProfile;
import me.dickmeister.mcprotocol.minecraft.status.PlayerInfo;
import me.dickmeister.mcprotocol.minecraft.status.ServerStatusInfo;
import me.dickmeister.mcprotocol.minecraft.status.VersionInfo;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.Arrays;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND, connectionState = ConnectionState.STATUS)
public class ServerStatusResponsePacket extends Packet {
    private static final Gson gson = new GsonBuilder().create();
    private ServerStatusInfo statusInfo;

    {
        this.setId(0x00);
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        final JsonObject jsonObject = new JsonObject();
        final JsonObject version = new JsonObject();

        String versionName = statusInfo.getVersion().getName();
        version.addProperty("name", versionName);
        version.addProperty("protocol", statusInfo.getVersion().getProtocol());
        final JsonObject players = new JsonObject();
        players.addProperty("max", statusInfo.getPlayers().getMaxPlayers());
        players.addProperty("online", statusInfo.getPlayers().getOnlinePlayers());
        if (statusInfo.getPlayers().getPlayers().length > 0) {
            final JsonArray array = new JsonArray();
            Arrays.stream(statusInfo.getPlayers().getPlayers()).forEach(gameProfile -> {
                final JsonObject jsonObject1 = new JsonObject();
                jsonObject1.addProperty("name", gameProfile.getUsername());
                jsonObject1.addProperty("id", gameProfile.getUuid().toString());
                array.add(jsonObject1);
            });
            players.add("sample", array);
        }
        jsonObject.add("version", version);
        jsonObject.add("players", players);
        jsonObject.add("description", gson.fromJson(ComponentSerializer.toString(statusInfo.getDescription()), JsonObject.class));
        if (Objects.nonNull(statusInfo.getIcon()))
            jsonObject.addProperty("favicon", statusInfo.getIcon());

        packetBuffer.writeString(jsonObject.toString());
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        final JsonObject jsonObject = gson.fromJson(packetBuffer.readStringFromBuffer(32767), JsonObject.class);
        final JsonObject version = jsonObject.get("version").getAsJsonObject();
        final VersionInfo versionInfo = new VersionInfo(version.get("name").getAsString(), version.get("protocol").getAsInt());
        final JsonObject players = jsonObject.get("players").getAsJsonObject();

        GameProfile[] gameProfiles = new GameProfile[0];
        if (players.has("sample")) {
            final JsonArray profiles = players.get("sample").getAsJsonArray();
            if (profiles.size() > 0) {
                gameProfiles = new GameProfile[profiles.size()];
                for (int index = 0; index < profiles.size(); index++) {
                    final JsonObject jsonObject1 = profiles.get(index).getAsJsonObject();
                    gameProfiles[index] = new GameProfile(jsonObject1.get("name").getAsString(), UUID.fromString(jsonObject1.get("id").getAsString()));
                }
            }
        }

        final PlayerInfo playerInfo = new PlayerInfo(players.get("online").getAsInt(), players.get("max").getAsInt(), gameProfiles);
        final BaseComponent[] description = ComponentSerializer.parse(jsonObject.get("description").toString());
        String icon = null;
        if (jsonObject.has("favicon"))
            icon = jsonObject.get("favicon").getAsString();

        statusInfo = new ServerStatusInfo(versionInfo, playerInfo, description, icon);
    }
}
