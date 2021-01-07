package me.dickmeister.mcprotocol.minecraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Gamemode {
    SURVIVAL(0),
    CREATIVE(1),
    ADVENTURE(2),
    SPECTATOR(3),
    HARDCORE(0x8);

    private final int id;

    public static Gamemode getById(int id) {
        return Arrays.stream(Gamemode.values())
                .filter(gamemode -> gamemode.id == id)
                .findFirst()
                .orElse(Gamemode.SURVIVAL);
    }
}
