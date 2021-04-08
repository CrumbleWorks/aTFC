package org.crumbleworks.forge.aTFC.content.tileentities;

import org.crumbleworks.forge.aTFC.content.capabilities.atfcItemHandler;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
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
public abstract class aTFCSpecialInventoryTE extends TileEntity {

    public final LazyOptional<ItemStackHandler> inventory;
    private final String storageKey;

    public aTFCSpecialInventoryTE(TileEntityType<?> tileEntityTypeIn,
            LazyOptional<ItemStackHandler> inventory, String storageKey) {
        super(tileEntityTypeIn);

        this.inventory = inventory;
        this.storageKey = storageKey;
    }

    public ItemStack insertItem(int slot, ItemStack item) {
        ItemStack resultStack = inventory.resolve().get().insertItem(slot,
                item, false);

        markDirty();
        world.notifyBlockUpdate(pos, getBlockState(), getBlockState(),
                Constants.BlockFlags.DEFAULT);
        return resultStack;
    }

    public ItemStack extractItem(int slot) {
        atfcItemHandler _inventory = (atfcItemHandler)inventory.resolve()
                .get();
        ItemStack stack = _inventory.extractItem(slot, 1, false);

        markDirty();
        if(_inventory.isEmpty()) {
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
                handler -> nbt.put(storageKey, handler.serializeNBT()));
        return nbt;
    }

    @Override
    public void read(BlockState state, CompoundNBT nbt) {
        super.read(state, nbt);
        inventory.ifPresent(handler -> handler
                .deserializeNBT(nbt.getCompound(storageKey)));
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        if(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY == cap) {
            return inventory.cast();
        }
        return super.getCapability(cap);
    }

}
