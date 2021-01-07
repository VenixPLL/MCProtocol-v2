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
