package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.skills.Skills;
import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences.TastePreference;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface ATFCPlayerData {

    TastePreference tastePreferences();
    Skills skills();
}
