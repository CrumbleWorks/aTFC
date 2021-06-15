package org.crumbleworks.forge.aTFC.worldgen;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class WorldSetupEventsMod {

    private static ContinentalWorldType continentalWorldType = new ContinentalWorldType();

    @SubscribeEvent
    public static void registerCodecs(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.BIOME_PROVIDER_CODEC, Main.MOD_ID + ":" + ContinentalWorldType.IDENTIFIER + "_biomeprovider", ContinentalBiomeProvider.CODEC);
            Registry.register(Registry.CHUNK_GENERATOR_CODEC, Main.MOD_ID + ":" + ContinentalWorldType.IDENTIFIER + "_chunkgenerator", ContinentalChunkGenerator.CODEC);
        });
    }
    
    @SubscribeEvent
    public static void registerWorldTypes(RegistryEvent.Register<ForgeWorldType> event) {
        continentalWorldType.setRegistryName(new ResourceLocation(Main.MOD_ID, ContinentalWorldType.IDENTIFIER));
        event.getRegistry().register(continentalWorldType);
    }

    @SubscribeEvent
    public static void registerWorldTypeScreenFactories(FMLClientSetupEvent event) {
        // FIXME move this into its own class?
        ContinentalWorldType.continentalConfigGui();
    }
}
