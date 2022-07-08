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

package me.dickmeister.mcprotocol.minecraft.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

/**
 * @author Unix
 * @since 21.08.2020
 */

@Getter
@RequiredArgsConstructor
public enum WindowType {
    GENERIC_INVENTORY("minecraft:container"),
    ANVIL("minecraft:anvil"),
    BEACON("minecraft:beacon"),
    BREWING_STAND("minecraft:brewing_stand"),
    CHEST("minecraft:chest"),
    CRAFTING_TABLE("minecraft:crafting_table"),
    DISPENSER("minecraft:dispenser"),
    DROPPER("minecraft:dropper"),
    ENCHANTING_TABLE("minecraft:enchanting_table"),
    FURNACE("minecraft:furnace"),
    HOPPER("minecraft:hopper"),
    VILLAGER("minecraft:villager"),
    HORSE("EntityHorse");

    private final String id;

    public static WindowType of(String id) {
        return Arrays.stream(values())
                .filter(windowType -> windowType.id.equals(id))
                .findFirst()
                .orElse(GENERIC_INVENTORY);
    }
}