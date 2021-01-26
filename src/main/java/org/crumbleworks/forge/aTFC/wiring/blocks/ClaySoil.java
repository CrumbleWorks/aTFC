package org.crumbleworks.forge.aTFC.wiring.blocks;

import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.blocks.GrasscoverableBlock;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing.WorldItemPlacerTE;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.TintableBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.TileEntitiesMappings;
import org.crumbleworks.forge.aTFC.wiring.items.Clay;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.RandomValueRange;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ClaySoil extends GrassCoverableBlock {

    private static final String name = "claysoil";

    private static final int minClayAmount = 1;
    private static final int maxClayAmount = 3;

    public static final RegistryObject<Block> CLAYSOIL_BLOCK = BLOCKS
            .register(name,
                    () -> new GrasscoverableBlock(AbstractBlock.Properties
                            .create(Materials.CLAY_SOIL)
                            .hardnessAndResistance(0.6F)
                            .sound(SoundType.GROUND)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> CLAYSOIL_ITEM = ITEMS.register(
            name, () -> new TintableBlockItem(CLAYSOIL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    public ClaySoil() {
        super(name, CLAYSOIL_BLOCK);
    }

    @Override
    public void registerTileEntities(TileEntitiesMappings tm) {
        tm.addMapping(WorldItemPlacerTE.class, CLAYSOIL_BLOCK.get());
    }

    @Override
    protected ResourceLocation primaryTexture(BlockModels bm) {
        return bm.modLoc("block/soil");
    }

    @Override
    protected ResourceLocation secondaryTexture(BlockModels bm) {
        return bm.modLoc("block/overlays/clay");
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name,
                LootTable.builder().addLootPool(LootPool.builder()
                        .name(name)
                        .rolls(RandomValueRange.of(minClayAmount,
                                maxClayAmount))
                        .addEntry(ItemLootEntry
                                .builder(Clay.CLAY_ITEM.get()))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(CLAYSOIL_BLOCK.get(), "Clay");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(CLAYSOIL_BLOCK.get(), "Lehmerd\u00e4");
    }

    @Override
    public void registerForItemTags(ItemTags it) {
        it.itemTagBuilder(Tags.Items.CLAY).add(CLAYSOIL_ITEM.get());
    }
}
