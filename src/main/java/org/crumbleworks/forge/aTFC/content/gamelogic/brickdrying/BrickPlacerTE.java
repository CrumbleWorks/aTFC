package org.crumbleworks.forge.aTFC.content.gamelogic.brickdrying;

import org.crumbleworks.forge.aTFC.wiring.TileEntities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BrickPlacerTE extends TileEntity implements ITickableTileEntity {
    
    public BrickPlacerTE() {
        super(TileEntities.BRICK_PLACER_TE);
    }

    @Override
    public void tick() {
        System.out.println("YOLO SWAGGOLO");
    }
}
