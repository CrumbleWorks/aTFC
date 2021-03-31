package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences.TastePreference;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
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

    private static final String TASTE_PREFS_TAG = "taste_prefs";

    public static class ATFCPlayerDataImplStorage
            implements IStorage<ATFCPlayerData> {

        @Override
        public INBT writeNBT(Capability<ATFCPlayerData> capability,
                ATFCPlayerData instance, Direction side) {

            CompoundNBT nbt = new CompoundNBT();
            nbt.put(TASTE_PREFS_TAG,
                    instance.tastePreferences().serializeNBT());

            return nbt;
        }

        @Override
        public void readNBT(Capability<ATFCPlayerData> capability,
                ATFCPlayerData instance, Direction side, INBT nbt) {

            CompoundNBT nbtCast = (CompoundNBT)nbt;

            TastePreference tastePref = new TastePreference();
            tastePref.deserializeNBT(nbtCast.get(TASTE_PREFS_TAG));
            ((ATFCPlayerDataImpl)instance).tastePreferences = tastePref;
        }
    }
}
