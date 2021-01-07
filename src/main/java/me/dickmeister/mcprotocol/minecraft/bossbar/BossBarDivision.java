package me.dickmeister.mcprotocol.minecraft.bossbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum BossBarDivision
{
    NONE(0),
    NOTCHES_6(1),
    NOTCHES_10(2),
    NOTCHES_12(3),
    NOTCHES_20(4);

    private final int id;
}
