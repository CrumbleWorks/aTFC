package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.content.blocks.SoilBlock;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.TintableBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Soil extends GrassCoverableBlock {

    private static final String name = "soil";
    public static final RegistryObject<Block> SOIL_BLOCK = BLOCKS
            .register(name, () -> new SoilBlock());
    public static final RegistryObject<Item> SOIL_ITEM = ITEMS
            .register(name, () -> new TintableBlockItem(SOIL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    public Soil() {
        super(name, SOIL_BLOCK);
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name,
                LootTable.builder().addLootPool(LootPool.builder().name(name)
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(SOIL_ITEM.get()))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Soil");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Erd\u00e4");
    }
}
