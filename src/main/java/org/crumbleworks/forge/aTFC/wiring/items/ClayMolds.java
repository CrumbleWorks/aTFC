package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.items.UnfiredClayMoldItem;
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

    private static final String name_axe_mold = "clay_mold/axe";
    private static final String name_chisel_mold = "clay_mold/chisel";
    private static final String name_hammer_mold = "clay_mold/hammer";
    private static final String name_hoe_mold = "clay_mold/hoe";
    private static final String name_knife_mold = "clay_mold/knife";
    private static final String name_pickaxe_mold = "clay_mold/pickaxe";
    private static final String name_prospectorpick_mold = "clay_mold/prospectorpick";
    private static final String name_saw_mold = "clay_mold/saw";
    private static final String name_scythe_mold = "clay_mold/scythe";
    private static final String name_shovel_mold = "clay_mold/shovel";

    private static final String name_javelin_mold = "clay_mold/javelin";
    private static final String name_mace_mold = "clay_mold/mace";
    private static final String name_sword_mold = "clay_mold/sword";

    // TODO coin mold (allows casting 4 coins, consuming 4 units of metal?)
    // #104
    private static final String name_ingot_mold = "clay_mold/ingot";

    public static final RegistryObject<Item> AXE = ITEMS.register(
            name_axe_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> CHISEL = ITEMS.register(
            name_chisel_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> HAMMER = ITEMS.register(
            name_hammer_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> HOE = ITEMS.register(
            name_hoe_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> KNIFE = ITEMS.register(
            name_knife_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> PICKAXE = ITEMS.register(
            name_pickaxe_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> PROSPECTORPICK = ITEMS.register(
            name_prospectorpick_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> SAW = ITEMS.register(
            name_saw_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> SCYTHE = ITEMS.register(
            name_scythe_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> SHOVEL = ITEMS.register(
            name_shovel_mold, () -> new UnfiredClayMoldItem());

    public static final RegistryObject<Item> JAVELIN = ITEMS.register(
            name_javelin_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> MACE = ITEMS.register(
            name_mace_mold, () -> new UnfiredClayMoldItem());
    public static final RegistryObject<Item> SWORD = ITEMS.register(
            name_sword_mold, () -> new UnfiredClayMoldItem());

    public static final RegistryObject<Item> INGOT = ITEMS.register(
            name_ingot_mold, () -> new UnfiredClayMoldItem());

    @Override
    public void generateItemModels(ItemModels im) {
        moldModel(im, name_axe_mold, "axe_head");
        moldModel(im, name_chisel_mold, "chisel_head");
        moldModel(im, name_hammer_mold, "hammer_head");
        moldModel(im, name_hoe_mold, "hoe_head");
        moldModel(im, name_knife_mold, "knife_blade");
        moldModel(im, name_pickaxe_mold, "pick_head");
        moldModel(im, name_prospectorpick_mold, "propick_head");
        moldModel(im, name_saw_mold, "saw_blade");
        moldModel(im, name_scythe_mold, "scythe_blade");
        moldModel(im, name_shovel_mold, "shovel_head");

        moldModel(im, name_javelin_mold, "javelin_head");
        moldModel(im, name_mace_mold, "mace_head");
        moldModel(im, name_sword_mold, "sword_blade");

        moldModel(im, name_ingot_mold, "ingot");
    }

    private static final void moldModel(ItemModels im, String name,
            String tngMoldName) {
        im.getBuilder("item/" + name)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im
                        .modLoc("tfctng/items/ceramics/unfired/mold/"
                                + tngMoldName));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(AXE.get(), "Axe Mold");
        tr.add(CHISEL.get(), "Chisel Mold");
        tr.add(HAMMER.get(), "Hammer Mold");
        tr.add(HOE.get(), "Hoe Mold");
        tr.add(KNIFE.get(), "Knife Mold");
        tr.add(PICKAXE.get(), "Pickaxe Mold");
        tr.add(PROSPECTORPICK.get(), "Prospector's Pick Mold");
        tr.add(SAW.get(), "Saw Mold");
        tr.add(SCYTHE.get(), "Scythe Mold");
        tr.add(SHOVEL.get(), "Shovel Mold");

        tr.add(JAVELIN.get(), "Javelin Mold");
        tr.add(MACE.get(), "Mace Mold");
        tr.add(SWORD.get(), "Sword Mold");

        tr.add(INGOT.get(), "Ingot Mold");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(AXE.get(), "Bieli Gussform");
        tr.add(CHISEL.get(), "Beitel Gussform");
        tr.add(HAMMER.get(), "Hammer Gussform");
        tr.add(HOE.get(), "Hacki Gussform");
        tr.add(KNIFE.get(), "Mässer Gussform");
        tr.add(PICKAXE.get(), "Spitzhacki Gussform");
        tr.add(PROSPECTORPICK.get(), "Schürfhacki Gussform");
        tr.add(SAW.get(), "Sagi Gussform");
        tr.add(SCYTHE.get(), "Sägässä Gussform");
        tr.add(SHOVEL.get(), "Schuflä Gussform");

        tr.add(JAVELIN.get(), "Speer Gussform");
        tr.add(MACE.get(), "Cholbä Gussform");
        tr.add(SWORD.get(), "Schwert Gussform");

        tr.add(INGOT.get(), "Barrä Gussform");
    }

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(AXE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(CHISEL.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(HAMMER.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(HOE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(KNIFE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(PICKAXE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(PROSPECTORPICK.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(SAW.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(SCYTHE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(SHOVEL.get());

        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(JAVELIN.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(MACE.get());
        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(SWORD.get());

        it.itemTagBuilder(Tags.Items.CLAY_MOLD).add(INGOT.get());
    }
}
