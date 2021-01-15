package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.blocks.Tintable;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TintableBlockItem extends BlockItem implements Tintable {

    public TintableBlockItem(Block blockIn, Properties builder) {
        super(blockIn, builder);
    }
}
