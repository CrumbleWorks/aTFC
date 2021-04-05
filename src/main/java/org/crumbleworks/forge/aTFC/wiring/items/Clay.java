package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.Bulky.Bulk;
import org.crumbleworks.forge.aTFC.content.items.Weighty;
import org.crumbleworks.forge.aTFC.content.items.aTFCBaseItem;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Clay implements Wireable {

    private static final String name = "clay";

    public static final RegistryObject<Item> CLAY_ITEM = ITEMS.register(name,
            () -> new aTFCBaseItem(Bulk.SMALL, Weighty.POUND,
                    new Item.Properties().group(ItemGroups.MATERIALS)));

    @Override
    public void generateItemModels(ItemModels im) {
        im.getBuilder(name)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.mcLoc("item/clay_ball"));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(CLAY_ITEM.get(), "Clayball");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(CLAY_ITEM.get(), "Lehmb\u00f6lleli");
    }

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.CLAY_BALL).add(CLAY_ITEM.get());
    }
}
