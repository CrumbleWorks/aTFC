package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalWorldType;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
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
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public class ModWorldEvents {
    
    public static ContinentalWorldType continentalWorldType = new ContinentalWorldType();

    @SubscribeEvent
    public static void registerWorldTypes(RegistryEvent.Register<ForgeWorldType> event) {
        continentalWorldType.setRegistryName(new ResourceLocation(Main.MOD_ID, ContinentalWorldType.IDENTIFIER));
        ForgeRegistries.WORLD_TYPES.register(continentalWorldType);
    }
    
    @SubscribeEvent
    public static void registerWorldTypeScreenFactories(FMLClientSetupEvent event) {
        //FIXME move this into its own class?
        ContinentalWorldType.continentalConfigGui();
    }
}
