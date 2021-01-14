package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.data.DataGenerator;
import net.minecraft.util.Direction;
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
    public BlockModels(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        getBuilder("dirt")
            .parent(getExistingFile(mcLoc("block/block")))
            .texture("all", mcLoc("block/dirt"))
            .texture("overlay_top", modLoc("block/tfc/overlays/grass_top"))
            .texture("overlay_side", modLoc("block/tfc/overlays/grass_side"))
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