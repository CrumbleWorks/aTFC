package org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying;

import net.minecraft.item.Item;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class DryableBrickItem extends Item {
    public DryableBrickItem(Properties properties) {
        super(properties);
    }
    
    //FIXME add capability for remaining drying time
    //FIXME how do we transform this from a wet to the corresponding dry block
}
