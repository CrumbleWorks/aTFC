package org.crumbleworks.forge.aTFC.content;

import net.minecraft.state.DirectionProperty;
import net.minecraft.state.EnumProperty;
import net.minecraft.state.Property;
import net.minecraft.util.Direction;
import net.minecraft.util.IStringSerializable;

/**
 * Holds all Properties used in the mode, as well as nice groups for different
 * behaviourtypes (e.g. grass coverage).
 * <p>
 * This class is called <code>BSP</code> so our code can be a bit more
 * concise, it stands for: <code>(aTFC)BlockStateProperties</code>.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BSP {

    public static enum GrassCoverage implements IStringSerializable {

        NONE, TOP, SIDE, OPPOSITES, CORNER, USHAPE, HALO;

        @Override
        public String getString() {
            // need to match [a-z0-9_]+ (source: forge forums...)
            return name().toLowerCase();
        }
    }

    public static final DirectionProperty FACING = DirectionProperty
            .create("facing", Direction.Plane.HORIZONTAL);
    public static final EnumProperty<GrassCoverage> COVERAGE = EnumProperty
            .create("coverage", GrassCoverage.class);

    public static final Property<?>[] PROPSET_GRASS_COVERABLE = {
            BSP.FACING,
            BSP.COVERAGE };
}
