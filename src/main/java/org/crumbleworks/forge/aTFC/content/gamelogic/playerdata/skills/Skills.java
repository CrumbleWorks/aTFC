package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.skills;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.crumbleworks.forge.aTFC.content.gamelogic.playerskills.Skill;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Skills implements INBTSerializable<INBT> {

    public static final int MAX_SKILL_LEVEL = 4;
    public static final int EXPERIENCE_PER_LEVEL = 1000;

    private Map<Skill, Integer> skillExperience;

    public Skills() {
        Map<Skill, Integer> skillExperience = new HashMap<>();

        for(Skill s : Skill.values()) {
            skillExperience.put(s, 0);
        }

        this.skillExperience = Collections.unmodifiableMap(skillExperience);
    }

    public int skillLevel(Skill skill) {
        return (int) (skillExperience.get(skill) / EXPERIENCE_PER_LEVEL);
    }

    public int partialExp(Skill skill) {
        return skillExperience.get(skill) % EXPERIENCE_PER_LEVEL;
    }

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        for(Entry<Skill, Integer> entry : skillExperience.entrySet()) {
            nbt.putInt(entry.getKey().name(), entry.getValue());
        }

        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        Map<Skill, Integer> skillExperience = new HashMap<>();

        CompoundNBT nbtCast = (CompoundNBT)nbt;

        for(Skill s : Skill.values()) {
            skillExperience.put(s, nbtCast.getInt(s.name()));
        }

        this.skillExperience = Collections.unmodifiableMap(skillExperience);
    }
}
