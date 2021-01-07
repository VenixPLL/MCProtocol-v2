package me.dickmeister.mcprotocol.minecraft.world.block;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum BlockFace
{
    DOWN(0),
    UP(1),
    NORTH(2),
    SOUTH(3),
    WEST(4),
    EAST(5);

    private final int id;

    public static BlockFace of(int id) {
        return Arrays.stream(values())
                .filter(blockFace -> blockFace.id == id)
                .findFirst()
                .orElse(DOWN);
    }
}
