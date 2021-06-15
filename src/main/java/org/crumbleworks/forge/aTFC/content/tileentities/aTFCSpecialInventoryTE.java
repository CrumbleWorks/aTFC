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

        setChanged();
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(),
                Constants.BlockFlags.DEFAULT);
        return resultStack;
    }

    public ItemStack extractItem(int slot) {
        atfcItemHandler _inventory = (atfcItemHandler)inventory.resolve()
                .get();
        ItemStack stack = _inventory.extractItem(slot, 1, false);

        setChanged();
        if(_inventory.isEmpty()) {
            level.setBlock(worldPosition, Blocks.AIR.defaultBlockState(),
                    Constants.BlockFlags.DEFAULT);
        }

        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(),
                Constants.BlockFlags.DEFAULT);
        return stack;
    }

    /* SINGLE ENTITY SYNCH */

    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(getBlockPos(), -1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net,
            SUpdateTileEntityPacket pkt) {
        handleUpdateTag(getBlockState(), pkt.getTag());
    }

    /* CHUNK LOAD SYNCH */

    @Override
    public CompoundNBT getUpdateTag() {
        return save(new CompoundNBT());
    }

    @Override
    public void handleUpdateTag(BlockState state, CompoundNBT tag) {
        load(state, tag);
    }

    /* STORAGE AND CAPABILITY STUFF */

    @Override
    public CompoundNBT save(CompoundNBT nbt) {
        super.save(nbt);
        inventory.ifPresent(
                handler -> nbt.put(storageKey, handler.serializeNBT()));
        return nbt;
    }

    @Override
    public void load(BlockState state, CompoundNBT nbt) {
        super.load(state, nbt);
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
