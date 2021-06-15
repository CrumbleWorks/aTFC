package org.crumbleworks.forge.aTFC.utilities;

import net.minecraft.inventory.InventoryHelper;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.IItemHandler;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCInventoryHelper {

    public static void dropInventoryItems(World worldIn, BlockPos pos,
            IItemHandler itemHandler) {
        for(int i = 0 ; i < itemHandler.getSlots() ; ++i) {
            InventoryHelper.dropItemStack(worldIn, pos.getX(), pos.getY(),
                    pos.getZ(), itemHandler.extractItem(i,
                            itemHandler.getSlotLimit(i), false));
        }
    }
}
