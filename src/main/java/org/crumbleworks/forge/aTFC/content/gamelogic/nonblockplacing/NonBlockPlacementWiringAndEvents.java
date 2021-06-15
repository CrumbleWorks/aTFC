package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

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
public class NonBlockPlacementWiringAndEvents implements Wireable {

    private static final String name = "nonblockplacement";

    public static final RegistryObject<Block> NONBLOCKPLACEMENT_BLOCK = BLOCKS
            .register(name, () -> new aTFCSpecialInventoryBlock(
                    AbstractBlock.Properties.of(Materials.ABSTRACT_BLOCKS)
                            .instabreak()
                            .sound(SoundType.GLASS)
                            .noCollission(),
                    () -> new NonBlockPlacementTE(),
                    NonBlockPlacementTE.class));

    public static final RegistryObject<TileEntityType<NonBlockPlacementTE>> NON_BLOCK_PLACER_TE = TILE_ENTITIES
            .register(name,
                    () -> TileEntityType.Builder
                            .of(NonBlockPlacementTE::new,
                                    NONBLOCKPLACEMENT_BLOCK.get())
                            .build(null));

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void createPlacementStorage(
                PlayerInteractEvent.RightClickBlock event) {
            aTFCSpecialInventoryBlock.rightClickLogic(event,
                    NONBLOCKPLACEMENT_BLOCK.get(), WorldPlaceable.class);
        }
    }

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.getBuilder(name); // empty model
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.simpleState(name, NONBLOCKPLACEMENT_BLOCK.get());
    }

    @Override
    public void registerTileEntityRenderers(TileEntityRenderers ter) {
        ter.register(NON_BLOCK_PLACER_TE.get(), NonBlockPlacementTER::new);
    }
}
