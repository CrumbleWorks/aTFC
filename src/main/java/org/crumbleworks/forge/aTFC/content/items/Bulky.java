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
     * <p>
     * Also determines a rough stacksize by making assumptions about how many
     * items of a bulk might roughly fit into a minecraft block. Items can
     * have smaller stacksizes.
     */
    public static enum Bulk {

        /** Pebbles, small gems and such */
        TINY(64),
        /** Can comfortably fit a human palm (e.g. a rock, a potato, ...) */
        VERY_SMALL(32),
        /** Comparable to a human (fore)arm (e.g. sticks, a squash, ...) */
        SMALL(16),
        /** An eighth of a minecraft block (e.g. a jug, flower pot, ...) */
        MEDIUM(8),
        /** A quarter of a minecraft block (e.g. a log, a vessel, ...) */
        NORMAL(4),
        /** About half a minecraft block (e.g. a potato sack, ...) */
        LARGE(2),
        /** About a minecraft block (e.g. a barrel, a chest, ...) */
        VERY_LARGE(1),
        /** Larger than a minecraft block (e.g. a waterwheel, ...) */
        HUGE(1);

        private final int stacksize;

        private Bulk(int stacksize) {
            this.stacksize = stacksize;
        }

        public int getStacksize() {
            return stacksize;
        }

        public final boolean isSmallerThan(Bulk bulk) {
            return this.ordinal() < bulk.ordinal();
        }

        public final boolean isLargerThan(Bulk bulk) {
            return this.ordinal() > bulk.ordinal();
        }
    }

    Bulk getBulk();
}
