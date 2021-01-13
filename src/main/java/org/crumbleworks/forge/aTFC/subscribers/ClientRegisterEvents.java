package org.crumbleworks.forge.aTFC.subscribers;

import java.util.Set;
import java.util.stream.Collectors;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.blocks.BiomeAndRockBasedBlockColor;
import org.crumbleworks.forge.aTFC.blocks.Tintable;
import org.crumbleworks.forge.aTFC.wiring.Dirt;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MOD_ID,
        bus = Bus.MOD)
public final class ClientRegisterEvents {

    private static Block[] blocks;
    static {
        Set<Block> _b = ForgeRegistries.BLOCKS.getValues().stream()
                .filter((b) -> {
                    return b instanceof Tintable;
                }).collect(Collectors.toSet());
        blocks = _b.toArray(new Block[_b.size()]);
    }

    @SubscribeEvent
    public static void registerTranslucency(FMLClientSetupEvent event) {
        // FIXME we need to get array of blocks dynamically, this is wrong
        RenderTypeLookup.setRenderLayer(Dirt.DIRT_BLOCK.get(),
                RenderType.getCutout());
    }

    @SubscribeEvent
    public static void registerColorer(ColorHandlerEvent.Block event) {
        // FIXME we need to get array of blocks dynamically, this is wrong
        event.getBlockColors().register(new BiomeAndRockBasedBlockColor(),
                new Block[] { Dirt.DIRT_BLOCK.get() });
    }
}
