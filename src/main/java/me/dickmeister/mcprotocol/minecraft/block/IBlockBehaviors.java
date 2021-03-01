package me.dickmeister.mcprotocol.minecraft.block;

import me.dickmeister.mcprotocol.minecraft.BlockPos;

/**
 * @author Unix
 * @since 10.01.2021
 */

public interface IBlockBehaviors
{
    /**
     * Called on both Client and Server when World#addBlockEvent is called. On the Server, this may perform additional
     * changes to the world, like pistons replacing the block with an extended base. On the client, the update may
     * involve replacing tile entities, playing sounds, or performing other visual actions to reflect the server side
     * changes.
     */
    boolean onBlockEventReceived(BlockPos pos, int id, int param);

    /**
     * Called when a neighboring block was changed and marks that this state should perform any checks during a neighbor
     * change. Cases may include when redstone power is updated, cactus blocks popping off due to a neighboring solid
     * block, etc.
     */
    void neighborChanged(BlockPos pos, Block blockIn, BlockPos p_189546_4_);
}