package org.crumbleworks.forge.aTFC.blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.crumbleworks.forge.aTFC.entities.UnstableBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class UnstableBlock extends FallingBlock {

    public UnstableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void tick(BlockState state, ServerWorld worldIn, BlockPos pos,
            Random rand) {
        if(!isBlockSupported(worldIn, pos) && pos.getY() >= 0) {

            BlockPos fallPos = determineFallDirection(worldIn, pos, rand);
            UnstableBlockEntity fallingblockentity = new UnstableBlockEntity(
                    worldIn,
                    (double)fallPos.getX() + 0.5D,
                    (double)fallPos.getY(),
                    (double)fallPos.getZ() + 0.5D,
                    worldIn.getBlockState(pos)); // original blockstate

            this.onStartFalling(fallingblockentity);

            worldIn.addEntity(fallingblockentity);
        }
    }

    private BlockPos determineFallDirection(World worldIn, BlockPos pos,
            Random rand) {
        // Always drop down if possible
        if(!isBlockSolid(worldIn, pos.down())) {
            return pos;
        }

        Integer[] directions = { 0, 1, 2, 3 }; // n, e, s, w
        List<Integer> l = Arrays.asList(directions);
        Collections.shuffle(l);

        for(int i : directions) {

            if(i == 0 && !isSupportingBlock(worldIn, pos.north())
                    && canFallTowards(worldIn, pos.north())) {
                return pos.add(0, 0, -1);
            }

            if(i == 1 && !isSupportingBlock(worldIn, pos.east())
                    && canFallTowards(worldIn, pos.east())) {
                return pos.add(1, 0, 0);
            }

            if(i == 2 && !isSupportingBlock(worldIn, pos.south())
                    && canFallTowards(worldIn, pos.south())) {
                return pos.add(0, 0, 1);
            }

            if(i == 3 && !isSupportingBlock(worldIn, pos.west())
                    && canFallTowards(worldIn, pos.west())) {
                return pos.add(-1, 0, 0);
            }
        }

        return pos;
    }

    private boolean canFallTowards(World worldIn, BlockPos pos) {
        AxisAlignedBB bb = new AxisAlignedBB(pos);
        return !isBlockSolid(worldIn, pos) && worldIn
                .getEntitiesWithinAABB(EntityType.FALLING_BLOCK, bb, (e) -> {
                    return true;
                }).isEmpty()
                && !isBlockSolid(worldIn, pos.down());
    }

    private boolean isSupportingBlock(World worldIn, BlockPos pos) {
        BlockState state = worldIn.getBlockState(pos);
        Material material = state.getMaterial();
        // Water is able to lend support to blocks (making sure coasts don't
        // errode)
        return !worldIn.isAirBlock(pos)
                && !state.isIn(BlockTags.FIRE)
                && !material.isReplaceable();
    }

    private boolean isBlockSolid(World worldIn, BlockPos pos) {
        return !worldIn.isAirBlock(pos)
                && !canFallThrough(worldIn.getBlockState(pos));
    }

    private boolean isBlockSupported(World worldIn, BlockPos pos) {
        // Block is supported, no falling possible
        // TODO https://github.com/CrumbleWorks/aTFC/issues/3

        // If below block is not solid, it cannot be supporting
        if(!isBlockSolid(worldIn, pos.down())) {
            return false;
        }

        // Needs at least(!) two blocks on any sides to be supported
        int supportedSides = 0;
        if(isSupportingBlock(worldIn, pos.north())) supportedSides++;
        if(isSupportingBlock(worldIn, pos.east())) supportedSides++;
        if(isSupportingBlock(worldIn, pos.south())) supportedSides++;
        if(isSupportingBlock(worldIn, pos.west())) supportedSides++;
        if(supportedSides >= 2) {
            return true;
        }

        // Is there anywhere to fall to?
        int fallableSpots = 0;
        if(canFallTowards(worldIn, pos.north())) {
            fallableSpots++;
        }
        if(canFallTowards(worldIn, pos.east())) {
            fallableSpots++;
        }
        if(canFallTowards(worldIn, pos.south())) {
            fallableSpots++;
        }
        if(canFallTowards(worldIn, pos.west())) {
            fallableSpots++;
        }

        return fallableSpots < 1;
    }
}
