package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.blocks.aTFCBaseBlock;
import org.crumbleworks.forge.aTFC.utilities.Util;
import org.crumbleworks.forge.aTFC.utilities.aTFCInventoryHelper;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.block.IWaterLoggable;
import net.minecraft.block.SoundType;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementBlock extends aTFCBaseBlock
        implements IWaterLoggable {

    public NonBlockPlacementBlock() {
        super(AbstractBlock.Properties.create(Materials.ABSTRACT_BLOCKS)
                .zeroHardnessAndResistance().sound(SoundType.GLASS)
                .doesNotBlockMovement());
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
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getRenderShape(BlockState state, IBlockReader worldIn,
            BlockPos pos) {
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

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn,
            BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit) {
        if(worldIn.isRemote()) {
            return ActionResultType.SUCCESS;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(! (tileEntity instanceof NonBlockPlacementTE)) {
            return ActionResultType.PASS;
        }

        NonBlockPlacementTE nbpte = (NonBlockPlacementTE)tileEntity;
        ItemStack itemstack = player.getHeldItem(handIn);
        int targetSlot = Util.gridSlot2x2XZ(hit);
        if(itemstack.isEmpty()) { // holding nothing
            player.setHeldItem(handIn, nbpte.extractItem(targetSlot));
            return ActionResultType.CONSUME;
        }
        
        if(player.isCreative()) {
            nbpte.insertItem(targetSlot, itemstack.copy());
        } else {
            player.setHeldItem(handIn,
                    nbpte.insertItem(targetSlot, itemstack));
        }
        
        return ActionResultType.CONSUME;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos,
            BlockState newState, boolean isMoving) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(tileEntity instanceof NonBlockPlacementTE) {
            aTFCInventoryHelper.dropInventoryItems(worldIn, pos,
                    ((NonBlockPlacementTE)tileEntity).inventory.resolve()
                            .get());
        }

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world,
            BlockPos pos, ParticleManager manager) {
        return true;
    }
}
