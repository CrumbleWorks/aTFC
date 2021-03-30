package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;


/**
 * This is what we 'attach' to the already existing entity. Basically a
 * `component` in an ECS...
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ATFCPlayerDataCapabilityProvider
        implements ICapabilitySerializable<INBT> {

    @CapabilityInject(ATFCPlayerData.class)
    public static Capability<ATFCPlayerData> CAPABILITY_ATFCPLAYERDATA = null;
    private ATFCPlayerData atfcPlayerData;

    public ATFCPlayerDataCapabilityProvider(long worldSeed,
            PlayerEntity player) {
        atfcPlayerData = new ATFCPlayerDataImpl(worldSeed, player);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap,
            Direction side) {
        if(CAPABILITY_ATFCPLAYERDATA == cap) {
            return (LazyOptional<T>)LazyOptional.of(() -> atfcPlayerData);
        }

        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CAPABILITY_ATFCPLAYERDATA.getStorage()
                .writeNBT(CAPABILITY_ATFCPLAYERDATA, atfcPlayerData, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CAPABILITY_ATFCPLAYERDATA.getStorage().readNBT(
                CAPABILITY_ATFCPLAYERDATA, atfcPlayerData,
                null, nbt);
    }
}
