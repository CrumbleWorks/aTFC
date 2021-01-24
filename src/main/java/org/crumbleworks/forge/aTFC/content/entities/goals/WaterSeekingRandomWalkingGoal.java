package org.crumbleworks.forge.aTFC.content.entities.goals;

import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.entity.ai.goal.RandomWalkingGoal;
import net.minecraft.util.math.vector.Vector3d;

public class WaterSeekingRandomWalkingGoal extends RandomWalkingGoal {

    protected final float probability;

    public WaterSeekingRandomWalkingGoal(CreatureEntity creatureIn,
            double speedIn) {
        super(creatureIn, speedIn);
        probability = 0.001f;
    }

    @Override
    protected Vector3d getPosition() {
        if(!creature.isInWaterOrBubbleColumn()) {
            Vector3d vector3d = RandomPositionGenerator.getLandPos(creature,
                    15, 7);
            return vector3d == null ? super.getPosition() : vector3d;
        } else {
            return creature.getRNG().nextFloat() >= probability
                    ? RandomPositionGenerator.getLandPos(creature, 10, 7)
                    : super.getPosition();
        }
    }

}
