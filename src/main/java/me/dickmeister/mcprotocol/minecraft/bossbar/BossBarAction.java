package me.dickmeister.mcprotocol.minecraft.bossbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum BossBarAction {

    ADD(0),
    REMOVE(1),
    UPDATE_HEALTH(2),
    UPDATE_TITLE(3),
    UPDATE_STYLE(4),
    UPDATE_FLAGS(5);

    private final int id;
}
