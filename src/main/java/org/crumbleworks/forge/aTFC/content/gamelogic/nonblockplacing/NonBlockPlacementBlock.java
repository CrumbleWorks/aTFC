package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.blocks.aTFCBaseBlock;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementBlock extends aTFCBaseBlock {

    public NonBlockPlacementBlock() {
        super(AbstractBlock.Properties.create(Materials.ABSTRACT_BLOCKS)
                .zeroHardnessAndResistance().sound(SoundType.GLASS));
    }

    // copy from AirBlock
    /**
     * The type of render function called. MODEL for mixed tesr and static
     * model, MODELBLOCK_ANIMATED for TESR-only,
     * LIQUID for vanilla liquids, INVISIBLE to skip all rendering
     * 
     * @deprecated call via {@link IBlockState#getRenderType()} whenever
     *             possible. Implementing/overriding is fine.
     */
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    // copy from AirBlock
    public VoxelShape getShape(BlockState state, IBlockReader worldIn,
            BlockPos pos, ISelectionContext context) {
        return VoxelShapes.empty();
    }
    
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }
    
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new NonBlockPlacementTE();
    }
}
