package org.crumbleworks.forge.aTFC.content.gamelogic.drying.bricks;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.Materials;
import org.crumbleworks.forge.aTFC.content.blocks.aTFCSpecialInventoryBlock;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.wiring.TileEntityRenderers;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDryingWiringAndEvents implements Wireable {

    private static final String name = "brick_drying";

    public static final RegistryObject<Block> BRICKDRYING_BLOCK = BLOCKS
            .register(name, () -> new aTFCSpecialInventoryBlock(
                    AbstractBlock.Properties.create(Materials.ABSTRACT_BLOCKS)
                            .zeroHardnessAndResistance().sound(SoundType.BONE)
                            .doesNotBlockMovement(),
                    () -> new BrickDryPlacerTE(),
                    BrickDryPlacerTE.class));

    public static final RegistryObject<TileEntityType<BrickDryPlacerTE>> BRICK_PLACER_TE = TILE_ENTITIES
            .register(name, () -> TileEntityType.Builder
                    .create(BrickDryPlacerTE::new, BRICKDRYING_BLOCK.get())
                    .build(null));

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void createPlacementStorage(
                PlayerInteractEvent.RightClickBlock event) {
            aTFCSpecialInventoryBlock.firstItemCreationLogic(event,
                    BRICKDRYING_BLOCK.get(), DryableBrick.class);
        }
    }

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.getBuilder(name); // empty model
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.simpleState(name, BRICKDRYING_BLOCK.get());
    }

    @Override
    public void registerTileEntityRenderers(TileEntityRenderers ter) {
        ter.register(BRICK_PLACER_TE.get(), BrickDryPlacerTER::new);
    }
}
