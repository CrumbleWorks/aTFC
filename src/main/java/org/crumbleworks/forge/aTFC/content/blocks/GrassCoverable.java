package org.crumbleworks.forge.aTFC.content.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.Property;

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
}
