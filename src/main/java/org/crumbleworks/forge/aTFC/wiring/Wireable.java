package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.Main;

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
}
