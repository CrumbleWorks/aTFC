package org.crumbleworks.forge.aTFC.content.blocks;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.LightType;
import net.minecraft.world.server.ServerWorld;

/**
 * Marker and methods for blocks that can have grass growing on them (changing
 * their {@link BlockState}). Don't forget to call these methods from your
 * block (e.g. from
 * {@link Block#randomTick(BlockState, net.minecraft.world.server.ServerWorld, net.minecraft.util.math.BlockPos, java.util.Random)}.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface GrassCoverable extends BSP {

    public static final Property<?>[] PROPSET_GRASS_COVERABLE = { FACING,
            COVERAGE };

    static final int MINIMUM_LIGHTLEVEL_FOR_GRASSGROWING = 9;
    static final int MINIMUM_LIGHTLEVEL_FOR_GRASSKEEPING = 4;

    default void tryGrowingGrass(BlockState state, ServerWorld world,
            BlockPos pos, Random rand) {
        BlockState defaultState = state.getBlock().defaultBlockState();
        int sunlight = world.getBrightness(LightType.SKY, pos.above());

        // Don't spread grass from non-grown blocks
        if(state == defaultState) {
            return;
        }

        // Check if we have enough 'power' for spreading
        if(MINIMUM_LIGHTLEVEL_FOR_GRASSGROWING > sunlight) {
            if(MINIMUM_LIGHTLEVEL_FOR_GRASSKEEPING > sunlight) {
                world.setBlockAndUpdate(pos, defaultState);
            }

            return;
        }

        BlockPos upPosA = pos.above().north().west();
        BlockPos upPosB = pos.above().south().east();
        Stream<BlockPos> upStream = StreamSupport.stream(
                BlockPos.betweenClosed(upPosA, upPosB).spliterator(),
                false);
        BlockPos midPosA = pos.north(3).west(3);
        BlockPos midPosB = pos.south(3).east(3);
        Stream<BlockPos> midStream = StreamSupport.stream(
                BlockPos.betweenClosed(midPosA, midPosB).spliterator(),
                false);
        BlockPos downPosA = pos.below().north().west();
        BlockPos downPosB = pos.below(3).south().east();
        Stream<BlockPos> downStream = StreamSupport.stream(
                BlockPos.betweenClosed(downPosA, downPosB).spliterator(),
                false);

        List<BlockPos> targets = Stream
                .concat(upStream, Stream.concat(midStream, downStream))
                .filter(b -> GrassCoverable.class
                        .isInstance(world.getBlockState(b).getBlock()))
                // check block above our targetblock; because that's what
                // minecraft expects from you
                .filter(b -> world.canSeeSky(b.above()))
                // only spread to default blocks, non-defaults get their own chance to grow over
                .filter(b -> {
                    BlockState _state = world.getBlockState(b);
                    return _state == _state.getBlock().defaultBlockState();
                })
                .collect(Collectors.toList());
        if(targets.isEmpty()) {
            return;
        }

        // try spread
        Collections.shuffle(targets);
        spreadGrass(world, targets.get(0));

        // try grow over
        spreadGrass(world, pos);
    }

    static void spreadGrass(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if(! (state.getBlock() instanceof GrassCoverable)) {
            return; // fail
        }

        // At first grass needs to get a hold, so it only grows on top
        BlockState defaultState = state.getBlock().defaultBlockState();
        if(state == defaultState) {
            world.setBlockAndUpdate(pos, state.setValue(COVERAGE, GrassCoverage.TOP));
            return;
        }

        List<Direction> surroundingBlocks = new ArrayList<>();
        if(!allowsCoverage(world, pos.north())) {
            surroundingBlocks.add(Direction.NORTH);
        }
        if(!allowsCoverage(world, pos.east())) {
            surroundingBlocks.add(Direction.EAST);
        }
        if(!allowsCoverage(world, pos.south())) {
            surroundingBlocks.add(Direction.SOUTH);
        }
        if(!allowsCoverage(world, pos.west())) {
            surroundingBlocks.add(Direction.WEST);
        }

        switch(surroundingBlocks.size()) {
            case 0:
                world.setBlockAndUpdate(pos,
                        state.setValue(COVERAGE, GrassCoverage.HALO));
                return;
            case 1:
                world.setBlockAndUpdate(pos,
                        state.setValue(COVERAGE, GrassCoverage.USHAPE).setValue(
                                FACING,
                                surroundingBlocks.get(0).getOpposite()));
                return;
            case 2:
                List<Direction> leftoverBlocks = Lists
                        .newArrayList(Direction.NORTH, Direction.SOUTH);
                leftoverBlocks.retainAll(surroundingBlocks);

                switch(leftoverBlocks.size()) {
                    case 0:
                        world.setBlockAndUpdate(pos,
                                state.setValue(COVERAGE, GrassCoverage.OPPOSITES)
                                        .setValue(FACING, Direction.WEST));
                        return;
                    case 1:
                        if(surroundingBlocks.contains(Direction.NORTH)
                                && surroundingBlocks
                                        .contains(Direction.EAST)) {
                            world.setBlockAndUpdate(pos,
                                    state.setValue(COVERAGE, GrassCoverage.CORNER)
                                            .setValue(FACING, Direction.EAST));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.EAST)
                                && surroundingBlocks
                                        .contains(Direction.SOUTH)) {
                            world.setBlockAndUpdate(pos,
                                    state.setValue(COVERAGE, GrassCoverage.CORNER)
                                            .setValue(FACING, Direction.SOUTH));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.SOUTH)
                                && surroundingBlocks
                                        .contains(Direction.WEST)) {
                            world.setBlockAndUpdate(pos,
                                    state.setValue(COVERAGE, GrassCoverage.CORNER)
                                            .setValue(FACING, Direction.WEST));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.WEST)
                                && surroundingBlocks
                                        .contains(Direction.NORTH)) {
                            world.setBlockAndUpdate(pos,
                                    state.setValue(COVERAGE, GrassCoverage.CORNER)
                                            .setValue(FACING, Direction.NORTH));
                            return;
                        }
                        return;
                    case 2:
                        world.setBlockAndUpdate(pos,
                                state.setValue(COVERAGE, GrassCoverage.OPPOSITES)
                                        .setValue(FACING, Direction.NORTH));
                        return;
                }
                return;
            case 3:
                List<Direction> horizontalAll = Lists
                        .newArrayList(Direction.Plane.HORIZONTAL);
                horizontalAll.removeAll(surroundingBlocks);
                world.setBlockAndUpdate(pos,
                        state.setValue(COVERAGE, GrassCoverage.SIDE).setValue(FACING,
                                horizontalAll.get(0)));
                return;
            case 4:
                world.setBlockAndUpdate(pos,
                        state.setValue(COVERAGE, GrassCoverage.TOP));
                return;
        }
    }

    static boolean allowsCoverage(ServerWorld world, BlockPos pos) {
        return world.isEmptyBlock(pos) && world.getBlockState(pos.below())
                .getBlock() instanceof GrassCoverable;
    }
}
