package org.crumbleworks.forge.aTFC.wiring;


import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

import com.mojang.datafixers.util.Pair;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * Marks classes that should be wired using the {@link Wiring} class.
 * These classes get instantiated in order to run them through the
 * classloader.
 * <p>
 * Holds all needed {@link DeferredRegister}s for easy reference.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface Wireable {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister
            .create(ForgeRegistries.BLOCKS, Main.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister
            .create(ForgeRegistries.ITEMS, Main.MOD_ID);

    void generateBlockModels(BlockModels bm);
    void generateItemModels(ItemModels im);
    
    void generateBlockStates(BlockStates bs);
    
    List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> generateLootTables();

    void englishTranslations(Translations tren);
    void swissTranslations(Translations trch);
}
