package me.dickmeister.mcprotocol.network.packet.impl.play.server;


import com.mojang.authlib.properties.Property;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.dickmeister.mcprotocol.minecraft.Gamemode;
import me.dickmeister.mcprotocol.minecraft.auth.GameProfile;
import me.dickmeister.mcprotocol.minecraft.playerlist.PlayerListEntry;
import me.dickmeister.mcprotocol.minecraft.playerlist.PlayerListEntryAction;
import me.dickmeister.mcprotocol.network.ConnectionState;
import me.dickmeister.mcprotocol.network.PacketDirection;
import me.dickmeister.mcprotocol.network.netty.PacketBuffer;
import me.dickmeister.mcprotocol.network.packet.Packet;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Packet.PacketInfo(packetDirection = PacketDirection.CLIENTBOUND,connectionState = ConnectionState.PLAY)
public class ServerPlayerListEntryPacket extends Packet {

    {
        this.setId(0x2E);
    }

    private PlayerListEntryAction action;
    private PlayerListEntry[] entries;

    public PlayerListEntryAction getAction() {
        return action;
    }

    public PlayerListEntry[] getEntries() {
        return entries;
    }

    public void read(PacketBuffer in) throws Exception {
        try {
            action = PlayerListEntryAction.getById(in.readVarIntFromBuffer());
            entries = new PlayerListEntry[in.readVarIntFromBuffer()];
            for (int count = 0; count < entries.length; count++) {
                BaseComponent[] disp;
                int png;
                Gamemode mode;
                BaseComponent[] displayName;
                int ping;
                Gamemode gameMode;
                int index, properties;
                GameProfile profile;
                UUID uuid = in.readUuid();

                if (action == PlayerListEntryAction.ADD_PLAYER) {
                    profile = new GameProfile(in.readStringFromBuffer(32767), uuid);
                } else {
                    profile = new GameProfile(null, uuid);
                }

                PlayerListEntry entry = null;
                switch (action) {
                    case ADD_PLAYER:
                        properties = in.readVarIntFromBuffer();
                        for (index = 0; index < properties; index++) {
                            String propertyName = in.readStringFromBuffer(32767);
                            String value = in.readStringFromBuffer(32767);
                            String signature = null;
                            if (in.readBoolean()) {
                                signature = in.readStringFromBuffer(32767);
                            }

                            profile.getProperties().put(propertyName, new Property(propertyName, value, signature));
                        }

                        gameMode = Gamemode.getById(in.readVarIntFromBuffer());
                        ping = in.readVarIntFromBuffer();
                        displayName = null;
                        if (in.readBoolean()) {
                            displayName = ComponentSerializer.parse(in.readStringFromBuffer(32767));
                        }

                        entry = new PlayerListEntry(profile, gameMode, ping, displayName);
                        break;
                    case UPDATE_GAMEMODE:
                        mode = Gamemode.getById(in.readVarIntFromBuffer());
                        entry = new PlayerListEntry(profile, mode);
                        break;
                    case UPDATE_LATENCY:
                        png = in.readVarIntFromBuffer();
                        entry = new PlayerListEntry(profile, png);
                        break;
                    case UPDATE_DISPLAY_NAME:
                        disp = null;
                        if (in.readBoolean()) {
                            disp = ComponentSerializer.parse(in.readStringFromBuffer(32767));
                        }
                        entry = new PlayerListEntry(profile, disp);
                        break;
                    case REMOVE_PLAYER:
                        entry = new PlayerListEntry(profile);
                        break;
                }

                entries[count] = entry;
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }


    public void write(PacketBuffer out) throws Exception {
        try {
            out.writeVarIntToBuffer(action.getId());
            out.writeVarIntToBuffer(entries.length);
            for (PlayerListEntry entry : entries) {
                out.writeUuid(entry.getProfile().getUuid());
                switch (action) {
                    case ADD_PLAYER:
                        out.writeString(entry.getProfile().getUsername());
                        out.writeVarIntToBuffer(entry.getProfile().getProperties().size());
                        entry.getProfile().getProperties().forEach((name, property) -> {
                            out.writeString(property.getName());
                            out.writeString(property.getValue());
                            out.writeBoolean(property.hasSignature());
                            if (property.hasSignature()) {
                                out.writeString(property.getSignature());
                            }
                        });
                        out.writeVarIntToBuffer(entry.getGameMode().getId());
                        out.writeVarIntToBuffer(entry.getPing());
                        out.writeBoolean((entry.getDisplayName() != null));
                        if (entry.getDisplayName() != null) {
                            out.writeString(entry.getDisplayAsJson());
                        }
                        break;

                    case UPDATE_GAMEMODE:
                        out.writeVarIntToBuffer(entry.getGameMode().getId());
                        break;
                    case UPDATE_LATENCY:
                        out.writeVarIntToBuffer(entry.getPing());
                        break;
                    case UPDATE_DISPLAY_NAME:
                        out.writeBoolean((entry.getDisplayName() != null));
                        if (entry.getDisplayName() != null) {
                            out.writeString(entry.getDisplayAsJson());
                        }
                        break;
                }
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    }
}
