package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing;


/**
 * Marks Items that can be placed in the gameworld (instead of being only
 * stored in containers, or becoming blocks).
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface WorldPlaceableItem {

    public enum Size {
        SMALL, LARGE
    }

    /**
     * @return the {@link Size} of this item. A {@link Size#LARGE} item takes
     *         up about the space of a block.
     */
    public Size getItemSize();
}
