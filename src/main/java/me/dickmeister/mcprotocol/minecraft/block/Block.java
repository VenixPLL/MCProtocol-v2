package me.dickmeister.mcprotocol.minecraft.block;

/**
 * @author Unix
 * @since 10.01.2021
 */

public class Block {

    public static int getIdFromBlock(Block blockIn)
    {
        return 0;
    }

    /**
     * Get a unique ID for the given BlockState, containing both BlockID and metadata
     */
    public static int getStateId(IBlockState state)
    {
        Block block = state.getBlock();
        return 0;
    }

    public static Block getBlockById(int id)
    {
        return null;
    }

    /**
     * Get a BlockState by it's ID (see getStateId)
     */
    public static IBlockState getStateById(int id)
    {
        int i = id & 4095;
        int j = id >> 12 & 15;
        return null;
    }
}