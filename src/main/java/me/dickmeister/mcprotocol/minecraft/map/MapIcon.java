package me.dickmeister.mcprotocol.minecraft.map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MapIcon
{
    private int x, y;
    private int size;
    private int direction;
}
