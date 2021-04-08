package org.crumbleworks.forge.aTFC.content.gamelogic.drying.bricks;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.tileentities.aTFCSpecialInventoryTE;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraftforge.common.util.LazyOptional;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickDryPlacerTE extends aTFCSpecialInventoryTE
        implements ITickableTileEntity {

    private static final String STORAGE_KEY = Main.MOD_ID + "_BDP_inv";

    public BrickDryPlacerTE() {
        super(BrickDryingWiringAndEvents.BRICK_PLACER_TE.get(),
                LazyOptional.of(() -> new BrickDryingItemStackHandler()),
                STORAGE_KEY);
    }

    @Override
    public void tick() {
        //TODO drying logic
    }
}
