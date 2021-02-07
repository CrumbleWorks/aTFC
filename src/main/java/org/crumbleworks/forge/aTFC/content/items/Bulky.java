package org.crumbleworks.forge.aTFC.content.items;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface Bulky {

    /**
     * Lets a container decide, if it is big enough to hold the item. E.g.
     * {@link #VERY_LARGE} and bigger items don't fit into conventional
     * containers (e.g. chests) at all.
     */
    public static enum Bulk {
        /** Pebbles, small gems and such */
        TINY,
        /** Can comfortably fit a human palm (e.g. a rock, a potato, ...) */
        VERY_SMALL,
        /** Comparable to a human (fore)arm (e.g. sticks, a squash, ...) */
        SMALL,
        /**
         * About a quarter of a minecraft block (e.g. a jug, flower pot, ...)
         */
        NORMAL,
        /** Half a minecraft block (e.g. a log, a bucket, ...) */
        LARGE,
        /**
         * About the size of a minecraft block (e.g. a barrel, a chest, ...)
         */
        VERY_LARGE,
        /** Larger than a minecraft block (e.g. a waterwheel, ...) */
        HUGE
    }

    Bulk getBulk();
}
