package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalWorld;

import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
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
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class WorldEvents {

    @SubscribeEvent
    public static void registerWorldTypes(
            RegistryEvent.Register<ForgeWorldType> event) {
        // don't try subclassing ForgeWorldType, results in a crash
        event.getRegistry().register(
                new ForgeWorldType(ContinentalWorld::createChunkGenerator)
                        .setRegistryName(ContinentalWorld.IDENTIFIER));
    }

    @SubscribeEvent
    public static void registerWorldTypeScreenFactories(
            FMLClientSetupEvent event) {
        ContinentalWorld.continentalConfigGui();
    }
}
