package org.crumbleworks.forge.aTFC.worldgen;

import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import javax.annotation.Nullable;

import org.crumbleworks.forge.aTFC.content.BSP;
import org.crumbleworks.forge.aTFC.content.BSP.GrassCoverage;
import org.crumbleworks.forge.aTFC.wiring.Soil;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityClassification;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.util.SharedSeedRandom;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Blockreader;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeManager;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.biome.provider.BiomeProvider;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage.Carving;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.Heightmap.Type;
import net.minecraft.world.gen.NoiseChunkGenerator;
import net.minecraft.world.gen.SimplexNoiseGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.feature.structure.StructureManager;
import net.minecraft.world.gen.feature.template.TemplateManager;
import net.minecraft.world.gen.settings.DimensionStructuresSettings;
import net.minecraft.world.gen.settings.NoiseSettings;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.spawner.WorldEntitySpawner;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

/**
 * Basically copy/paste from {@link NoiseChunkGenerator} as the class is
 * written as a final class with way too many references to `this`.
 * <p>
 * This class has been reduced to the bare minimum and built-up to our
 * requirements again.
 *
 * @author Michael Stocker
 * @author Mojang
 * @since CURRENT_VERSION
 */
public class ContinentalChunkGenerator extends ChunkGenerator {

    public static final Codec<ContinentalChunkGenerator> CODEC = RecordCodecBuilder
            .create((p_236091_0_) -> {
                return p_236091_0_.group(BiomeProvider.CODEC
                        .fieldOf("biome_source").forGetter((p_236096_0_) -> {
                            return p_236096_0_.biomeProvider;
                        }), Codec.LONG.fieldOf("seed").stable()
                                .forGetter((p_236093_0_) -> {
                                    return p_236093_0_.seed;
                                }),
                        DimensionSettings.field_236098_b_.fieldOf("settings")
                                .forGetter((p_236090_0_) -> {
                                    return p_236090_0_.dimSettingsSupplier;
                                }))
                        .apply(p_236091_0_, p_236091_0_
                                .stable(ContinentalChunkGenerator::new));
            });

    private final int verticalNoiseGranularity;
    private final int noiseSizeY;

    protected final SharedSeedRandom randomSeed;

    @Nullable
    private final SimplexNoiseGenerator field_236083_v_;

    private final long seed;

    protected final Supplier<DimensionSettings> dimSettingsSupplier;

    public ContinentalChunkGenerator(BiomeProvider biomeProvider, long seed,
            Supplier<DimensionSettings> dimSettingsSupplier) {
        this(biomeProvider, biomeProvider, seed, dimSettingsSupplier);
    }

    private ContinentalChunkGenerator(BiomeProvider biomeProviderLeft,
            BiomeProvider biomeProviderRight, long seed,
            Supplier<DimensionSettings> dimSettingsSupplier) {
        super(biomeProviderLeft, biomeProviderRight,
                dimSettingsSupplier.get().getStructures(), seed);
        this.seed = seed;

        DimensionSettings dimensionsettings = dimSettingsSupplier.get();
        this.dimSettingsSupplier = dimSettingsSupplier;
        NoiseSettings noisesettings = dimensionsettings.getNoise();
        verticalNoiseGranularity = noisesettings.func_236175_f_() * 4;

        noiseSizeY = noisesettings.func_236169_a_()
                / verticalNoiseGranularity;

        randomSeed = new SharedSeedRandom(seed);

        randomSeed.skip(2620);
        if(noisesettings.func_236180_k_()) {
            SharedSeedRandom sharedseedrandom = new SharedSeedRandom(seed);
            sharedseedrandom.skip(17292);
            field_236083_v_ = new SimplexNoiseGenerator(sharedseedrandom);
        } else {
            field_236083_v_ = null;
        }

    }

