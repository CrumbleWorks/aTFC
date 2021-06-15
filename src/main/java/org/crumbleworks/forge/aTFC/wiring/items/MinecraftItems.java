package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.item.Items;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class MinecraftItems implements Wireable {

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.CLAY).add(Items.CLAY);
        it.itemTagBuilder(Tags.Items.CLAY_BALL).add(Items.CLAY_BALL);
        it.itemTagBuilder(Tags.Items.FLINT).add(Items.FLINT);
        it.itemTagBuilder(Tags.Items.GRAVEL).add(Items.GRAVEL);
        it.itemTagBuilder(Tags.Items.SAND).add(Items.SAND).add(Items.RED_SAND);
        it.itemTagBuilder(Tags.Items.SOIL).add(Items.DIRT).add(Items.COARSE_DIRT);

        it.itemTagBuilder(Tags.Items.SHOVELS).add(Items.WOODEN_SHOVEL)
                .add(Items.STONE_SHOVEL).add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL).add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);
        it.itemTagBuilder(Tags.Items.HATCHETS).add(Items.WOODEN_AXE)
                .add(Items.STONE_AXE).add(Items.IRON_AXE)
                .add(Items.GOLDEN_AXE).add(Items.DIAMOND_AXE)
                .add(Items.NETHERITE_AXE);
    }
}
