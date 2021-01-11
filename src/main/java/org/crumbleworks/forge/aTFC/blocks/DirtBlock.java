package org.crumbleworks.forge.aTFC.blocks;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class DirtBlock extends FallingBlock {

    public DirtBlock() {
        super(AbstractBlock.Properties
                .create(Material.EARTH, MaterialColor.DIRT)
                .hardnessAndResistance(0.5F)
                .sound(SoundType.GROUND));
    }
}
