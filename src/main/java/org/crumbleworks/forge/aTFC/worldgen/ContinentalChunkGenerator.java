package org.crumbleworks.forge.aTFC.worldgen;

import com.mojang.serialization.Codec;

import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalChunkGenerator extends ChunkGenerator {

    public ContinentalChunkGenerator(BiomeProvider p_i231887_1_, BiomeProvider p_i231887_2_, DimensionStructuresSettings dimensionStructuresSettings, long seed) {
        super(p_i231887_1_, p_i231887_2_, dimensionStructuresSettings, seed);
    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ChunkGenerator func_230349_a_(long p_230349_1_) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void generateSurface(WorldGenRegion p_225551_1_, IChunk p_225551_2_) {
        // TODO Auto-generated method stub
    }

    @Override
    public void func_230352_b_(IWorld p_230352_1_, StructureManager p_230352_2_, IChunk p_230352_3_) {
        // TODO Auto-generated method stub
    }

    @Override
    public int getHeight(int x, int z, Type heightmapType) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        // TODO Auto-generated method stub
        return null;
    }
}
