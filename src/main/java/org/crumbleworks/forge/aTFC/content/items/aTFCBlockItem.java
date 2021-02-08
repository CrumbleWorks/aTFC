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
    private final Weight weight = Weight.MEDIUM;

    public aTFCBlockItem(Block block,
            Properties properties) {
        super(block, properties);
    }

    @Override
    public Bulk getBulk() {
        return bulk;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }

    @Override
    public int getMaxStackSize() {
        return weight.getStacksize();
    }
}
