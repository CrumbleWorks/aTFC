package org.crumbleworks.forge.aTFC.content.entities.goals;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;

/**
 * Goal which makes an animal move away from a player if they get too close.
 *
 * @author Patrick Bächli
 * @since CURRENT_VERSION
 */
public class MoveAwayFromPlayerGoal extends Goal {

    private AnimalEntity animal;
    private double minDistance;
    private double speed;

    /**
     * @param animal
     *            An animal entity
     * @param minDistance
     *            Minimum distance to player. If the actual distance is lower
     *            than this value, the animal will start to move away from the
     *            player.
     * @param speed
     *            Speed with which {@code animal} moves away from the player
     */
    public MoveAwayFromPlayerGoal(AnimalEntity animal, double minDistance,
            double speed) {
        this.animal = animal;
        this.minDistance = minDistance;
        this.speed = speed;
    }

    @Override
    public boolean shouldExecute() {
        return animal.world.getClosestPlayer(animal, minDistance) != null;
    }

    @Override
    public void tick() {
        PlayerEntity closestPlayer = animal.world.getClosestPlayer(animal,
                minDistance);

        BlockPos animalPosition = animal.getPosition();
        BlockPos playerPosition = closestPlayer.getPosition();

        BlockPos difference = animalPosition.subtract(playerPosition);
        BlockPos newTarget = animalPosition.add(difference);
        animal.getNavigator().tryMoveToXYZ(newTarget.getX(), newTarget.getY(),
                newTarget.getZ(), speed);
    }

}
