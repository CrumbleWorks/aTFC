package org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.utilities.Util;
import org.crumbleworks.forge.aTFC.wiring.TileEntitiesMappings;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDryingWiringAndEvents implements Wireable {

    private static final String name = "brickdrying";

    public static final RegistryObject<Block> BRICKDRYING_BLOCK = BLOCKS
            .register(name, () -> new BrickDriyingBlock());

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void createPlacementStorage(
                PlayerInteractEvent.RightClickBlock event) {
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
            if(! (item instanceof DryableBrickItem)) {
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
                    BRICKDRYING_BLOCK.get().getDefaultState());
            TileEntity te = event.getWorld().getTileEntity(placementPos);
            if(! (te instanceof BrickPlacerTE)) {
                return;
            }

            BrickPlacerTE nbpte = (BrickPlacerTE)te;
            int targetSlot = Util.gridSlot2x2XZ(event.getHitVec());
            if(player.isCreative()) {
                nbpte.insertItem(targetSlot, itemstack.copy());
            } else {
                player.setHeldItem(event.getHand(),
                        nbpte.insertItem(targetSlot, itemstack));
            }

            event.setCanceled(true);
        }
    }

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.getBuilder(name); // empty model
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.simpleState(name, BRICKDRYING_BLOCK.get());
    }

    @Override
    public void registerTileEntities(TileEntitiesMappings tm) {
        tm.addMapping(BrickPlacerTE.class,
                BRICKDRYING_BLOCK.get());
    }
}
