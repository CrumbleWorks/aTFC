package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.WorldPlaceable;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class WorldPlaceableItem extends aTFCBaseItem implements WorldPlaceable {

    public WorldPlaceableItem(Bulk bulk, int weight, Properties properties) {
        super(bulk, weight, properties);
    }
}
