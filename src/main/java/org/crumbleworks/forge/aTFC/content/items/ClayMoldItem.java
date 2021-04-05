package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;

import net.minecraft.item.Item;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ClayMoldItem extends PlaceableItem {

    public ClayMoldItem() {
        super(Bulk.SMALL, Weighty.TWO_POUND, new Item.Properties()
                .group(ItemGroups.MOLDS).isImmuneToFire());
    }
}
