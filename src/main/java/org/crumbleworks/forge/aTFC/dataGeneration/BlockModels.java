package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BlockModels extends BlockModelProvider {

    /**
     * @param generator
     * @param modid
     * @param existingFileHelper
     */
    public BlockModels(DataGenerator generator,
            ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(Wireable wireable : Main.wireables) {
            wireable.generateBlockModels(this);
        }
    }
}
