package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.Random;

import org.crumbleworks.forge.aTFC.content.Materials;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ClaySoilBlock extends Block
        implements Tintable, Multilayered, GrassCoverable {

    public ClaySoilBlock() {
        super(AbstractBlock.Properties
                .create(Materials.CLAY_SOIL)
                .hardnessAndResistance(0.6F)
                .sound(SoundType.GROUND)
                .harvestTool(ToolType.SHOVEL));

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
}
