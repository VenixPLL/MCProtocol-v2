package me.dickmeister.mcprotocol.minecraft.bossbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum BossBarColor
{
    PINK(0),
    CYAN(1),
    RED(2),
    LIME(3),
    YELLOW(4),
    PURPLE(5),
    WHITE(6);

    private final int id;
}
