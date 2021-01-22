package org.crumbleworks.forge.aTFC.wiring;


import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
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
    
    void generateLootTables(LootTables lt);

    void englishTranslations(Translations tren);
    void swissTranslations(Translations trch);
    
    void registerForBlockTags(BlockTags bt);
    void registerForItemTags(ItemTags it);
}
