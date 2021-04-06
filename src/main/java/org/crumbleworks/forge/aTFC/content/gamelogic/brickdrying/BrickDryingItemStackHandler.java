package org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying;

import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDryingItemStackHandler extends ItemStackHandler {

    public BrickDryingItemStackHandler() {
        super(4);
    }

    @Override
    public boolean isItemValid(int slot, ItemStack stack) {
        return stack.getItem() instanceof DryableBrickItem;
    }

    @Override
    public int getSlotLimit(int slot) {
        return 1;
    }

    public final boolean isEmpty() {
        for(int i = 0 ; i < getSlots() ; i++) {
            if(!getStackInSlot(i).isEmpty()) {
                return false;
            }
        }

        return true;
    }
}
