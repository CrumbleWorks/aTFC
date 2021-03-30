package org.crumbleworks.forge.aTFC.wiring.tileentityblocks;

import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.NonBlockPlacementBlock;
import org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing.NonBlockPlacementTE;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.wiring.TileEntitiesMappings;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.Block;
import net.minecraftforge.fml.RegistryObject;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacement implements Wireable {

    private static final String name = "nonblockplacement";

    public static final RegistryObject<Block> NONBLOCKPLACEMENT_BLOCK = BLOCKS
            .register(name, () -> new NonBlockPlacementBlock());

    @Override
    public void generateBlockModels(BlockModels bm) {
        bm.getBuilder(name); // empty model
    }

    @Override
    public void generateBlockStates(BlockStates bs) {
        bs.simpleState(name, NONBLOCKPLACEMENT_BLOCK.get());
    }

    @Override
    public void registerTileEntities(TileEntitiesMappings tm) {
        tm.addMapping(NonBlockPlacementTE.class,
                NONBLOCKPLACEMENT_BLOCK.get());
    }
}
