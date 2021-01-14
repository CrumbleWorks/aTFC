package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.blocks.SoilBlock;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.items.TintableBlockItem;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameters;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Soil implements Wireable {

    private static final String name = "soil";
    public static final RegistryObject<Block> SOIL_BLOCK = BLOCKS
            .register(name, () -> new SoilBlock());
    public static final RegistryObject<Item> SOIL_ITEM = ITEMS
            .register(name, () -> new TintableBlockItem(SOIL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.createGrassCoverableBlock(name, bm.mcLoc("block/dirt"));
    }

    @Override
    public void generateItemModels(ItemModels im) {
        im.createBlockItem(name);
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.getVariantBuilder(SOIL_BLOCK.get())
                .partialState().modelForState()
                .modelFile(bs.models()
                        .getExistingFile(bs.modLoc("block/" + name)))
                .addModel();
    }

    @Override
    public List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> generateLootTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> lootTables = new ArrayList<>();

        lootTables.add(Pair.of(SoilLootTables::new,
                LootTables.createParams(name,
                        (c) -> c.required(LootParameters.BLOCK_STATE)
                                .required(LootParameters.field_237457_g_)
                                .required(LootParameters.TOOL)
                                .optional(LootParameters.THIS_ENTITY)
                                .optional(LootParameters.BLOCK_ENTITY)
                                .optional(LootParameters.EXPLOSION_RADIUS))));

        return lootTables;
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Soil");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Erd\u00e4");
    }

    public class SoilLootTables implements
            Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(
                BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootTable.Builder tableBuilder = LootTable.builder().addLootPool(
                    LootPool.builder().rolls(ConstantRange.of(1)).addEntry(
                            ItemLootEntry.builder(SOIL_BLOCK.get())));
            consumer.accept(SOIL_BLOCK.get().getLootTable(), tableBuilder);
        }
    }
}
