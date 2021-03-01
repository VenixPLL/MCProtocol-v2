package me.dickmeister.mcprotocol.minecraft.entity.player;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import java.util.Arrays;

/**
 * @author Unix
 * @since 10.01.2021
 */

@Getter
@RequiredArgsConstructor
public enum PlayerState {

    START_SNEAKING(0), // change to static AtomicInteger?
    STOP_SNEAKING(1),
    LEAVE_BED(2),
    START_SPRINTING(3),
    STOP_SPRINTING(4),
    START_HORSE_JUMP(5),
    STOP_HORSE_JUMP(6),
    OPEN_HORSE_INVENTORY(7),
    START_ELYTRA_FLYING(8);

    private final int id;

    public static PlayerState of(int id) {
        return Arrays.stream(values())
                .filter(playerState -> playerState.id == id)
                .findFirst()
                .orElse(null);
    }
}