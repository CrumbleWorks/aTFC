package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.content.tileentities.aTFCSpecialInventoryTE;
import org.crumbleworks.forge.aTFC.utilities.Util;
import org.crumbleworks.forge.aTFC.utilities.aTFCInventoryHelper;

import net.minecraft.block.Block;
import net.minecraft.block.BlockRenderType;
import net.minecraft.block.BlockState;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraft.block.AbstractBlock.Properties;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCSpecialInventoryBlock extends aTFCBaseBlock {

    private final Supplier<TileEntity> teFactory;
    private final Class<? extends aTFCSpecialInventoryTE> limiterClass;

    public aTFCSpecialInventoryBlock(Properties properties,
            Supplier<TileEntity> teFactory,
            Class<? extends aTFCSpecialInventoryTE> limiterClass) {
        super(properties);

        this.teFactory = teFactory;
        this.limiterClass = limiterClass;
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
        return teFactory.get();
    }

    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn,
            BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit) {
        if(worldIn.isRemote()) {
            return ActionResultType.SUCCESS;
        }

        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(! (limiterClass.isInstance(tileEntity))) {
            return ActionResultType.PASS;
        }

        aTFCSpecialInventoryTE te = (aTFCSpecialInventoryTE)tileEntity;
        ItemStack itemstack = player.getHeldItem(handIn);
        int targetSlot = Util.gridSlot2x2XZ(hit);
        if(itemstack.isEmpty()) { // holding nothing
            player.setHeldItem(handIn, te.extractItem(targetSlot));
            return ActionResultType.CONSUME;
        }

        if(player.isCreative()) {
            te.insertItem(targetSlot, itemstack.copy());
        } else {
            player.setHeldItem(handIn,
                    te.insertItem(targetSlot, itemstack));
        }

        return ActionResultType.CONSUME;
    }

    public static void firstItemCreationLogic(
            PlayerInteractEvent.RightClickBlock event, Block createdBlock,
            Class<?> itemMarker) {
        if(!Util.isServerWorld(event.getWorld())) {
            return;
        }

        PlayerEntity player = event.getPlayer();
        if(!player.isSneaking()) {
            return;
        }

        ItemStack itemstack = event.getItemStack();
        if(itemstack.isEmpty()) {
            return;
        }

        Item item = itemstack.getItem();
        if(! (itemMarker.isInstance(item))) {
            return;
        }

        if(event.getFace() != Direction.UP) {
            return;
        }

        if(!event.getWorld().getBlockState(event.getPos()).isSolidSide(
                event.getWorld(), event.getPos(), Direction.UP)) {
            return;
        }

        BlockPos placementPos = event.getPos().up();
        event.getWorld().setBlockState(placementPos,
                createdBlock.getDefaultState());
        TileEntity te = event.getWorld().getTileEntity(placementPos);
        if(! (te instanceof aTFCSpecialInventoryTE)) {
            return;
        }

        aTFCSpecialInventoryTE specTe = (aTFCSpecialInventoryTE)te;
        int targetSlot = Util.gridSlot2x2XZ(event.getHitVec());
        if(player.isCreative()) {
            specTe.insertItem(targetSlot, itemstack.copy());
        } else {
            player.setHeldItem(event.getHand(),
                    specTe.insertItem(targetSlot, itemstack));
        }

        event.setCanceled(true);
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos,
            BlockState newState, boolean isMoving) {
        TileEntity tileEntity = worldIn.getTileEntity(pos);
        if(limiterClass.isInstance(tileEntity)) {
            aTFCInventoryHelper.dropInventoryItems(worldIn, pos,
                    ((aTFCSpecialInventoryTE)tileEntity).inventory.resolve()
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
