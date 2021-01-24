package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing;


/**
 * Marks an Item as being able to be placed as a nonblock-thingy. That is, it
 * can be placed in the world without it actively modifying the block it is
 * placed on or simiarl, just placed, like a block, but not a block.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface NonBlockPlaceable {

    /**
     * Items can be either <code>large</code> or <code>small</code> when
     * placed in the world as nonblock-thingies. As everything is in
     * blockspace, this changes how big an item will be rendered. In general
     * an item is considered to be <code>large</code>, that measn it takes as
     * much space as a block would. Items can also be <code>small</code> which
     * means it takes up roughly an eigth of a block, or thus a quarter of a
     * blockside. Due to the nature of things, either 1 <code>large</code> or
     * 4 <code>small</code> items can be placed on any given block.
     * 
     * @return <code>true</code> if an item would take as much space as a
     *         regular block
     */
    default boolean isLarge() {
        return true;
    }
}
