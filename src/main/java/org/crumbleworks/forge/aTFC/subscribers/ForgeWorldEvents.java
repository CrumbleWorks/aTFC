package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalCloudRenderHandler;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalWorld;

import net.minecraft.client.world.ClientWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class ForgeWorldEvents {
    
    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerRenderHandlers(WorldEvent.Load event) {
        if(!(event.getWorld() instanceof ClientWorld)) {
            return;
        }
        
        ((ClientWorld)event.getWorld())
        .field_239131_x_
        .setCloudRenderHandler(new ContinentalCloudRenderHandler(ContinentalWorld.CLOUD_LEVEL));
    }
}
