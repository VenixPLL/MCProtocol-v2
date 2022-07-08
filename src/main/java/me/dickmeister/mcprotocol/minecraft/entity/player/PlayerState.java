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