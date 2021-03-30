package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences.TastePreference;

import net.minecraft.entity.player.PlayerEntity;
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
public class ATFCPlayerDataImpl implements ATFCPlayerData {

    private TastePreference tastePreferences;

    public ATFCPlayerDataImpl(long worldSeed, PlayerEntity player) {
        tastePreferences = new TastePreference(worldSeed, player);
    }
    
    public ATFCPlayerDataImpl() {
        tastePreferences = new TastePreference();
    }

    @Override
    public TastePreference tastePreferences() {
        return tastePreferences;
    }

    public static class ATFCPlayerDataImplStorage
            implements IStorage<ATFCPlayerData> {

        @Override
        public INBT writeNBT(Capability<ATFCPlayerData> capability,
                ATFCPlayerData instance, Direction side) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public void readNBT(Capability<ATFCPlayerData> capability,
                ATFCPlayerData instance, Direction side, INBT nbt) {
            // TODO Auto-generated method stub
        }
    }
}
