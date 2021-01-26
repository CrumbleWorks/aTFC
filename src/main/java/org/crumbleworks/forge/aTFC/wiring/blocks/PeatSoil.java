package org.crumbleworks.forge.aTFC.wiring.blocks;

import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.blocks.BogBlock;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing.WorldItemPlacerTE;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.content.items.TintableBlockItem;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.TileEntitiesMappings;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class PeatSoil extends GrassCoverableBlock {

    private static final String name = "peatsoil";

    private static final int soilWeight = 3;
    private static final int peatClodWeight = 1;

    public static final RegistryObject<Block> PEATSOIL_BLOCK = BLOCKS
            .register(name,
                    () -> new BogBlock(AbstractBlock.Properties
                            .create(Materials.PEAT)
                            .hardnessAndResistance(0.6F)
                            .sound(SoundType.SOUL_SOIL)
                            .harvestTool(ToolType.SHOVEL)));
    public static final RegistryObject<Item> PEATSOIL_ITEM = ITEMS.register(
            name, () -> new TintableBlockItem(PEATSOIL_BLOCK.get(),
                    new Item.Properties().group(ItemGroups.BLOCKS)));

    public PeatSoil() {
        super(name, PEATSOIL_BLOCK);
    }

    @Override
    public void registerTileEntities(TileEntitiesMappings tm) {
        tm.addMapping(WorldItemPlacerTE.class, PEATSOIL_BLOCK.get());
    }

    @Override
    protected ResourceLocation primaryTexture(BlockModels bm) {
        return bm.modLoc("block/soil");
    }

    @Override
    protected ResourceLocation secondaryTexture(BlockModels bm) {
        return bm.modLoc("block/overlays/peat");
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addBlock(name,
                LootTable.builder().addLootPool(LootPool.builder()
                        .name(name)
                        .rolls(ConstantRange.of(1))
                        .addEntry(ItemLootEntry.builder(Soil.SOIL_ITEM.get())
                                .weight(soilWeight))
                        .addEntry(ItemLootEntry
                                .builder(Peat.PEAT_CLOD_ITEM.get())
                                .weight(peatClodWeight))));
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add(PEATSOIL_BLOCK.get(), "Bog Soil");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add(PEATSOIL_BLOCK.get(), "Brucherd\u00e4");
    }
}
