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
public class TranslationsUSEnglish extends Translations {

    /**
     * @param gen
     * @param modid
     * @param locale
     */
    public TranslationsUSEnglish(DataGenerator gen) {
        super(gen, "en_us");
    }

    @Override
    protected void addTranslations() {
        for(Wireable wireable : Main.wireables) {
            wireable.englishTranslations(this);
        }
    }
}
