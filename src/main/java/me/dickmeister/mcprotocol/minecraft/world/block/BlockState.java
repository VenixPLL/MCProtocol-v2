package me.dickmeister.mcprotocol.minecraft.world.block;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Unix
 * @since 20.08.2020
 */

@Setter
@Getter
@AllArgsConstructor
public class BlockState {
    private int id;
    private int data;
}
