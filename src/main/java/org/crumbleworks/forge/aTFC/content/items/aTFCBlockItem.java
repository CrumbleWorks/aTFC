package org.crumbleworks.forge.aTFC.content.items;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCBlockItem extends BlockItem implements Bulky, Weighty {

    // size and weight are chosen to be able to place stacks of material in
    // chests without too much hassle
    private final Bulk bulk = Bulk.LARGE;
    private final int stacksize = 16;
    private final int weight = Weighty.HALF_TON;

    public aTFCBlockItem(Block block,
            Properties properties) {
        super(block, properties);
    }

    @Override
    public Bulk getBulk() {
        return bulk;
    }

    @Override
    public int getWeight() {
        return weight;
    }

    @Override
    public int getMaxStackSize() {
        return stacksize;
    }
}
