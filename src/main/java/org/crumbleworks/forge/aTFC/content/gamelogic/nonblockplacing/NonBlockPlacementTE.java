package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplacing;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.tileentities.aTFCSpecialInventoryTE;

import net.minecraftforge.common.util.LazyOptional;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementTE extends aTFCSpecialInventoryTE {

    private static final String STORAGE_KEY = Main.MOD_ID + "_NBP_inv";

    public NonBlockPlacementTE() {
        super(NonBlockPlacementWiringAndEvents.NON_BLOCK_PLACER_TE.get(),
                LazyOptional
                        .of(() -> new NonBlockPlacementItemStackHandler()),
                STORAGE_KEY);
    }
}
