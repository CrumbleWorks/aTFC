package org.crumbleworks.forge.aTFC.content.itemgroups;

import org.crumbleworks.forge.aTFC.wiring.blocks.Soil;
import org.crumbleworks.forge.aTFC.wiring.items.Clay;

import net.minecraft.item.ItemGroup;

//TODO #22
/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ItemGroups {

    //Register Translations in MiscellaneousTranslations.java
    public static final ItemGroup BLOCKS = new ModItemGroup("blocks", Soil.SOIL_ITEM);
    public static final ItemGroup MATERIALS = new ModItemGroup("materials", Clay.CLAY_ITEM);
}
