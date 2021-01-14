package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ItemModels extends ItemModelProvider {

    /**
     * @param generator
     * @param modid
     * @param existingFileHelper
     */
    public ItemModels(DataGenerator generator,
            ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for(Wireable wireable : Main.wireables) {
            wireable.generateItemModels(this);
        }
    }

    public void createBlockItem(String name) {
        getBuilder(name).parent(getExistingFile(modLoc("block/" + name)));
    }
}
