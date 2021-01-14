package org.crumbleworks.forge.aTFC.subscribers;

import java.util.Set;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.Util;
import org.crumbleworks.forge.aTFC.blocks.Multilayered;
import org.crumbleworks.forge.aTFC.blocks.Tintable;
import org.crumbleworks.forge.aTFC.dataGeneration.DynamicPainter;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.Item;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MOD_ID,
        bus = Bus.MOD)
public final class ClientEvents {

    @SubscribeEvent
    public static void registerTranslucency(FMLClientSetupEvent event) {
        Set<Block> blocks = Util.getBlocks(Multilayered.class);
        for(Block block : blocks) {
            RenderTypeLookup.setRenderLayer(block, RenderType.getCutout());
        }
    }

    @SubscribeEvent
    public static void registerColorer(ColorHandlerEvent.Block event) {
        Set<Block> blocks = Util.getBlocks(Tintable.class);
        event.getBlockColors().register(new DynamicPainter(),
                blocks.toArray(new Block[blocks.size()]));
    }
    
    @SubscribeEvent
    public static void registerColorer(ColorHandlerEvent.Item event) {
        Set<Item> items = Util.getItems(Tintable.class);
        event.getItemColors().register(new DynamicPainter(),
                items.toArray(new Item[items.size()]));
    }
}
