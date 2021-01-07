package me.dickmeister.mcprotocol.minecraft.playerlist;

import lombok.Data;
import me.dickmeister.mcprotocol.minecraft.Gamemode;
import me.dickmeister.mcprotocol.minecraft.auth.GameProfile;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;

import java.util.Objects;

@Data
public class PlayerListEntry {
    private GameProfile profile;
    private Gamemode gameMode;
    private int ping;
    private BaseComponent[] displayName;

    public PlayerListEntry(GameProfile profile, Gamemode gameMode, int ping, BaseComponent... displayName) {
        this.profile = profile;
        this.gameMode = gameMode;
        this.ping = ping;
        this.displayName = displayName;
    }

    public PlayerListEntry(GameProfile profile, Gamemode gameMode) {
        this.profile = profile;
        this.gameMode = gameMode;
    }

    public PlayerListEntry(GameProfile profile, int ping) {
        this.profile = profile;
        this.ping = ping;
    }

    public PlayerListEntry(GameProfile profile, BaseComponent... displayName) {
        this.profile = profile;
        this.displayName = displayName;
    }

    public PlayerListEntry(GameProfile profile) {
        this.profile = profile;
    }

    public String getDisplayName() {
        return (Objects.isNull(displayName)
                ? null
                : displayName.length > 0 ? displayName[0].toPlainText()
                : null
        );

    }

    public String getDisplayAsJson() {
        return ComponentSerializer.toString(displayName);
    }
}
