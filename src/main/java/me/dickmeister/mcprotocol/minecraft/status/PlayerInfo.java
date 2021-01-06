package me.dickmeister.mcprotocol.minecraft.status;

import lombok.Data;
import me.dickmeister.mcprotocol.minecraft.auth.GameProfile;

/**
 * Skided long time ago from idk, probably MCProtocolLib
 */
@Data
public class PlayerInfo {
    private int onlinePlayers, maxPlayers;
    private GameProfile[] players;

    public PlayerInfo(int onlinePlayers, int maxPlayers, GameProfile... players) {
        this.onlinePlayers = onlinePlayers;
        this.maxPlayers = maxPlayers;
        this.players = players;
    }
}
