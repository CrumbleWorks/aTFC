package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.blocks.SoilBlock;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.items.TintableBlockItem;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Soil implements Wireable {

    private static final String name = "soil";
    public static final RegistryObject<Block> SOIL_BLOCK = BLOCKS
            .register(name, () -> new SoilBlock());
    public static final RegistryObject<Item> SOIL_ITEM = ITEMS
            .register(name, () -> new TintableBlockItem(SOIL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.createGrassCoverableBlock(name, bm.mcLoc("block/dirt"));
    }

    @Override
    public void generateItemModels(ItemModels im) {
        im.createBlockItem(name);
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.getVariantBuilder(SOIL_BLOCK.get())
                .partialState().modelForState()
                .modelFile(bs.models()
                        .getExistingFile(bs.modLoc("block/" + name)))
                .addModel();
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Soil");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(SOIL_BLOCK.get(), "Erd\u00e4");
    }
}
