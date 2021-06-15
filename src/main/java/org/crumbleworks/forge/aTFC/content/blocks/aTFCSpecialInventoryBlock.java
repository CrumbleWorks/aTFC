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

/**
 * Baseblock for storing items in the world
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
    public BlockRenderType getRenderShape(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @Override
    public VoxelShape getOcclusionShape(BlockState state,
            IBlockReader worldIn,
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
    public ActionResultType use(BlockState state, World worldIn,
            BlockPos pos, PlayerEntity player, Hand handIn,
            BlockRayTraceResult hit) {
        if(worldIn.isClientSide()) {
            return ActionResultType.SUCCESS;
        }

        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if(! (limiterClass.isInstance(tileEntity))) {
            return ActionResultType.PASS;
        }

        aTFCSpecialInventoryTE te = (aTFCSpecialInventoryTE)tileEntity;
        ItemStack itemstack = player.getItemInHand(handIn);

        // TODO see if instead we can get a hit-vector for the underlying
        // block and thus always allow for using the up-case
        int targetSlot;
        if(hit.getDirection() == Direction.UP) {
            targetSlot = Util.gridSlot2x2up(hit);
        } else if(hit.getDirection() == Direction.NORTH) {
            targetSlot = Util.gridSlot2x2north(hit);
        } else if(hit.getDirection() == Direction.SOUTH) {
            // FIXME beide achsen vertauscht
            targetSlot = Util.gridSlot2x2south(hit);
        } else if(hit.getDirection() == Direction.EAST) {
            // FIXME x-achse vertauscht
            targetSlot = Util.gridSlot2x2east(hit);
        } else { // WEST
            targetSlot = Util.gridSlot2x2west(hit);
        }

        if(itemstack.isEmpty()) { // holding nothing
            player.setItemInHand(handIn, te.extractItem(targetSlot));
            return ActionResultType.CONSUME;
        }

        if(player.isCreative()) {
            te.insertItem(targetSlot, itemstack.copy());
        } else {
            player.setItemInHand(handIn,
                    te.insertItem(targetSlot, itemstack));
        }

        return ActionResultType.CONSUME;
    }

    public static void rightClickLogic(
            PlayerInteractEvent.RightClickBlock event, Block createdBlock,
            Class<?> itemMarker) {
        if(!Util.isServerWorld(event.getWorld())) {
            return;
        }

        BlockState targetBlock = event.getWorld()
                .getBlockState(event.getPos());
        if(targetBlock.getBlock() == createdBlock) {
            // Add to existing TileEntity
            ActionResultType result = createdBlock.use(targetBlock,
                    event.getWorld(), event.getPos(), event.getPlayer(),
                    event.getHand(), event.getHitVec());

            if(result.consumesAction()) {
                event.setCanceled(true);
            }

            return;
        }

        // Place new TileEntity
        PlayerEntity player = event.getPlayer();
        if(!player.isShiftKeyDown()) {
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

        if(!event.getWorld().getBlockState(event.getPos()).isFaceSturdy(
                event.getWorld(), event.getPos(), Direction.UP)) {
            return;
        }

        BlockPos placementPos = event.getPos().above();
        event.getWorld().setBlockAndUpdate(placementPos,
                createdBlock.defaultBlockState());
        TileEntity te = event.getWorld().getBlockEntity(placementPos);
        if(! (te instanceof aTFCSpecialInventoryTE)) {
            return;
        }

        aTFCSpecialInventoryTE specTe = (aTFCSpecialInventoryTE)te;
        int targetSlot = Util.gridSlot2x2up(event.getHitVec());
        if(player.isCreative()) {
            specTe.insertItem(targetSlot, itemstack.copy());
        } else {
            player.setItemInHand(event.getHand(),
                    specTe.insertItem(targetSlot, itemstack));
        }

        event.setCanceled(true);
    }

    @Override
    public void onRemove(BlockState state, World worldIn, BlockPos pos,
            BlockState newState, boolean isMoving) {
        TileEntity tileEntity = worldIn.getBlockEntity(pos);
        if(limiterClass.isInstance(tileEntity)) {
            aTFCInventoryHelper.dropInventoryItems(worldIn, pos,
                    ((aTFCSpecialInventoryTE)tileEntity).inventory.resolve()
                            .get());
        }

        super.onRemove(state, worldIn, pos, newState, isMoving);
    }

    @Override
    public boolean addDestroyEffects(BlockState state, World world,
            BlockPos pos, ParticleManager manager) {
        return true;
    }
}
