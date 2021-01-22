package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class Translations extends LanguageProvider {

    /**
     * @param gen
     * @param modid
     * @param locale
     */
    public Translations(DataGenerator gen, String locale) {
        super(gen, Main.MOD_ID, locale);
    }
    
    @Override
    public String getName() {
        return Main.MOD_ID + " " + super.getName();
    }
}
