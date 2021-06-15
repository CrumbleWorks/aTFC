package org.crumbleworks.forge.aTFC.worldgen;

import com.mojang.serialization.Lifecycle;

import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;
import net.minecraft.world.Dimension;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.settings.DimensionGeneratorSettings;
import net.minecraftforge.common.world.ForgeWorldType;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalWorldType extends ForgeWorldType {
    
    public static final String IDENTIFIER = "continental";
    
    //TODO raise when transitioning to 1.17
    public static final int BUILD_LEVEL = 256;
    
    public static final int CLOUD_LEVEL = 224;
    public static final int SEA_LEVEL = 144;
    public static final int GROUND_LEVEL = 152; //used for spawn, etc. 
    
    //TODO rocklayers
    //lowest Y for each rocklayer to begin at
    public static final int ROCK_LAYER_ONE = 120;
    public static final int ROCK_LAYER_TWO = 80;
    public static final int ROCK_LAYER_THREE = 40;
    
    public ContinentalWorldType() {
        super(null);
    }
    
    @Override
    public ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> dimensionSettings, long seed, String settings) {
        BiomeProvider bp = new ContinentalBiomeProvider(seed, biomes);
        return new ContinentalChunkGenerator(bp, seed, () -> dimensionSettings.getOrThrow(DimensionSettings.OVERWORLD));
    }
    
    public static SimpleRegistry<Dimension> dimensions(Registry<Biome> biomeRegistry, Registry<DimensionSettings> dimensionSettingsRegistry, long seed) {
        SimpleRegistry<Dimension> registry = new SimpleRegistry<>(Registry.DIMENSION_KEY, Lifecycle.stable());
        return registry;
    }
    
    @Override
    public DimensionGeneratorSettings createSettings(DynamicRegistries dynamicRegistries, long seed, boolean generateStructures, boolean generateLoot, String generatorSettings) {
        Registry<Biome> biomeRegistry = dynamicRegistries.getRegistry(Registry.BIOME_KEY);
        Registry<DimensionSettings> dimensionSettingsRegistry = dynamicRegistries.getRegistry(Registry.NOISE_SETTINGS_KEY);
        Registry<DimensionType> dimensionTypeRegistry = dynamicRegistries.getRegistry(Registry.DIMENSION_TYPE_KEY);
        
        return new DimensionGeneratorSettings(seed, false, false, 
                DimensionGeneratorSettings.func_242749_a(
                        dimensionTypeRegistry,
                        dimensions(biomeRegistry, dimensionSettingsRegistry, seed),
                        createChunkGenerator(biomeRegistry, dimensionSettingsRegistry, seed, null)
                        )
                );
    }
    
    public static void continentalConfigGui() {
        // TODO #40
        // We can register custom GUIs here, the below is a bare minimum sample for a config UI
//        ForgeWorldTypeScreens.registerFactory(WORLD_TYPE, (returnTo, dimensionGeneratorSettings) -> new Screen(worldType.getDisplayName()) {
//            @Override
//            protected void init() {
//                super.init();
//                
//                addButton(new Button(0, 0, 120, 20, new StringTextComponent("CLOOOOOOOOOZE"), btn -> {
//                    Minecraft.getInstance().displayGuiScreen(returnTo);
//                }));
//            }
//        });
    }
}
