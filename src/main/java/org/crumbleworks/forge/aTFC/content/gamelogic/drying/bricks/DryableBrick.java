package org.crumbleworks.forge.aTFC.content.gamelogic.drying.bricks;

import org.crumbleworks.forge.aTFC.content.gamelogic.drying.Dryable;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface DryableBrick extends Dryable {
    
    float dryingThreshold();
    float renderYOffset();
}
