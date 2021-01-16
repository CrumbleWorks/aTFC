package org.crumbleworks.forge.aTFC.content.blocks;

import org.crumbleworks.forge.aTFC.content.BSP;
import org.crumbleworks.forge.aTFC.content.BSP.GrassCoverage;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.state.StateContainer.Builder;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class SoilBlock extends UnstableBlock
        implements Tintable, Multilayered {

    public SoilBlock() {
        super(AbstractBlock.Properties
                .create(Material.EARTH, MaterialColor.DIRT)
                .hardnessAndResistance(0.5F)
                .sound(SoundType.GROUND));

        setDefaultState(stateContainer.getBaseState().with(BSP.COVERAGE,
                GrassCoverage.NONE));
    }

    @Override
    protected void fillStateContainer(Builder<Block, BlockState> builder) {
        builder.add(BSP.PROPSET_GRASS_COVERABLE);
    }
}
