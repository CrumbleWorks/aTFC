package org.crumbleworks.forge.aTFC.content.entities.animals;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.passive.ChickenEntity;
import net.minecraft.world.World;

public class EurasianCootEntity extends ChickenEntity {

    public EurasianCootEntity(EntityType<? extends ChickenEntity> type,
            World worldIn) {
        super(type, worldIn);
    }

    public static AttributeModifierMap.MutableAttribute getCustomAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

}
