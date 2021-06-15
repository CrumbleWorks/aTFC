package org.crumbleworks.forge.aTFC.content.gamelogic.drying;

import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;


/**
 * TODO
 * 
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class DryingCapabilityProvider
        implements ICapabilitySerializable<INBT> {

    @CapabilityInject(Drying.class)
    public static Capability<Drying> CAPABILITY_DRYING = null;
    private final Drying drying;

    public DryingCapabilityProvider() {
        drying = new DryingImpl();
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap,
            Direction side) {
        if(CAPABILITY_DRYING == cap) {
            return (LazyOptional<T>)LazyOptional.of(() -> drying);
        }

        return LazyOptional.empty();
    }

    @Override
    public INBT serializeNBT() {
        return CAPABILITY_DRYING.getStorage()
                .writeNBT(CAPABILITY_DRYING, drying, null);
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        CAPABILITY_DRYING.getStorage().readNBT(CAPABILITY_DRYING, drying,
                null, nbt);
    }
}
