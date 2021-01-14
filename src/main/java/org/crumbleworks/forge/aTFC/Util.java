package org.crumbleworks.forge.aTFC;

import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Util {

    /**
     * @param marker
     *            an interface that marks the {@link Block Blocks} you want
     * @return a set of blocks that implement the <code>marker</code>
     */
    public static final Set<Block> getBlocks(Class<?> marker) {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(b -> marker.isInstance(b))
                .collect(Collectors.toSet());
    }

    /**
     * @param marker
     *            an interface that marks the {@link Item Items} you want
     * @return a set of items that implement the <code>marker</code>
     */
    public static final Set<Item> getItems(Class<?> marker) {
        return ForgeRegistries.ITEMS.getValues().stream()
                .filter(i -> marker.isInstance(i))
                .collect(Collectors.toSet());
    }
}
