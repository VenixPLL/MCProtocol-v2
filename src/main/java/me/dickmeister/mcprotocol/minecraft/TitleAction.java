package me.dickmeister.mcprotocol.minecraft;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum TitleAction {
    TITLE(0),
    SUBTITLE(1),
    TIMES(2),
    HIDE(3),
    RESET(4);

    private final int id;

    public static TitleAction getById(int id) {
        return Arrays.stream(TitleAction.values())
                .filter(titleAction -> titleAction.id == id)
                .findFirst()
                .orElse(TitleAction.RESET);
    }
}