    @Override
    protected Codec<? extends ChunkGenerator> func_230347_a_() {
        return CODEC;
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ChunkGenerator func_230349_a_(long p_230349_1_) {
        return new ContinentalChunkGenerator(
                biomeProvider.getBiomeProvider(p_230349_1_), p_230349_1_,
                dimSettingsSupplier);
    }

    /*
     * PSEUDOCONSTANTS
     */

    @Override
    public int getMaxBuildHeight() {
        return ContinentalWorld.BUILD_LEVEL;
    }

    @Override
    public int getSeaLevel() {
        return ContinentalWorld.SEA_LEVEL;
    }

    @Override
    public int getGroundHeight() {
        return ContinentalWorld.GROUND_LEVEL;
    }

    @Override
    public int getHeight(int x, int z, Heightmap.Type heightmapType) {
        // not sure what this does exactly, no averse effects found from
        // returning 0
        return 0;
    }

    @Override
    public int getNoiseHeight(int x, int z, Type heightmapType) {
        return super.getNoiseHeight(x, z, heightmapType);
    }

    @Override
    public int getNoiseHeightMinusOne(int x, int z, Type heightmapType) {
        return super.getNoiseHeightMinusOne(x, z, heightmapType);
    }

    @Override
    public BiomeProvider getBiomeProvider() {
        return super.getBiomeProvider();
    }

    @Override
    public DimensionStructuresSettings func_235957_b_() {
        return super.func_235957_b_();
    }

    /*
     * NECESSARY FUNCTIONS (from ChunkGenerator)
     */

    @Override
    public void func_242706_a(Registry<Biome> p_242706_1_,
            IChunk p_242706_2_) {
        // chunk priming
        // WORLDGEN BREAKS IF THIS METHOD DOES NOT DO WHATEVER IT DOES
        super.func_242706_a(p_242706_1_, p_242706_2_);
    }

    @Override
    public void generateSurface(WorldGenRegion worldGenRegion, IChunk chunk) {
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        makeBedrockLayer(chunk, sharedseedrandom);

        int xStart = chunk.getPos().getXStart();
        int zStart = chunk.getPos().getZStart();

        // FIXME write function (or find out if it already exists...) to
        // determine lowest, not-yet-set, block -> so we can layer the world
        // like cake(?)

        for(BlockPos pos : BlockPos.getAllInBoxMutable(xStart, 2, zStart,
                xStart + 15, 2, zStart + 15)) {
            chunk.setBlockState(pos, Soil.SOIL_BLOCK.get().getDefaultState()
                    .with(BSP.COVERAGE, GrassCoverage.TOP), false);
        }
    }

    // Spawns mobs based on the Mobspawninfo from the supplied biome
    @Override
    public void func_230354_a_(WorldGenRegion worldGenRegion) {
        int i = worldGenRegion.getMainChunkX();
        int j = worldGenRegion.getMainChunkZ();
        Biome biome = worldGenRegion
                .getBiome( (new ChunkPos(i, j)).asBlockPos());
        SharedSeedRandom sharedseedrandom = new SharedSeedRandom();
        sharedseedrandom.setDecorationSeed(worldGenRegion.getSeed(), i << 4,
                j << 4);
        WorldEntitySpawner.performWorldGenSpawning(worldGenRegion, biome, i,
                j, sharedseedrandom);
    }

    /*
     * OTHER FUNCTIONS (from ChunkGenerator)
     */

    @Override
    public IBlockReader func_230348_a_(int p_230348_1_, int p_230348_2_) {
        // This function seems to be used to generate structures
        BlockState[] ablockstate = new BlockState[noiseSizeY
                * verticalNoiseGranularity];
        return new Blockreader(ablockstate);
    }

    @Override
    public void func_230352_b_(IWorld world,
            StructureManager structureManager, IChunk chunk) {
        // Villages ??
    }

    @Override
    public List<MobSpawnInfo.Spawners> func_230353_a_(Biome biome,
            StructureManager structureManager,
            EntityClassification entityClass, BlockPos pos) {
        // Spawner placement
        return null;
    }

    @Override
    public void func_230350_a_(long p_230350_1_, BiomeManager p_230350_3_,
            IChunk p_230350_4_, Carving p_230350_5_) {
        // this seems to be responsible for carving underwater canyons
    }

    @Override
    public void func_230351_a_(WorldGenRegion p_230351_1_,
            StructureManager p_230351_2_) {
        // this seems to be responsible for underwater temples, bones,
        // shipwrecks, ...
    }

    @Override
    public boolean func_235952_a_(ChunkPos p_235952_1_) {
        // Stronghold generation
        return false;
    }

    @Override
    public void func_242707_a(DynamicRegistries p_242707_1_,
            StructureManager p_242707_2_, IChunk p_242707_3_,
            TemplateManager p_242707_4_, long p_242707_5_) {
        // this seems to create complex structurs (jigsaws?)
    }

    @Override
    public void func_235953_a_(ISeedReader p_235953_1_,
            StructureManager p_235953_2_, IChunk p_235953_3_) {
        // more structure stuff?!
    }

    @Override
    public BlockPos func_235956_a_(ServerWorld p_235956_1_,
            Structure<?> p_235956_2_, BlockPos p_235956_3_, int p_235956_4_,
            boolean p_235956_5_) {
        // more stronghold stuff
        return null;
    }

    /*
     * INTERNALS
     */

    /**
     * Creates a 2 blocks high layer of bedrock at the bottom of the world
     */
    private void makeBedrockLayer(IChunk chunkIn, Random rand) {
        int xStart = chunkIn.getPos().getXStart();
        int zStart = chunkIn.getPos().getZStart();
        for(BlockPos pos : BlockPos.getAllInBoxMutable(xStart, 0, zStart,
                xStart + 15, 1, zStart + 15)) {

            // FIXME make layer at y=1 random, so it looks a bit less
            // artificial
            chunkIn.setBlockState(pos, Blocks.BEDROCK.getDefaultState(),
                    false);
        }
    }
}
