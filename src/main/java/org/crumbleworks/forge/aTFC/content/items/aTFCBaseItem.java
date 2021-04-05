package org.crumbleworks.forge.aTFC.content.items;

import net.minecraft.item.Item;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCBaseItem extends Item implements Bulky, Weighty {

    private final Bulk bulk;
    private final int weight;

    public aTFCBaseItem(Bulk bulk, int weight, Properties properties) {
        super(properties);
        this.bulk = bulk;
        this.weight = weight;
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
        return bulk.getStacksize();
    }
}
