package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.Capability.IStorage;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ATFCPlayerDataStorage implements IStorage<ATFCPlayerData> {

    @Override
    public INBT writeNBT(Capability<ATFCPlayerData> capability, ATFCPlayerData instance, Direction side) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void readNBT(Capability<ATFCPlayerData> capability, ATFCPlayerData instance, Direction side, INBT nbt) {
        // TODO Auto-generated method stub
    }
}
