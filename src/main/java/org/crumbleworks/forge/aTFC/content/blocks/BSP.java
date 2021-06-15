package org.crumbleworks.forge.aTFC.content.blocks;

import net.minecraft.block.BlockState;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

/**
 * Marker interface for blocks that change their {@link BlockState} based on
 * custom {@link Property Properties}.
 * <p>
 * Also serves as a collector for all our custom mod properties.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface BSP {

    public static enum GrassCoverage implements IStringSerializable {

        NONE, TOP, SIDE, OPPOSITES, CORNER, USHAPE, HALO;

        @Override
        public String getSerializedName() {
            // need to match [a-z0-9_]+ (source: forge forums...)
            return name().toLowerCase();
        }
    }

    public static final DirectionProperty FACING = DirectionProperty
            .create("facing", Direction.Plane.HORIZONTAL);
    public static final EnumProperty<GrassCoverage> COVERAGE = EnumProperty
            .create("coverage", GrassCoverage.class);
}
