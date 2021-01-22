package org.crumbleworks.forge.aTFC.wiring.items;

import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.item.Items;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class MinecraftShovels implements Wireable {

    @Override
    public void generateBlockModels(BlockModels bm) {}

    @Override
    public void generateBlockStates(BlockStates bs) {}

    @Override
    public void generateItemModels(ItemModels im) {}

    @Override
    public void generateLootTables(LootTables lt) {}

    @Override
    public void englishTranslations(Translations tr) {}

    @Override
    public void swissTranslations(Translations tr) {}

    @Override
    public void registerForBlockTags(BlockTags bt) {}

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.SHOVELS).add(Items.WOODEN_SHOVEL)
                .add(Items.STONE_SHOVEL).add(Items.IRON_SHOVEL)
                .add(Items.GOLDEN_SHOVEL).add(Items.DIAMOND_SHOVEL)
                .add(Items.NETHERITE_SHOVEL);
    }
}
