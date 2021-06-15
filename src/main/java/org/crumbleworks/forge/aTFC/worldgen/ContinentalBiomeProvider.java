package org.crumbleworks.forge.aTFC.worldgen;

import java.util.ArrayList;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryLookupCodec;
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
    
    public static final Codec<ContinentalBiomeProvider> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
                Codec.LONG.fieldOf("seed").stable().forGetter(cbp -> cbp.seed),
                RegistryLookupCodec.create(Registry.BIOME_REGISTRY).forGetter(cbp -> cbp.biomes)
            ).apply(instance, instance.stable(ContinentalBiomeProvider::new))
    );
    
    private final long seed;
    private final Layer noiseBiomelayer;
    private final Registry<Biome> biomes;
    
    protected ContinentalBiomeProvider(long seed, Registry<Biome> biomes) {
        super(new ArrayList<>()); //FIXME this should hold biomes apparently? So unnecessarily complex
        this.seed = seed;
        noiseBiomelayer = ContinentalLayerUtil.createGenLayers(seed);
        this.biomes = biomes;
    }

    @Override
    public Biome getNoiseBiome(int x, int y, int z) {
        //FIXME why do we drop y?! Might be because we assume biomes to be all over the place...
        return noiseBiomelayer.get(biomes, x, z);
    }

    @Override
    protected Codec<? extends BiomeProvider> codec() {
        return CODEC;
    }

    @Override
    public BiomeProvider withSeed(long seed) {
        return new ContinentalBiomeProvider(seed, biomes);
    }
}
