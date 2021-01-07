package me.dickmeister.mcprotocol.minecraft.chat;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum MessagePosition {
    CHATBOX(0),
    SYSTEM(1),
    HOTBAR(2);

    private final int id;

    public static MessagePosition getById(int id) {
        return Arrays.stream(MessagePosition.values())
                .filter(messagePosition -> messagePosition.getId() == id)
                .findFirst()
                .orElse(CHATBOX);
    }
}
