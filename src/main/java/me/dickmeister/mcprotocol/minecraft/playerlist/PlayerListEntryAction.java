package me.dickmeister.mcprotocol.minecraft.playerlist;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum PlayerListEntryAction
{
    ADD_PLAYER(0),
    UPDATE_GAMEMODE(1),
    UPDATE_LATENCY(2),
    UPDATE_DISPLAY_NAME(3),
    REMOVE_PLAYER(4);

    private final int id;

    public static PlayerListEntryAction getById(int id) {
        return Arrays.stream(PlayerListEntryAction.values())
                .filter(action -> action.id == id)
                .findFirst()
                .orElse(PlayerListEntryAction.REMOVE_PLAYER);
    }
}
