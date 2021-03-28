package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockTags;
import org.crumbleworks.forge.aTFC.dataGeneration.EntityTypeTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Recipes;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsSchwizerdeutsch;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsUSEnglish;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalBiomeProvider;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalChunkGenerator;
import org.crumbleworks.forge.aTFC.worldgen.ContinentalWorldType;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.world.ForgeWorldType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public final class ModRegisteringEvents {

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
    
    @SubscribeEvent
    public static void registerDatagenerators(GatherDataEvent event) {
        event.getGenerator().addProvider(new BlockModels(event.getGenerator(),
                event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModels(event.getGenerator(),
                event.getExistingFileHelper()));

        event.getGenerator().addProvider(new BlockStates(event.getGenerator(),
                event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new TranslationsUSEnglish(event.getGenerator()));
        event.getGenerator().addProvider(
                new TranslationsSchwizerdeutsch(event.getGenerator()));

        event.getGenerator().addProvider(new BlockTags(event.getGenerator(),
                Registry.BLOCK, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemTags(event.getGenerator(),
                Registry.ITEM, event.getExistingFileHelper()));
        event.getGenerator()
                .addProvider(new EntityTypeTags(event.getGenerator(),
                        Registry.ENTITY_TYPE, event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new LootTables(event.getGenerator()));

        event.getGenerator().addProvider(new Recipes(event.getGenerator()));
    }
}
