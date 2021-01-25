package org.crumbleworks.forge.aTFC.wiring.blocks;

import java.util.function.Consumer;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.blocks.BogBlock;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
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
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Peat extends GrassCoverableBlock {

    private static final String name_peat_block = "peat";
    private static final String name_peat_clod = "peat_clod";
    private static final String name_peat_fresh = "peat_fresh";
    private static final String name_peat_dried = "peat_dried";

    private static final int minPeatClods = 3;
    private static final int maxPeatClods = 7;
    private static final int minPeatAmountWithShovel = 3;
    private static final int maxPeatAmountWithShovel = 5;

    private static final int peatClodsPerBrick = 2;

    public static final RegistryObject<Block> PEAT_BLOCK = BLOCKS
            .register(name_peat_block,
                    () -> new BogBlock(AbstractBlock.Properties
                            .create(Materials.PEAT)
                            .hardnessAndResistance(0.6F)
                            .sound(SoundType.SOUL_SAND)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> PEAT_ITEM = ITEMS.register(
            name_peat_block, () -> new BlockItem(PEAT_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    public static final RegistryObject<Item> PEAT_CLOD_ITEM = ITEMS.register(
            name_peat_clod, () -> new Item(
                    new Item.Properties().group(ItemGroups.MATERIALS)));
    public static final RegistryObject<Item> FRESH_PEAT_ITEM = ITEMS.register(
            name_peat_fresh, () -> new Item(
                    new Item.Properties().group(ItemGroups.MATERIALS)));
    public static final RegistryObject<Item> DRIED_PEAT_ITEM = ITEMS.register(
            name_peat_dried, () -> new Item(
                    new Item.Properties().group(ItemGroups.MATERIALS)));

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

        im.getBuilder(name_peat_fresh)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/peat_brick_fresh"));

        im.getBuilder(name_peat_dried)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/peat_brick_dried"));
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name_peat_fresh, LootTable.builder()
                .addLootPool(LootPool.builder().name(name_peat_fresh)
                        .rolls(RandomValueRange.of(
                                minPeatAmountWithShovel,
                                maxPeatAmountWithShovel))
                        .addEntry(ItemLootEntry
                                .builder(FRESH_PEAT_ITEM.get()))));

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
                                                                + name_peat_fresh))
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
        tr.add(FRESH_PEAT_ITEM.get(), "Fresh Peat");
        tr.add(DRIED_PEAT_ITEM.get(), "Dried Peat");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(PEAT_BLOCK.get(), "Torf");
        tr.add(PEAT_CLOD_ITEM.get(), "Torf Chlump\00e4");
        tr.add(FRESH_PEAT_ITEM.get(), "Fr\00fcsch\00e4 Torf");
        tr.add(DRIED_PEAT_ITEM.get(), "Tr\00f6chn\00e4t\00e4 Torf");
    }
    
    @Override
    public void registerRecipes(Recipes re,
            Consumer<IFinishedRecipe> consumer) {
        re.shapelessRecipe(FRESH_PEAT_ITEM.get())
                .addIngredient(PEAT_CLOD_ITEM.get(), peatClodsPerBrick)
                .addCriterion("has_peat_clod",
                        re.triggerWhenHasItem(PEAT_CLOD_ITEM.get()))
                .build(consumer);
        re.shapelessRecipe(PEAT_CLOD_ITEM.get(), peatClodsPerBrick)
                .addIngredient(FRESH_PEAT_ITEM.get())
                .addCriterion("has_fresh_peat",
                        re.triggerWhenHasItem(FRESH_PEAT_ITEM.get()))
                .build(consumer);
    }
}
