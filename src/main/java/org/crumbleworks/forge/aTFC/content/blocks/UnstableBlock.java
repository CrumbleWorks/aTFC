package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import org.crumbleworks.forge.aTFC.content.entities.UnstableBlockEntity;

import net.minecraft.block.BlockState;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.FallingBlockEntity;
import net.minecraft.particles.BlockParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;


/**
 * Mostly a copy of {@link FallingBlock}.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class UnstableBlock extends aTFCBaseBlock {

    public UnstableBlock(Properties properties) {
        super(properties);
    }

    @Override
    public void onBlockAdded(BlockState state, World worldIn, BlockPos pos,
            BlockState oldState, boolean isMoving) {
        worldIn.getPendingBlockTicks().scheduleTick(pos, this,
                this.getFallDelay());
    }

    /**
     * Update the provided state given the provided neighbor facing and
     * neighbor state, returning a new state.
     * For example, fences make their connections to the passed in state if
     * possible, and wet concrete powder immediately
     * returns its solidified counterpart.
     * Note that this method should ideally consider only the specific face
     * passed in.
     */
    @Override
    public BlockState updatePostPlacement(BlockState stateIn,
            Direction facing, BlockState facingState, IWorld worldIn,
            BlockPos currentPos, BlockPos facingPos) {
        worldIn.getPendingBlockTicks().scheduleTick(currentPos, this,
                this.getFallDelay());
        return super.updatePostPlacement(stateIn, facing, facingState,
                worldIn, currentPos, facingPos);
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

    protected void onStartFalling(FallingBlockEntity fallingEntity) {}

    protected int getFallDelay() {
        return 2;
    }

    public void onEndFalling(World worldIn, BlockPos pos,
            BlockState fallingState, BlockState hitState,
            FallingBlockEntity fallingBlock) {}

    public void onBroken(World worldIn, BlockPos pos,
            FallingBlockEntity fallingBlock) {}

    /**
     * Called periodically clientside on blocks near the player to show
     * effects (like furnace fire particles). Note that
     * this method is unrelated to {@link randomTick} and
     * {@link #needsRandomTick}, and will always be called regardless
     * of whether the block can receive random update ticks
     */
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos,
            Random rand) {
        if(rand.nextInt(16) == 0) {
            BlockPos blockpos = pos.down();
            if(worldIn.isAirBlock(blockpos)
                    || FallingBlock.canFallThrough(
                            worldIn.getBlockState(blockpos))) {
                double d0 = (double)pos.getX() + rand.nextDouble();
                double d1 = (double)pos.getY() - 0.05D;
                double d2 = (double)pos.getZ() + rand.nextDouble();
                worldIn.addParticle(
                        new BlockParticleData(ParticleTypes.FALLING_DUST,
                                stateIn),
                        d0, d1, d2, 0.0D, 0.0D, 0.0D);
            }
        }

    }

    @OnlyIn(Dist.CLIENT)
    public int getDustColor(BlockState state, IBlockReader reader,
            BlockPos pos) {
        return -16777216;
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
                && !FallingBlock.canFallThrough(worldIn.getBlockState(pos));
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
