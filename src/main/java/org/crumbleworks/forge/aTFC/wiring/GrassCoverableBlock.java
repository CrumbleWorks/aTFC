package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.content.blocks.BSP;
import org.crumbleworks.forge.aTFC.content.blocks.BSP.GrassCoverage;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.DynamicPainter;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;

import net.minecraft.block.Block;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.fml.RegistryObject;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class GrassCoverableBlock implements Wireable {

    private final String name;
    private final RegistryObject<Block> block;

    public GrassCoverableBlock(String name, RegistryObject<Block> block) {
        this.name = name;
        this.block = block;
    }

    @Override
    public void generateBlockModels(BlockModels bm) {
        createGrassCoverableBlock(name, bm.modLoc("block/" + name), bm);
    }

    private void createGrassCoverableBlock(String name,
            ResourceLocation mainTex, BlockModels bm) {

        // Base
        bm.getBuilder(name)
                .parent(bm.getExistingFile(bm.mcLoc("block/block")))
                .texture("particle", mainTex)
                .texture("all", mainTex)
                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((d, fb) -> {
                    fb.uvs(0, 0, 16, 16)
                            .texture("#all")
                            .cullface(d)
                            .tintindex(DynamicPainter.TINT_SOIL)
                            .end();
                }).end();

        // Only on top
        BlockModelBuilder.ElementBuilder topEb = innerGrassBlock(
                name + "_grass_top",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        topEb = faceGrassFull(topEb, Direction.UP);
        topEb = faceGrassRim(topEb, Direction.NORTH);
        topEb = faceGrassRim(topEb, Direction.EAST);
        topEb = faceGrassRim(topEb, Direction.SOUTH);
        topEb = faceGrassRim(topEb, Direction.WEST);
        topEb.end();

        // Top and Side
        BlockModelBuilder.ElementBuilder sideEb = innerGrassBlock(
                name + "_grass_side",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        sideEb = faceGrassFull(sideEb, Direction.UP);
        sideEb = faceGrassFull(sideEb, Direction.NORTH);
        sideEb = faceGrassRim(sideEb, Direction.EAST);
        sideEb = faceGrassRim(sideEb, Direction.SOUTH);
        sideEb = faceGrassRim(sideEb, Direction.WEST);
        sideEb.end();

        // Top and Corner
        BlockModelBuilder.ElementBuilder cornerEb = innerGrassBlock(
                name + "_grass_corner",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        cornerEb = faceGrassFull(cornerEb, Direction.UP);
        cornerEb = faceGrassFull(cornerEb, Direction.NORTH);
        cornerEb = faceGrassFull(cornerEb, Direction.EAST);
        cornerEb = faceGrassRim(cornerEb, Direction.SOUTH);
        cornerEb = faceGrassRim(cornerEb, Direction.WEST);
        cornerEb.end();

        // Top and Opposite Sides
        BlockModelBuilder.ElementBuilder oppositesEb = innerGrassBlock(
                name + "_grass_opposites",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        oppositesEb = faceGrassFull(oppositesEb, Direction.UP);
        oppositesEb = faceGrassFull(oppositesEb, Direction.NORTH);
        oppositesEb = faceGrassRim(oppositesEb, Direction.EAST);
        oppositesEb = faceGrassFull(oppositesEb, Direction.SOUTH);
        oppositesEb = faceGrassRim(oppositesEb, Direction.WEST);
        oppositesEb.end();

        // Top and 3 Sides
        BlockModelBuilder.ElementBuilder ushapeEb = innerGrassBlock(
                name + "_grass_ushape",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        ushapeEb = faceGrassFull(ushapeEb, Direction.UP);
        ushapeEb = faceGrassFull(ushapeEb, Direction.NORTH);
        ushapeEb = faceGrassFull(ushapeEb, Direction.EAST);
        ushapeEb = faceGrassFull(ushapeEb, Direction.SOUTH);
        ushapeEb = faceGrassRim(ushapeEb, Direction.WEST);
        ushapeEb.end();

        // Top and All Sides
        BlockModelBuilder.ElementBuilder haloEb = innerGrassBlock(
                name + "_grass_halo",
                mainTex, bm).element().from(0, 0, 0).to(16, 16, 16);
        haloEb = faceGrassFull(haloEb, Direction.UP);
        haloEb = faceGrassFull(haloEb, Direction.NORTH);
        haloEb = faceGrassFull(haloEb, Direction.EAST);
        haloEb = faceGrassFull(haloEb, Direction.SOUTH);
        haloEb = faceGrassFull(haloEb, Direction.WEST);
        haloEb.end();
    }

    private BlockModelBuilder innerGrassBlock(String name,
            ResourceLocation mainTex, BlockModels bm) {
        return bm.getBuilder(name)
                .parent(bm.getExistingFile(bm.mcLoc("block/block")))
                .texture("particle", mainTex)
                .texture("all", mainTex)
                .texture("overlay_top", bm.modLoc("tfctng/blocks/grass_top"))
                .texture("overlay_side",
                        bm.modLoc("tfctng/blocks/grass_side"))
                .element().from(0, 0, 0).to(16, 16, 16)
                .allFaces((d, fb) -> {
                    fb.uvs(0, 0, 16, 16)
                            .texture("#all")
                            .cullface(d)
                            .tintindex(DynamicPainter.TINT_SOIL)
                            .end();
                }).end();
    }

    private BlockModelBuilder.ElementBuilder faceGrassFull(
            BlockModelBuilder.ElementBuilder e, Direction d) {
        return e.face(d)
                .uvs(0, 0, 16, 16)
                .texture("#overlay_top")
                .cullface(d)
                .tintindex(DynamicPainter.TINT_GREENERY)
                .end();
    }

    private BlockModelBuilder.ElementBuilder faceGrassRim(
            BlockModelBuilder.ElementBuilder e, Direction d) {
        return e.face(d)
                .uvs(0, 0, 16, 16)
                .texture("#overlay_side")
                .cullface(d)
                .tintindex(DynamicPainter.TINT_GREENERY)
                .end();
    }

    @Override
    public void generateItemModels(ItemModels im) {
        im.createBlockItem(name);
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.getVariantBuilder(block.get())
        
                //no coverage
                .partialState().with(BSP.COVERAGE, GrassCoverage.NONE)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name)))
                .addModel()
                
                //top
                .partialState().with(BSP.COVERAGE, GrassCoverage.TOP)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_top")))
                .addModel()
                
                //side
                .partialState().with(BSP.COVERAGE, GrassCoverage.SIDE).with(BSP.FACING, Direction.NORTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_side")))
                .rotationX(0).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.SIDE).with(BSP.FACING, Direction.EAST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_top")))
                .rotationX(90).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.SIDE).with(BSP.FACING, Direction.SOUTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_top")))
                .rotationX(180).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.SIDE).with(BSP.FACING, Direction.WEST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_top")))
                .rotationX(270).addModel()
                
                //opposites
                .partialState().with(BSP.COVERAGE, GrassCoverage.OPPOSITES).with(BSP.FACING, Direction.NORTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_opposites")))
                .rotationX(0).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.OPPOSITES).with(BSP.FACING, Direction.EAST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_opposites")))
                .rotationX(90).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.OPPOSITES).with(BSP.FACING, Direction.SOUTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_opposites")))
                .rotationX(0).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.OPPOSITES).with(BSP.FACING, Direction.WEST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_opposites")))
                .rotationX(90).addModel()
                
                //corner
                .partialState().with(BSP.COVERAGE, GrassCoverage.CORNER).with(BSP.FACING, Direction.NORTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_corner")))
                .rotationX(0).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.CORNER).with(BSP.FACING, Direction.EAST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_corner")))
                .rotationX(90).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.CORNER).with(BSP.FACING, Direction.SOUTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_corner")))
                .rotationX(180).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.CORNER).with(BSP.FACING, Direction.WEST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_corner")))
                .rotationX(270).addModel()
                
                //ushape
                .partialState().with(BSP.COVERAGE, GrassCoverage.USHAPE).with(BSP.FACING, Direction.NORTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_ushape")))
                .rotationX(0).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.USHAPE).with(BSP.FACING, Direction.EAST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_ushape")))
                .rotationX(90).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.USHAPE).with(BSP.FACING, Direction.SOUTH)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_ushape")))
                .rotationX(180).addModel()
                .partialState().with(BSP.COVERAGE, GrassCoverage.USHAPE).with(BSP.FACING, Direction.WEST)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_ushape")))
                .rotationX(270).addModel()
                
                //halo
                .partialState().with(BSP.COVERAGE, GrassCoverage.HALO)
                .modelForState().modelFile(bs.models().getExistingFile(bs.blockModel(name + "_grass_halo")))
                .addModel();
    }
}
