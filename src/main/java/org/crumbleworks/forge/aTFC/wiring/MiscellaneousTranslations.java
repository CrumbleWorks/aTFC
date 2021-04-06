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
        
        tr.add("itemroup.atfc_blocks", "aTFC Block");
        tr.add("itemGroup.atfc_materials", "aTFC Materials");
        tr.add("itemGroup.atfc_molds", "aTFC Molds");
        
        tr.add("key.atfc.categories.atfc_bindings", "aTFC Keybindings");
    }
    
    @Override
    public void swissTranslations(Translations tr) {
        tr.add("generator.atfc.continental", "aTFC Kontinente");
        
        tr.add("itemGroup.atfc_blocks", "aTFC Bl\u00f6ck");
        tr.add("itemGroup.atfc_materials", "aTFC Materialie");
        tr.add("itemGroup.atfc_molds", "aTFC Gussforme");
        
        tr.add("key.atfc.categories.atfc_bindings", "aTFC Taschtäbindige");
    }
}
