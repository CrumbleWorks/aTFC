package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.Random;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class SoilBlock extends UnstableBlock
        implements Tintable, Multilayered, GrassCoverable {

    public SoilBlock() {
        super(AbstractBlock.Properties
                .create(Material.EARTH, MaterialColor.DIRT)
                .hardnessAndResistance(0.5F)
                .sound(SoundType.GROUND));

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

    }
}
