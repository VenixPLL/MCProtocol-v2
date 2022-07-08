/*
 * MCProtocol-v2
 * Copyright (C) 2022.  VenixPLL
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package me.dickmeister.mcprotocol.minecraft.bossbar;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum BossBarDivision {
    NONE(0),
    NOTCHES_6(1),
    NOTCHES_10(2),
    NOTCHES_12(3),
    NOTCHES_20(4);

    private final int id;
}
