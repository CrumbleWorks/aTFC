package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;

import net.minecraft.item.Item;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class UnfiredClayMoldItem extends WorldPlaceableItem {

    public UnfiredClayMoldItem() {
        super(Bulk.MEDIUM, Weighty.TEN_POUND, new Item.Properties()
                .group(ItemGroups.MOLDS).isImmuneToFire());
    }
    
    public int getMaxStackSize() {
        return 1;
    }
}
