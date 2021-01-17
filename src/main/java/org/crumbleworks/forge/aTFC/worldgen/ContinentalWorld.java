package org.crumbleworks.forge.aTFC.worldgen;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.biome.provider.OverworldBiomeProvider;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalWorld {
    public static final String IDENTIFIER = "continental";
    
    public static ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> dimensionSettings, long seed, String settings) {
//        return DimensionGeneratorSettings.func_242750_a(biomes, dimensionSettings, seed);
        
        BiomeProvider bp = new OverworldBiomeProvider(seed, false, false, biomes);
        return new ContinentalChunkGenerator(bp, bp, dimensionSettings.getOrThrow(DimensionSettings.field_242734_c).getStructures(), seed);
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
