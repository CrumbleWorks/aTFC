package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying.BrickPlacerTE;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.NonBlockPlacementTE;

import net.minecraft.tileentity.TileEntityType;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TileEntities {

    private static final List<TileEntityType<?>> tileEntityTypes = new ArrayList<>();

    public static final TileEntityType<NonBlockPlacementTE> NON_BLOCK_PLACER_TE = TileEntityType.Builder
            .create(NonBlockPlacementTE::new,
                    TileEntitiesMappings.getMappings(NonBlockPlacementTE.class))
            .build(null);
    public static final TileEntityType<BrickPlacerTE> BRICK_PLACER_TE = TileEntityType.Builder
            .create(BrickPlacerTE::new,
                    TileEntitiesMappings.getMappings(BrickPlacerTE.class))
            .build(null);

    static {
        register("non_block_placer_tile", NON_BLOCK_PLACER_TE);
        register("brick_placer_tile", BRICK_PLACER_TE);
    }

    public static List<TileEntityType<?>> getTETs() {
        return Collections.unmodifiableList(tileEntityTypes);
    }

    private static void register(String name,
            TileEntityType<?> tileEntityType) {
        tileEntityType.setRegistryName(Main.MOD_ID + ":" + name);
        tileEntityTypes.add(tileEntityType);
    }
}
