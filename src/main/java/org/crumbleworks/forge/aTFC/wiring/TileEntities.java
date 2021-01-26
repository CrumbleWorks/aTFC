package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing.WorldItemPlacerTE;

import net.minecraft.tileentity.TileEntityType;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TileEntities {

    private static final List<TileEntityType<?>> tileEntityTypes = new ArrayList<>();

    public static final TileEntityType<WorldItemPlacerTE> WORLD_ITEM_PLACER_TE = TileEntityType.Builder
            .create(WorldItemPlacerTE::new,
                    TileEntitiesMappings.getMappings(WorldItemPlacerTE.class))
            .build(null);

    static {
        register("world_item_placer_tile", WORLD_ITEM_PLACER_TE);
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
