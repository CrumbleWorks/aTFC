package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.blocks.DirtBlock;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.DynamicPainter;
import org.crumbleworks.forge.aTFC.itemgroups.ItemGroups;

import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.Direction;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Dirt implements Wireable {

    public static final RegistryObject<Block> DIRT_BLOCK = BLOCKS
            .register("dirt", () -> new DirtBlock());
    public static final RegistryObject<Item> DIRT_ITEM = ITEMS
            .register("dirt", () -> new BlockItem(DIRT_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));
    
    @Override
    public void registerBlockModels(BlockModels bm) {
        bm.getBuilder("dirt")
            .parent(bm.getExistingFile(bm.mcLoc("block/block")))
            .texture("all", bm.mcLoc("block/dirt"))
            .texture("overlay_top", bm.modLoc("tfctng/blocks/grass_top"))
            .texture("overlay_side", bm.modLoc("tfctng/blocks/grass_side"))
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
