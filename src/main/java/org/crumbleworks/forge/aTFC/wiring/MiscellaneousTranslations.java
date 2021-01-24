package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class MiscellaneousTranslations implements Wireable {

    @Override
    public void englishTranslations(Translations tr) {
        tr.add("generator.atfc.continental", "aTFC Continental");
        tr.add("itemGroup.atfc.blocks", "aTFC Block");
        tr.add("itemGroup.atfc.meterials", "aTFC Materials");
    }
    
    @Override
    public void swissTranslations(Translations tr) {
        tr.add("generator.atfc.continental", "aTFC Kontinente");
        tr.add("itemGroup.atfc.blocks", "aTFC Bl\u00f6ck");
        tr.add("itemGroup.atfc.meterials", "aTFC Materialie");
    }
}
