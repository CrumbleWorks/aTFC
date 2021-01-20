package org.crumbleworks.forge.aTFC.wiring.blocks;

import org.crumbleworks.forge.aTFC.content.blocks.UnstableTintableBlock;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.TintableBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.DynamicPainter;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Sand implements Wireable {

    private static final String name = "sand";

    public static final RegistryObject<Block> SAND_BLOCK = BLOCKS
            .register(name,
                    () -> new UnstableTintableBlock(AbstractBlock.Properties
                            .create(Material.SAND, MaterialColor.SAND)
                            .hardnessAndResistance(0.5F)
                            .sound(SoundType.SAND)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> SAND_ITEM = ITEMS.register(
            name,
            () -> new TintableBlockItem(SAND_BLOCK.get(),
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
        bs.simpleState(name, SAND_BLOCK.get());
    }


    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name,
                LootTable.builder().addLootPool(LootPool.builder().name(name)
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(SAND_ITEM.get()))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(SAND_BLOCK.get(), "Sand");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(SAND_BLOCK.get(), "Sand");
    }
}
