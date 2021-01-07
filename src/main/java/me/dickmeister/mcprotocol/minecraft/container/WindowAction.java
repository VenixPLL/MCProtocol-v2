package me.dickmeister.mcprotocol.minecraft.container;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum WindowAction {
    CLICK_ITEM(0),
    SHIFT_CLICK_ITEM(1),
    MOVE_TO_HOTBAR_SLOT(2),
    CREATIVE_GRAB_MAX_STACK(3),
    DROP_ITEM(4),
    SPREAD_ITEM(5),
    FILL_STACK(6);

    private final int id;

    public static WindowAction getActionById(int id) {
        return Arrays.stream(values())
                .filter(windowAction -> windowAction.getId() == id)
                .findFirst()
                .orElse(null);
    }
}
