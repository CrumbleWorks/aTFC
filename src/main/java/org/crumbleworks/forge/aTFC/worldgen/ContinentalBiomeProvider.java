package org.crumbleworks.forge.aTFC.worldgen;

import java.util.ArrayList;

import com.mojang.serialization.Codec;

import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.gen.layer.Layer;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalBiomeProvider extends BiomeProvider {
    
    private final Layer noiseBiomelayer;
    private final Registry<Biome> biomes;
    
    protected ContinentalBiomeProvider(long seed, Registry<Biome> biomes) {
        super(new ArrayList<>()); //FIXME this should hold biomes apparently? So unnecessarily complex
        noiseBiomelayer = ContinentalLayerUtil.createGenLayers(seed);
        this.biomes = biomes;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        //FIXME why do we drop y?!
        return noiseBiomelayer.func_242936_a(biomes, x, z);
    }

    @Override
    protected Codec<? extends BiomeProvider> getBiomeProviderCodec() {
        return BiomeProvider.CODEC;
    }

    @Override
    public BiomeProvider getBiomeProvider(long seed) {
        return new ContinentalBiomeProvider(seed, biomes);
    }
}
