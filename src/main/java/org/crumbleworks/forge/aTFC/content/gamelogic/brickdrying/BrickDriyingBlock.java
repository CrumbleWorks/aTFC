package org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying;

import org.crumbleworks.forge.aTFC.content.Materials;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDriyingBlock extends Block {

    public BrickDriyingBlock() {
        super(AbstractBlock.Properties.create(Materials.ABSTRACT_BLOCKS)
                .zeroHardnessAndResistance());
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state,
            IBlockReader world) {
        return new BrickPlacerTE();
    }
}
