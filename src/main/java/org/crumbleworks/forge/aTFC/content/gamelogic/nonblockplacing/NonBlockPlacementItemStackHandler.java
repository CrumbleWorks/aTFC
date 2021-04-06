package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import org.crumbleworks.forge.aTFC.content.items.Bulky;
import org.crumbleworks.forge.aTFC.content.items.Bulky.Bulk;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementItemStackHandler extends ItemStackHandler {

    public NonBlockPlacementItemStackHandler() {
        super(4);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem() instanceof WorldPlaceable;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    @Override
    public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
        Item item = stack.getItem();
        if(!itemIsLarge(item)) { // normal insertion
            return super.insertItem(slot, stack, simulate);
        }

        // we have a large item, make sure the inventory is empty
        if(!isEmpty()) {
            return stack;
        }

        return super.insertItem(0, stack, simulate);
    }

    @Override
    public ItemStack extractItem(int slot, int amount, boolean simulate) {
        if(isHoldingLargeItem()) {
            return super.extractItem(0, amount, simulate);
        }

        return super.extractItem(slot, amount, simulate);
    }

    public final boolean isEmpty() {
        for(int i = 0 ; i < getSlots() ; i++) {
            if(!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }

    public final boolean isHoldingLargeItem() {
        ItemStack slot0Stack = getStackInSlot(0);
        if(slot0Stack.isEmpty()) {
            return false;
        }

        Item slot0Item = slot0Stack.getItem();
        return itemIsLarge(slot0Item);

    }

    private static final boolean itemIsLarge(Item item) {
        if(item instanceof Bulky
                && ((Bulky)item).getBulk().isLargerThan(Bulk.MEDIUM)) {
            return true;
        }

        return false;
    }
}
