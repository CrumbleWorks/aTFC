package org.crumbleworks.forge.aTFC.content.items;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.WorldPlaceableItem;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class PlaceableItem extends aTFCBaseItem implements WorldPlaceableItem {

    public PlaceableItem(Bulk bulk, int weight, Properties properties) {
        super(bulk, weight, properties);
    }
}
