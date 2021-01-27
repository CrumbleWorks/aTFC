package org.crumbleworks.forge.aTFC.content.entities.passive;

import org.crumbleworks.forge.aTFC.content.entities.goals.MoveAwayFromPlayerGoal;
import org.crumbleworks.forge.aTFC.content.entities.goals.WaterSeekingRandomWalkingGoal;
import org.crumbleworks.forge.aTFC.wiring.entities.passive.EurasianCoot;

import net.minecraft.block.BlockState;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.goal.BreedGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAtGoal;
import net.minecraft.entity.ai.goal.LookRandomlyGoal;
import net.minecraft.entity.ai.goal.PanicGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

public class EurasianCootEntity extends AnimalEntity {

    private static final Ingredient TEMPTATION_ITEMS = Ingredient
            .fromItems(Items.BREAD);

    // Copied from ChickenEntity
    public float wingRotation;
    public float destPos;
    public float oFlapSpeed;
    public float oFlap;
    public float wingRotDelta = 1.0F;

    public EurasianCootEntity(EntityType<? extends EurasianCootEntity> type,
            World worldIn) {
        super(type, worldIn);
        setPathPriority(PathNodeType.WATER, 0.0f);
    }

    @Override
    protected void registerGoals() {
        goalSelector.addGoal(0, new MoveAwayFromPlayerGoal(this, 10d, 1.5d));
        goalSelector.addGoal(1, new SwimGoal(this));
        goalSelector.addGoal(2, new PanicGoal(this, 1.4D));
        goalSelector.addGoal(3, new BreedGoal(this, 1.0D));
        goalSelector.addGoal(4,
                new TemptGoal(this, 1.0D, false, TEMPTATION_ITEMS));
        goalSelector.addGoal(5, new FollowParentGoal(this, 1.1D));
        goalSelector.addGoal(6,
                new WaterSeekingRandomWalkingGoal(this, 1.0D));
        goalSelector.addGoal(7,
                new LookAtGoal(this, PlayerEntity.class, 6.0F));
        goalSelector.addGoal(8, new LookRandomlyGoal(this));
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return isChild() ? sizeIn.height * 0.85F : sizeIn.height * 0.92F;
    }

    public static AttributeModifierMap.MutableAttribute getAttributes() {
        return MobEntity.func_233666_p_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 4.0D)
                .createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    // Mostly copied from ChickenEntity
    public void livingTick() {
        super.livingTick();
        oFlap = wingRotation;
        oFlapSpeed = destPos;
        destPos = (float) ((double)destPos
                + (double) (onGround ? -1 : 4) * 0.3D);
        destPos = MathHelper.clamp(destPos, 0.0F, 1.0F);
        if(!onGround && wingRotDelta < 1.0F) {
            wingRotDelta = 1.0F;
        }

        wingRotDelta = (float) ((double)wingRotDelta * 0.9D);
        Vector3d vector3d = getMotion();
        if(!onGround && vector3d.y < 0.0D) {
            setMotion(vector3d.mul(1.0D, 0.6D, 1.0D));
        }

        wingRotation += wingRotDelta * 2.0F;
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return EurasianCoot.EURASIAN_COOT_SOUND_AMBIENT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return EurasianCoot.EURASIAN_COOT_SOUND_HURT.get();
    }

    @Override
    protected SoundEvent getDeathSound() {
        return EurasianCoot.EURASIAN_COOT_SOUND_DEATH.get();
    }

    // Copied from ChickenEntity
    @Override
    protected void playStepSound(BlockPos pos, BlockState blockIn) {
        playSound(SoundEvents.ENTITY_CHICKEN_STEP, 0.15f, 1.0f);
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld,
            AgeableEntity ageableEntity) {
        return EurasianCoot.EURASIAN_COOT_ENTITY.get().create(serverWorld);
    }

}
