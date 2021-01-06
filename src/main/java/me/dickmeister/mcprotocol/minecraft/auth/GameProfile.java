package me.dickmeister.mcprotocol.minecraft.auth;

import com.mojang.authlib.properties.PropertyMap;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

/**
 * Skidded from Minecraft Client
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameProfile {
    private final PropertyMap properties = new PropertyMap();
    private String username;
    private UUID uuid;
}
