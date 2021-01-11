package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.blocks.DirtBlock;
import org.crumbleworks.forge.aTFC.itemgroups.ItemGroups;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Dirt implements Wireable {

    public static final RegistryObject<Block> DIRT_BLOCK = BLOCKS
            .register("dirt", () -> new DirtBlock());
    public static final RegistryObject<Item> DIRT_ITEM = ITEMS
            .register("dirt", () -> new BlockItem(DIRT_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));
}
