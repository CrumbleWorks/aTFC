package org.crumbleworks.forge.aTFC.content.gamelogic.drying;

import net.minecraft.nbt.FloatNBT;
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
public class DryingImpl implements Drying {

    private float progress = 0.0f;

    @Override
    public float getDryingProgress() {
        return progress;
    }

    @Override
    public void setDryingProgress(float progress) {
        this.progress = progress;
    }

    public static class DryingImplStorage
            implements IStorage<Drying> {

        @Override
        public INBT writeNBT(Capability<Drying> capability,
                Drying instance, Direction side) {
            return FloatNBT.valueOf( ((DryingImpl)instance).progress);
        }

        @Override
        public void readNBT(Capability<Drying> capability,
                Drying instance, Direction side, INBT nbt) {
            ((DryingImpl)instance).progress = ((FloatNBT)nbt).getAsFloat();
        }
    }

}
