package org.crumbleworks.forge.aTFC.content.entities.goals;

import java.util.EnumSet;

import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Goal which makes a mob move away from a player if they get too close.
 *
 * @author Patrick Bächli
 * @since CURRENT_VERSION
 */
public class MoveAwayFromPlayerGoal extends Goal {

    private MobEntity mob;
    private double minDistance;
    private double speed;

    /**
     * @param mob
     *            A mob entity
     * @param minDistance
     *            Minimum distance to player. If the actual distance is lower
     *            than this value, the mob will start to move away from the
     *            player.
     * @param speed
     *            Speed with which {@code mob} moves away from the player
     */
    public MoveAwayFromPlayerGoal(MobEntity mob, double minDistance,
            double speed) {
        this.mob = mob;
        this.minDistance = minDistance;
        this.speed = speed;

        setMutexFlags(EnumSet.of(Goal.Flag.MOVE));
    }

    @Override
    public boolean shouldExecute() {
        return mob.world.getClosestPlayer(mob, minDistance) != null;
    }

    @Override
    public void tick() {
        PlayerEntity closestPlayer = mob.world.getClosestPlayer(mob,
                minDistance);

        BlockPos mobPosition = mob.getPosition();
        BlockPos playerPosition = closestPlayer.getPosition();

        BlockPos difference = mobPosition.subtract(playerPosition);
        BlockPos newTarget = mobPosition.add(difference);
        mob.getNavigator().tryMoveToXYZ(newTarget.getX(), newTarget.getY(),
                newTarget.getZ(), speed);
    }

}
