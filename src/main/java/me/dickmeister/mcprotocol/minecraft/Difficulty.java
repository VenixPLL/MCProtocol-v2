package me.dickmeister.mcprotocol.minecraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Difficulty {
    PEACEFULL(0),
    EASY(1),
    NORMAL(2),
    HARD(3);

    private final int id;

    public static Difficulty getById(int id) {
        return Arrays.stream(Difficulty.values())
                .filter(difficulty -> difficulty.id == id)
                .findFirst()
                .orElse(Difficulty.NORMAL);
    }
}
