package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.HashSet;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;


import net.minecraft.block.AbstractBlock.Properties;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCBaseBlock extends Block {
    private static final int MAX_RECURSION_DEPTH_FOR_FALLCHECKS = 3;

    public aTFCBaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void neighborChanged(BlockState state, World worldIn, BlockPos pos,
            Block blockIn, BlockPos fromPos, boolean isMoving) {
        //is the neighborChanged source about a connected block being removed?
        if(!worldIn.getBlockState(fromPos).isSolid()) {
            Set<BlockPos> unsupportedBlocks = new HashSet<>();
            if(areBlocksUnsupported(worldIn, pos, 0, unsupportedBlocks)) {
                for(BlockPos delPos : unsupportedBlocks) {
                    
                    if(!worldIn.getBlockState(delPos).isSolid()) {
                        continue;
                    }
                    
                    worldIn.destroyBlock(delPos, false);
                    Block.spawnAsEntity(worldIn, delPos, new ItemStack(Item.getItemFromBlock(getSelf())));
                }
            }
        }

        super.neighborChanged(state, worldIn, pos, blockIn, fromPos,
                isMoving);
    }
    
    private static boolean areBlocksUnsupported(World world, BlockPos pos, final int currentDepth, Set<BlockPos> unsupportedBlocks) {
        unsupportedBlocks.add(pos);
        
        int supportedSides = 0;
        for(Direction dir : Direction.values()) {
            BlockPos testPos = pos.offset(dir);
            if(unsupportedBlocks.contains(testPos) || !world.getBlockState(testPos).isSolid()) {
                continue;
            }
            
            if(currentDepth < MAX_RECURSION_DEPTH_FOR_FALLCHECKS) {
                if(!areBlocksUnsupported(world, testPos, currentDepth + 1, unsupportedBlocks)) {
                    supportedSides++;
                }
            } else {
                supportedSides++;
            }
        }
        
        return supportedSides < 1;
    }
}
