package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Flint implements Wireable {

    private static final String name = "flint";

    public static final RegistryObject<Item> FLINT_ITEM = ITEMS.register(name,
            () -> new Item(
                    new Item.Properties().group(ItemGroups.MATERIALS)));

    @Override
    public void generateBlockModels(BlockModels bm) {}

    @Override
    public void generateBlockStates(BlockStates bs) {}

    @Override
    public void generateItemModels(ItemModels im) {
        im.getBuilder(name)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.mcLoc("item/flint"));
    }

    @Override
    public void generateLootTables(LootTables lt) {}

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(FLINT_ITEM.get(), "Flint");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(FLINT_ITEM.get(), "F\\u00fc\\u00fcrstei");
    }
    
    @Override
    public void registerForBlockTags(BlockTags bt) {}
    
    @Override
    public void registerForItemTags(ItemTags it) {}
}
