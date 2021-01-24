package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.entity.Entity;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.server.ServerWorld;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class GrasscoverableBlock extends Block
        implements Tintable, Multilayered, GrassCoverable {

    public GrasscoverableBlock(AbstractBlock.Properties properties) {
        super(properties);

        setDefaultState(stateContainer.getBaseState().with(COVERAGE,
                GrassCoverage.NONE));
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(PROPSET_GRASS_COVERABLE);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return true;
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn,
            BlockPos pos, Random random) {
        tryGrowingGrass(state, worldIn, pos, random);
    }
    
    @Override
        public SoundType getSoundType(BlockState state, IWorldReader world,
                BlockPos pos, Entity entity) {
            // TODO Auto-generated method stub
            return super.getSoundType(state, world, pos, entity);
        }
}
