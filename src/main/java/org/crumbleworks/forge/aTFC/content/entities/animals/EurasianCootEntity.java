package org.crumbleworks.forge.aTFC.content.entities.animals;

import net.minecraft.entity.EntityType;
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
        // TODO Which attributes are mandatory?
        return AttributeModifierMap.createMutableAttribute()
                .createMutableAttribute(Attributes.MAX_HEALTH)
                .createMutableAttribute(Attributes.FOLLOW_RANGE)
                .createMutableAttribute(Attributes.KNOCKBACK_RESISTANCE)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED)
                .createMutableAttribute(Attributes.FLYING_SPEED)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE)
                .createMutableAttribute(Attributes.ATTACK_KNOCKBACK)
                .createMutableAttribute(Attributes.ATTACK_SPEED)
                .createMutableAttribute(Attributes.ARMOR)
                .createMutableAttribute(Attributes.ARMOR_TOUGHNESS)
                .createMutableAttribute(Attributes.LUCK)
                .createMutableAttribute(
                        Attributes.ZOMBIE_SPAWN_REINFORCEMENTS)
                .createMutableAttribute(Attributes.HORSE_JUMP_STRENGTH)
                .createMutableAttribute(
                        net.minecraftforge.common.ForgeMod.SWIM_SPEED.get())
                .createMutableAttribute(
                        net.minecraftforge.common.ForgeMod.NAMETAG_DISTANCE
                                .get())
                .createMutableAttribute(
                        net.minecraftforge.common.ForgeMod.ENTITY_GRAVITY
                                .get());
    }

}
