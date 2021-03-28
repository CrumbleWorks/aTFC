package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.TileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementTE extends TileEntity {

    private static final String STORAGE_KEY = Main.MOD_ID + "_NBP_inv";

    // FIXME make this dependant on the block used to create ?
    private final LazyOptional<ItemStackHandler> inventory = LazyOptional
            .of(() -> new ItemStackHandler(4));

    public NonBlockPlacementTE() {
        super(TileEntities.NON_BLOCK_PLACER_TE);
    }

    
    
    @Override
    public void onDataPacket(NetworkManager net,
            SUpdateTileEntityPacket pkt) {
    }
    
    
    
    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        super.handleUpdateTag(state, tag);
    }
    
    
    
    @Override
    public CompoundNBT write(CompoundNBT nbt) {
        super.write(nbt);
        inventory.ifPresent(
                handler -> nbt.put(STORAGE_KEY, handler.serializeNBT()));
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        inventory.ifPresent(handler -> handler
                .deserializeNBT(nbt.getCompound(STORAGE_KEY)));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
            return inventory.cast();
        }
        return super.getCapability(cap);
    }
}
