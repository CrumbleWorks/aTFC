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
    private final Weight weight;

    public aTFCBaseItem(Bulk bulk, Weight weight, Properties properties) {
        super(properties);
        this.bulk = bulk;
        this.weight = weight;
    }

    @Override
    public Bulk getBulk() {
        return bulk;
    }

    @Override
    public Weight getWeight() {
        return weight;
    }
}
