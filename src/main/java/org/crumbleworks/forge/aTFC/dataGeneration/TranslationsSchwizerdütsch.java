package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TranslationsSchwizerd�tsch extends Translations {

    /**
     * @param gen
     * @param modid
     * @param locale
     */
    public TranslationsSchwizerd�tsch(DataGenerator gen) {
        super(gen, "de_ch");
    }

    @Override
    protected void addTranslations() {
        for(Wireable wireable : Main.wireables) {
            wireable.swissTranslations(this);
        }
    }
}
