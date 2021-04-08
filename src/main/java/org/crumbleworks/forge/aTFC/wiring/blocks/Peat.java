package org.crumbleworks.forge.aTFC.wiring.blocks;

import java.util.function.Consumer;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.blocks.BogBlock;
import org.crumbleworks.forge.aTFC.content.gamelogic.drying.DryingCapabilityProvider;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.Bulky.Bulk;
import org.crumbleworks.forge.aTFC.content.items.CurableBrickItem;
import org.crumbleworks.forge.aTFC.content.items.Weighty;
import org.crumbleworks.forge.aTFC.content.items.aTFCBaseItem;
import org.crumbleworks.forge.aTFC.content.items.aTFCBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Recipes;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemModelsProperties;
import net.minecraft.loot.AlternativesLootEntry;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.loot.TableLootEntry;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Peat extends GrassCoverableBlock {

    private static final String name_peat_block = "peat";
    private static final String name_peat_clod = "peat_clod";
    private static final String name_peat_brick = "peat_brick";
    private static final String name_peat_brick_dried = "peat_brick_dried";
    private static final String name_peat_brick_3D = "peat_brick_3d";
    private static final String name_peat_brick_dried_3D = "peat_brick_dried_3d";

    private static final int minPeatClods = 3;
    private static final int maxPeatClods = 7;
    private static final int minPeatAmountWithShovel = 3;
    private static final int maxPeatAmountWithShovel = 5;

    private static final int peatClodsPerBrick = 2;

    private static final ResourceLocation brick_3D = new ResourceLocation(
            Main.MOD_ID, "item/" + name_peat_brick_3D);
    private static final ResourceLocation brick_dry_3D = new ResourceLocation(
            Main.MOD_ID, "item/" + name_peat_brick_dried_3D);


    private static final float dryingThreshold = 0.8f;
    /*
     * Realistically drying peat takes 4-6 weeks at least...
     * source: http://www.zeno.org/Lueger-1904/A/Torfgewinnung+und+-verwertung
     */
    private static final float hoursUntilDriedInSun = 24 * 7 * 4;

    public static final RegistryObject<Block> PEAT_BLOCK = BLOCKS
            .register(name_peat_block,
                    () -> new BogBlock(AbstractBlock.Properties
                            .create(Materials.PEAT)
                            .hardnessAndResistance(0.6F)
                            .sound(SoundType.SOUL_SAND)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> PEAT_ITEM = ITEMS.register(
            name_peat_block, () -> new aTFCBlockItem(PEAT_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    public static final RegistryObject<Item> PEAT_CLOD_ITEM = ITEMS.register(
            name_peat_clod,
            () -> new aTFCBaseItem(Bulk.SMALL, Weighty.TWO_POUND,
                    new Item.Properties().group(ItemGroups.MATERIALS)));
    public static final RegistryObject<Item> PEAT_BRICK_ITEM = ITEMS.register(
            name_peat_brick,
            () -> new CurableBrickItem(Bulk.MEDIUM, Weighty.TWENTY_POUND,
                    new Item.Properties().group(ItemGroups.MATERIALS),
                    hoursUntilDriedInSun, brick_3D, brick_dry_3D,
                    dryingThreshold, 0.5f));

    public Peat() {
        super(name_peat_block, PEAT_BLOCK, false);
    }

    @Override
    protected ResourceLocation primaryTexture(BlockModels bm) {
        return bm.modLoc("block/peat");
    }

    @Override
    public void generateItemModels(ItemModels im) {
        super.generateItemModels(im);

        im.getBuilder(name_peat_clod)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/peat_clod"));

        im.getBuilder(name_peat_brick_dried)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/peat_brick_dried"));

        im.getBuilder(name_peat_brick)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/peat_brick_fresh"))
                .override()
                .predicate(new ResourceLocation(Main.MOD_ID, "drying"),
                        dryingThreshold)
                .model(im.getExistingFile(
                        im.modLoc("item/" + name_peat_brick_dried)))
                .end();

        // 3D models
        im.getBuilder(name_peat_brick_3D)
                .parent(im.getExistingFile(
                        im.modLoc("item/template_brick_tall")))
                .texture("tex", im.modLoc("block/peat"));

        im.getBuilder(name_peat_brick_dried_3D)
                .parent(im.getExistingFile(
                        im.modLoc("item/template_brick_tall")))
                .texture("tex", im.modLoc("block/peat_dry"));
    }

    @Override
    public void registerSpecialModels(ModelLoader ml) {
        ModelLoader.addSpecialModel(brick_3D);
        ModelLoader.addSpecialModel(brick_dry_3D);
    }

    @Override
    public void registerItemProperties() {
        ItemModelsProperties.registerProperty(PEAT_BRICK_ITEM.get(),
                new ResourceLocation(Main.MOD_ID, "drying"),
                (stack, world, living) -> {
                    return stack.getCapability(
                            DryingCapabilityProvider.CAPABILITY_DRYING)
                            .resolve().get().getDryingProgress();
                });
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name_peat_brick, LootTable.builder()
                .addLootPool(LootPool.builder().name(name_peat_brick)
                        .rolls(RandomValueRange.of(
                                minPeatAmountWithShovel,
                                maxPeatAmountWithShovel))
                        .addEntry(ItemLootEntry
                                .builder(PEAT_BRICK_ITEM.get()))));

        lt.addBlock(name_peat_clod, LootTable.builder()
                .addLootPool(LootPool.builder().name(name_peat_clod)
                        .rolls(RandomValueRange.of(
                                minPeatClods,
                                maxPeatClods))
                        .addEntry(ItemLootEntry
                                .builder(PEAT_CLOD_ITEM.get()))));

        lt.addBlock(name_peat_block, LootTable.builder()
                .addLootPool(LootPool.builder().name(name_peat_block)
                        .rolls(ConstantRange.of(1))
                        .addEntry(AlternativesLootEntry.builder(
                                new LootEntry.Builder<?>[] {
                                        TableLootEntry
                                                .builder(new ResourceLocation(
                                                        Main.MOD_ID,
                                                        "blocks/"
                                                                + name_peat_brick))
                                                .acceptCondition(
                                                        MatchTool.builder(
                                                                ItemPredicate.Builder
                                                                        .create()
                                                                        .tag(Tags.Items.SHOVELS))),
                                        TableLootEntry
                                                .builder(new ResourceLocation(
                                                        Main.MOD_ID,
                                                        "blocks/"
                                                                + name_peat_clod))
                                }))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(PEAT_BLOCK.get(), "Peat");
        tr.add(PEAT_CLOD_ITEM.get(), "Peat Clod");
        tr.add(PEAT_BRICK_ITEM.get(), "Peat Block");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(PEAT_BLOCK.get(), "Torf");
        tr.add(PEAT_CLOD_ITEM.get(), "Torf Chlump\00e4");
        tr.add(PEAT_BRICK_ITEM.get(), "Torf Block");
    }

    @Override
    public void registerRecipes(Recipes re,
            Consumer<IFinishedRecipe> consumer) {
        re.shapelessRecipe(PEAT_BRICK_ITEM.get())
                .addIngredient(PEAT_CLOD_ITEM.get(), peatClodsPerBrick)
                .addCriterion("has_peat_clod",
                        re.triggerWhenHasItem(PEAT_CLOD_ITEM.get()))
                .build(consumer);
        re.shapelessRecipe(PEAT_CLOD_ITEM.get(), peatClodsPerBrick)
                .addIngredient(PEAT_BRICK_ITEM.get())
                .addCriterion("has_fresh_peat",
                        re.triggerWhenHasItem(PEAT_BRICK_ITEM.get()))
                .build(consumer);
    }
}
