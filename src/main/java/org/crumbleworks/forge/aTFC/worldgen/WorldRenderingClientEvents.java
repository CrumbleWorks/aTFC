package org.crumbleworks.forge.aTFC.worldgen;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Main.MOD_ID,
        bus = Bus.FORGE)
public class WorldRenderingClientEvents {

    @SubscribeEvent
    public static void registerRenderHandlers(WorldEvent.Load event) {
        if(! (event.getWorld() instanceof ClientWorld)) {
            return;
        }

        ((ClientWorld)event.getWorld()).effects
                .setCloudRenderHandler(new ContinentalCloudRenderHandler(
                        ContinentalWorldType.CLOUD_LEVEL));
        ((ClientWorld)event.getWorld()).effects
                .setSkyRenderHandler(new ContinentalSkyRenderHandler());
    }
}
