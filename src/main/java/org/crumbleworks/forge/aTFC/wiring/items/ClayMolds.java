package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.items.ClayMoldItem;
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
public class ClayMolds implements Wireable {

    private static final String name_axe_mold = "clay_mold.axe";
    private static final String name_chisel_mold = "clay_mold.chisel";
    private static final String name_hammer_mold = "clay_mold.hammer";
    private static final String name_hoe_mold = "clay_mold.hoe";
    private static final String name_knife_mold = "clay_mold.knife";
    private static final String name_pickaxe_mold = "clay_mold.pickaxe";
    private static final String name_prospectorpick_mold = "clay_mold.prospectorpick";
    private static final String name_saw_mold = "clay_mold.saw";
    private static final String name_scythe_mold = "clay_mold.scythe";
    private static final String name_shovel_mold = "clay_mold.shovel";

    private static final String name_javelin_mold = "clay_mold.javelin";
    private static final String name_mace_mold = "clay_mold.mace";
    private static final String name_sword_mold = "clay_mold.sword";

    // TODO coin mold (allows casting 4 coins, consuming 4 units of metal?)
    // #104
    private static final String name_ingot_mold = "clay_mold.ingot";

    public static final RegistryObject<Item> INGOT = ITEMS.register(
            name_ingot_mold, () -> new ClayMoldItem());

    @Override
    public void generateItemModels(ItemModels im) {
        im.getBuilder(name_ingot_mold)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im
                        .modLoc("tfctng/items/ceramics/unfired/mold/ingot"));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(INGOT.get(), "Ingot Mold");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(INGOT.get(), "Barrä Gussform");
    }

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(INGOT.get());
    }
}
