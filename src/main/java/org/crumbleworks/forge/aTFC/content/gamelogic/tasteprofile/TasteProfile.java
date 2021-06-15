package org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile;

import java.util.Map;
import java.util.Set;

// TODO write code that handles combining tasteprofiles
// -> factor in facts such as that sourness reduces saltiness, etc.

/**
 * The tasteprofile of a fooditem. When cooking/combining fooditems,
 * tasteprofiles should be added (excluding specific rules such as sourness
 * numbing the saltyness, etc.)...
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface TasteProfile {

    Map<Taste, Integer> getTastes();

    Set<Flavour> getFlavours();
}
