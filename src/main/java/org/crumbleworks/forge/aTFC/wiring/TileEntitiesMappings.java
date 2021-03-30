package org.crumbleworks.forge.aTFC.wiring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.NonBlockPlacementTE;

import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TileEntitiesMappings {

    private static final Map<Class<? extends TileEntity>, List<Block>> BLOCK_MAPPINGS = new HashMap<>();

    public static final void addMapping(
            Class<? extends TileEntity> tileEntity, Block block) {
        if(!BLOCK_MAPPINGS.containsKey(tileEntity)) {
            BLOCK_MAPPINGS.put(tileEntity, new ArrayList<>());
        }

        BLOCK_MAPPINGS.get(tileEntity).add(block);
    }

    public static final Block[] getMappings(
            Class<? extends TileEntity> tileEntity) {
        if(!BLOCK_MAPPINGS.containsKey(tileEntity)) {
            return new Block[] {};
        }

        List<Block> blocks = BLOCK_MAPPINGS.get(NonBlockPlacementTE.class);
        return blocks.toArray(new Block[blocks.size()]);
    }
}
