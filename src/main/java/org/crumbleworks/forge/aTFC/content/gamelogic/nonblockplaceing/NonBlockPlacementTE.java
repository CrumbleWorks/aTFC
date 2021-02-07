package org.crumbleworks.forge.aTFC.content.gamelogic.nonblockplaceing;

import org.crumbleworks.forge.aTFC.wiring.TileEntities;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class NonBlockPlacementTE extends TileEntity implements ITickableTileEntity {
    
    public NonBlockPlacementTE() {
        super(TileEntities.NON_BLOCK_PLACER_TE);
    }
    
    @Override
    public void tick() {
        //TODO
    }
    
    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap) {
        // TODO Auto-generated method stub
        return super.getCapability(cap);
    }
}
