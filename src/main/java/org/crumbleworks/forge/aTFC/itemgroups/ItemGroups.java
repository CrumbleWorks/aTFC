package org.crumbleworks.forge.aTFC.itemgroups;

import org.crumbleworks.forge.aTFC.wiring.Dirt;

import net.minecraft.item.ItemGroup;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ItemGroups {

    public static final ItemGroup BLOCKS = new ModItemGroup("blocks",
            Dirt.DIRT_ITEM);
}
