package org.crumbleworks.forge.aTFC.content.items;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface Weighty {

    /**
     * Determines the stacksize of items.
     */
    public static enum Weight {

        /** 64 per stack */
        VERY_LIGHT(64),
        /** 32 per stack */
        LIGHT(32),
        /** 16 per stack */
        MEDIUM(16),
        /** 4 per stack */
        HEAVY(4),
        /** 1 per stack */
        VERY_HEAVY(1);

        private final int stacksize;

        private Weight(int stacksize) {
            this.stacksize = stacksize;
        }

        public int getStacksize() {
            return stacksize;
        }
    }

    Weight getWeight();
}
