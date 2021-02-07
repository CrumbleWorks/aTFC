package org.crumbleworks.forge.aTFC.wiring.blocks;

import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.blocks.UnstableTintableBlock;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.TintableBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.DynamicPainter;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;
import org.crumbleworks.forge.aTFC.wiring.items.Flint;

import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.conditions.MatchTool;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Gravel implements Wireable {

    private static final String name = "gravel";

    private static final int gravelWeight = 9;
    private static final int flintWeight = 1;

    public static final RegistryObject<Block> GRAVEL_BLOCK = BLOCKS
            .register(name,
                    () -> new UnstableTintableBlock(AbstractBlock.Properties
                            .create(Materials.GRAVEL)
                            .hardnessAndResistance(0.6F)
                            .sound(SoundType.GROUND)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> GRAVEL_ITEM = ITEMS.register(
            name,
            () -> new TintableBlockItem(GRAVEL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.simpleBlock(name, bm.modLoc("block/" + name),
                DynamicPainter.TINT_SOIL);
    }

    @Override
    public void generateItemModels(ItemModels im) {
        im.createBlockItem(name);
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.simpleState(name, GRAVEL_BLOCK.get());
    }


    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name,
                LootTable.builder().addLootPool(LootPool.builder().name(name)
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(GRAVEL_ITEM.get())
                                .weight(gravelWeight))
                        .addEntry(ItemLootEntry
                                .builder(Flint.FLINT_ITEM.get())
                                .weight(flintWeight)
                                .acceptCondition(MatchTool.builder(
                                        ItemPredicate.Builder.create()
                                                .tag(Tags.Items.SHOVELS))))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(GRAVEL_BLOCK.get(), "Gravel");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(GRAVEL_BLOCK.get(), "Gravel");
    }

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.GRAVEL).add(GRAVEL_ITEM.get());
    }
}
