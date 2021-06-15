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

import org.crumbleworks.forge.aTFC.content.blocks.BSP.GrassCoverage;

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
        BlockState defaultState = state.getBlock().getDefaultState();
        int sunlight = world.getLightFor(LightType.SKY, pos.up());

        // Don't spread grass from non-grown blocks
        if(state == defaultState) {
            return;
        }

        // Check if we have enough 'power' for spreading
        if(MINIMUM_LIGHTLEVEL_FOR_GRASSGROWING > sunlight) {
            if(MINIMUM_LIGHTLEVEL_FOR_GRASSKEEPING > sunlight) {
                world.setBlockState(pos, state.getBlock().getDefaultState());
            }

            return;
        }

        BlockPos upPosA = pos.up().north().west();
        BlockPos upPosB = pos.up().south().east();
        Stream<BlockPos> upStream = StreamSupport.stream(
                BlockPos.getAllInBoxMutable(upPosA, upPosB).spliterator(),
                false);
        BlockPos midPosA = pos.north(3).west(3);
        BlockPos midPosB = pos.south(3).east(3);
        Stream<BlockPos> midStream = StreamSupport.stream(
                BlockPos.getAllInBoxMutable(midPosA, midPosB).spliterator(),
                false);
        BlockPos downPosA = pos.down().north().west();
        BlockPos downPosB = pos.down(3).south().east();
        Stream<BlockPos> downStream = StreamSupport.stream(
                BlockPos.getAllInBoxMutable(downPosA, downPosB).spliterator(),
                false);

        List<BlockPos> targets = Stream
                .concat(upStream, Stream.concat(midStream, downStream))
                .filter(b -> GrassCoverable.class
                        .isInstance(world.getBlockState(b).getBlock()))
                // check block above our targetblock; because that's what
                // minecraft expects from you
                .filter(b -> world.canSeeSky(b.up()))
                .collect(Collectors.toList());
        if(targets.isEmpty()) {
            return;
        }

        Collections.shuffle(targets);
        spreadGrass(world, targets.get(0));

        spreadGrass(world, pos);
    }

    static void spreadGrass(ServerWorld world, BlockPos pos) {
        BlockState state = world.getBlockState(pos);

        if(! (state.getBlock() instanceof GrassCoverable)) {
            return; // fail
        }

        // At first grass needs to get a hold, so it only grows on top
        BlockState defaultState = state.getBlock().getDefaultState();
        if(state == defaultState) {
            world.setBlockState(pos, state.with(COVERAGE, GrassCoverage.TOP));
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
                world.setBlockState(pos,
                        state.with(COVERAGE, GrassCoverage.HALO));
                return;
            case 1:
                world.setBlockState(pos,
                        state.with(COVERAGE, GrassCoverage.USHAPE).with(
                                FACING,
                                surroundingBlocks.get(0).getOpposite()));
                return;
            case 2:
                List<Direction> leftoverBlocks = Lists
                        .newArrayList(Direction.NORTH, Direction.SOUTH);
                leftoverBlocks.retainAll(surroundingBlocks);

                switch(leftoverBlocks.size()) {
                    case 0:
                        world.setBlockState(pos,
                                state.with(COVERAGE, GrassCoverage.OPPOSITES)
                                        .with(FACING, Direction.WEST));
                        return;
                    case 1:
                        if(surroundingBlocks.contains(Direction.NORTH)
                                && surroundingBlocks
                                        .contains(Direction.EAST)) {
                            world.setBlockState(pos,
                                    state.with(COVERAGE, GrassCoverage.CORNER)
                                            .with(FACING, Direction.EAST));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.EAST)
                                && surroundingBlocks
                                        .contains(Direction.SOUTH)) {
                            world.setBlockState(pos,
                                    state.with(COVERAGE, GrassCoverage.CORNER)
                                            .with(FACING, Direction.SOUTH));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.SOUTH)
                                && surroundingBlocks
                                        .contains(Direction.WEST)) {
                            world.setBlockState(pos,
                                    state.with(COVERAGE, GrassCoverage.CORNER)
                                            .with(FACING, Direction.WEST));
                            return;
                        }
                        if(surroundingBlocks.contains(Direction.WEST)
                                && surroundingBlocks
                                        .contains(Direction.NORTH)) {
                            world.setBlockState(pos,
                                    state.with(COVERAGE, GrassCoverage.CORNER)
                                            .with(FACING, Direction.NORTH));
                            return;
                        }
                        return;
                    case 2:
                        world.setBlockState(pos,
                                state.with(COVERAGE, GrassCoverage.OPPOSITES)
                                        .with(FACING, Direction.NORTH));
                        return;
                }
                return;
            case 3:
                List<Direction> horizontalAll = Lists
                        .newArrayList(Direction.Plane.HORIZONTAL);
                horizontalAll.removeAll(surroundingBlocks);
                world.setBlockState(pos,
                        state.with(COVERAGE, GrassCoverage.SIDE).with(FACING,
                                horizontalAll.get(0)));
                return;
            case 4:
                world.setBlockState(pos,
                        state.with(COVERAGE, GrassCoverage.TOP));
                return;
        }
    }

    static boolean allowsCoverage(ServerWorld world, BlockPos pos) {
        return world.isAirBlock(pos) && world.getBlockState(pos.down())
                .getBlock() instanceof GrassCoverable;
    }
}
