package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
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
    
    public void createGrassCoverableBlock(String name, ResourceLocation mainTex) {
        getBuilder(name)
            .parent(getExistingFile(mcLoc("block/block")))
            .texture("particle", mainTex)
            .texture("all", mainTex)
            .texture("overlay_top", modLoc("tfctng/blocks/grass_top"))
            .texture("overlay_side", modLoc("tfctng/blocks/grass_side"))
            .element().from(0, 0, 0).to(16, 16, 16)
            .allFaces((d, fb) -> {
                fb.uvs(0, 0, 16, 16)
                  .texture("#all")
                  .cullface(d)
                  .tintindex(DynamicPainter.TINT_SOIL)
                  .end();
            }).end()
            .element().from(0, 0, 0).to(16, 16, 16)
            .face(Direction.NORTH).end()
            .face(Direction.EAST).end()
            .face(Direction.SOUTH).end()
            .face(Direction.WEST).end()
            .faces((d, fb) -> {
                fb.uvs(0, 0, 16, 16)
                  .texture("#overlay_side")
                  .cullface(d)
                  .tintindex(DynamicPainter.TINT_GREENERY)
                  .end();
            })
            .face(Direction.UP)
            .uvs(0, 0, 16, 16)
            .texture("#overlay_top")
            .cullface(Direction.UP)
            .tintindex(DynamicPainter.TINT_GREENERY)
            .end()
            .end();
    }
}
