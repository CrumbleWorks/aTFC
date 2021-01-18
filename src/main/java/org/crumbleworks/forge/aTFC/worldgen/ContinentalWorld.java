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
    
    public static ChunkGenerator createChunkGenerator(Registry<Biome> biomes, Registry<DimensionSettings> dimensionSettings, long seed, String settings) {
        BiomeProvider bp = new OverworldBiomeProvider(seed, false, false, biomes);
        return new ContinentalChunkGenerator(bp, seed, () -> {return dimensionSettings.getOrThrow(DimensionSettings.field_242734_c);});
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
