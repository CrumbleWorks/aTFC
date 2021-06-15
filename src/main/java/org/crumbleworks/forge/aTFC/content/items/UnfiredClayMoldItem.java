package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.WorldPlaceable;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;

import net.minecraft.item.Item;

import org.crumbleworks.forge.aTFC.content.items.Bulky.Bulk;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class UnfiredClayMoldItem extends aTFCBaseItem
        implements WorldPlaceable {

    public UnfiredClayMoldItem() {
        super(Bulk.MEDIUM, Weighty.TEN_POUND, new Item.Properties()
                .group(ItemGroups.MOLDS).isImmuneToFire());
    }

    @Override
    public int getMaxStackSize() {
        return 1;
    }
}
