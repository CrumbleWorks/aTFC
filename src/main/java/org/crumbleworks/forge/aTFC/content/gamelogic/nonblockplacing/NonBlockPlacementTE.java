package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.TileEntities;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
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

    private final LazyOptional<ItemStackHandler> inventory = LazyOptional
            .of(() -> new NonBlockPlacementItemStackHandler());

    public NonBlockPlacementTE() {
        super(TileEntities.NON_BLOCK_PLACER_TE);
    }

    public ItemStack insertItem(int slot, ItemStack item) {
        System.out.println(" >>> PLACING ITEM SIR");

        markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(),
                Constants.BlockFlags.DEFAULT);
        return inventory.resolve().get().insertItem(slot, item, false);
    }

    public ItemStack extractItem(int slot) {
        System.out.println(" >>> REMOVING ITEM SIR");

        NonBlockPlacementItemStackHandler _inventory = (NonBlockPlacementItemStackHandler)inventory
                .resolve().get();
        ItemStack stack = _inventory.extractItem(slot, 1, false);

        markDirty();
        if(_inventory.isEmpty()) {
            System.out.println(" >>> DESTROYING BLOCK SIR");
            world.setBlockState(pos, Blocks.AIR.getDefaultState(),
                    Constants.BlockFlags.DEFAULT);
        }

        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(),
                Constants.BlockFlags.DEFAULT);
        return stack;
    }

    /* SINGLE ENTITY SYNCH */

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getPos(), -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net,
            SUpdateTileEntityPacket pkt) {
        handleUpdateTag(getBlockState(), pkt.getNbtCompound());
    }

    /* CHUNK LOAD SYNCH */

    @Override
    public CompoundNBT getUpdateTag() {
        return write(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        read(state, tag);
    }

    /* STORAGE AND CAPABILITY STUFF */

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
