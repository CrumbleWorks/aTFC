package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
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
    
    public BlockModelBuilder simpleBlock(String name, ResourceLocation tex) {
        return simpleBlock(name, tex, Integer.MAX_VALUE);
    }
    
    /**
     * Setting <code>tintindex</code> to {@link Integer#MAX_VALUE} will not register a tintindex. 
     */
    public BlockModelBuilder simpleBlock(String name, ResourceLocation tex, int tintindex) {
        BlockModelBuilder bmb = getBuilder(name)
                .parent(getExistingFile(mcLoc("block/block")))
                .texture("particle", tex)
                .texture("all", tex)
                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((d, fb) -> {
                    fb.uvs(0, 0, 16, 16)
                            .texture("#all")
                            .cullface(d)
                            .tintindex(tintindex)
                            .end();
                }).end();
        return bmb;
    }
    
    @Override
    public String getName() {
        return Main.MOD_ID + " " + super.getName();
    }
}
