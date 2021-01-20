package org.crumbleworks.forge.aTFC.content.itemgroups;

import org.crumbleworks.forge.aTFC.wiring.Soil;

import net.minecraft.item.ItemGroup;

//TODO #22
/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ItemGroups {

    public static final ItemGroup BLOCKS = new ModItemGroup("blocks", Soil.SOIL_ITEM);
}
