package me.dickmeister.mcprotocol.minecraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum Dimension
{
    NETHER(-1),
    OVERWORLD(0),
    END(1);

    private final int id;

    public static Dimension getById(int id) {
        return Arrays.stream(Dimension.values())
                .filter(dimension -> dimension.id == id)
                .findFirst()
                .orElse(Dimension.OVERWORLD);
    }
}
