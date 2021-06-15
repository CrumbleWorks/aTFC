package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.stream.Collectors;

import org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile.Flavour;
import org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile.Taste;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TastePreference implements INBTSerializable<INBT> {

    public final static int MAX_SWEET_DESIRE = 120;
    public final static int MIN_SWEET_DESIRE = 40;
    public final static int MAX_SOUR_DESIRE = 60;
    public final static int MIN_SOUR_DESIRE = 5;
    public final static int MAX_SALTY_DESIRE = 80;
    public final static int MIN_SALTY_DESIRE = 20;
    public final static int MAX_BITTER_DESIRE = 20;
    public final static int MIN_BITTER_DESIRE = 0;
    public final static int MAX_UMAMI_DESIRE = 120;
    public final static int MIN_UMAMI_DESIRE = 80;

    /* anything lower is considered disgusting */
    public final static int FLAVOUR_DISLIKE_TRESHOLD = 5;
    public final static int FLAVOUR_ENJOY_TRESHOLD = 40;
    public final static int FLAVOUR_LOVE_TRESHOLD = 90;

    private Map<Taste, Integer> tasteDesires;
    private Map<Flavour, Integer> flavourDesires;
    //TODO add another map for foodstuffs once foodstuffs exist
    // -> use same system as flavourdesires

    public TastePreference(long worldSeed, PlayerEntity player) {
        Map<Taste, Integer> tasteDesires = new HashMap<>();
        Map<Flavour, Integer> flavourDesires = new HashMap<>();

        // seed random
        List<String> playerNameNumbers = new ArrayList<>();
        String playerName = player.getGameProfile() != null ? player.getName().getString() : "Yoghurt"; //handle missing profile gracefully..
        for(char c : playerName.toCharArray()) {
            playerNameNumbers.add(String.valueOf((int)c));
        }
        String playerNumber = playerNameNumbers.stream()
                .collect(Collectors.joining());
        long parsedPlayerNumber = Long.parseLong(playerNumber.substring(0, 18));

        Random rand = new Random(
                worldSeed + parsedPlayerNumber + System.nanoTime());

        // calculate tastes
        tasteDesires.put(Taste.SWEET,
                rand.nextInt( (MAX_SWEET_DESIRE - MIN_SWEET_DESIRE) + 1)
                        + MIN_SWEET_DESIRE);
        tasteDesires.put(Taste.SOUR,
                rand.nextInt( (MAX_SOUR_DESIRE - MIN_SOUR_DESIRE) + 1)
                        + MIN_SOUR_DESIRE);
        tasteDesires.put(Taste.SALTY,
                rand.nextInt( (MAX_SALTY_DESIRE - MIN_SALTY_DESIRE) + 1)
                        + MIN_SALTY_DESIRE);
        tasteDesires.put(Taste.BITTER,
                rand.nextInt( (MAX_BITTER_DESIRE - MIN_BITTER_DESIRE) + 1)
                        + MIN_BITTER_DESIRE);
        tasteDesires.put(Taste.UMAMI,
                rand.nextInt( (MAX_UMAMI_DESIRE - MIN_UMAMI_DESIRE) + 1)
                        + MIN_UMAMI_DESIRE);

        // calculate flavours
        for(Flavour f : Flavour.values()) {
            flavourDesires.put(f, rand.nextInt(101));
        }

        this.tasteDesires = Collections.unmodifiableMap(tasteDesires);
        this.flavourDesires = Collections.unmodifiableMap(flavourDesires);
    }

    /**
     * DESERIALIZING CTOR, ONLY USE FOR DESERIALIZING
     */
    public TastePreference() {}

    public int desireFor(Taste taste) {
        return tasteDesires.get(taste);
    }

    public int desireFor(Flavour flavour) {
        return flavourDesires.get(flavour);
    }


    private static final String TASTE_TAG = "tastes";
    private static final String FLAVOUR_TAG = "flavours";

    @Override
    public INBT serializeNBT() {
        CompoundNBT nbt = new CompoundNBT();

        CompoundNBT tastesNbt = new CompoundNBT();
        for(Entry<Taste, Integer> entry : tasteDesires.entrySet()) {
            tastesNbt.putInt(entry.getKey().name(), entry.getValue());
        }
        nbt.put(TASTE_TAG, tastesNbt);

        CompoundNBT flavoursNbt = new CompoundNBT();
        for(Entry<Flavour, Integer> entry : flavourDesires.entrySet()) {
            flavoursNbt.putInt(entry.getKey().name(), entry.getValue());
        }
        nbt.put(FLAVOUR_TAG, flavoursNbt);

        return nbt;
    }

    @Override
    public void deserializeNBT(INBT nbt) {
        Map<Taste, Integer> tasteDesires = new HashMap<>();
        Map<Flavour, Integer> flavourDesires = new HashMap<>();

        CompoundNBT nbtCast = (CompoundNBT)nbt;

        CompoundNBT tastesNbt = nbtCast.getCompound(TASTE_TAG);
        tasteDesires.put(Taste.SWEET, tastesNbt.getInt(Taste.SWEET.name()));
        tasteDesires.put(Taste.SOUR, tastesNbt.getInt(Taste.SOUR.name()));
        tasteDesires.put(Taste.SALTY, tastesNbt.getInt(Taste.SALTY.name()));
        tasteDesires.put(Taste.BITTER, tastesNbt.getInt(Taste.BITTER.name()));
        tasteDesires.put(Taste.UMAMI, tastesNbt.getInt(Taste.UMAMI.name()));

        CompoundNBT flavoursNbt = nbtCast.getCompound(FLAVOUR_TAG);
        for(Flavour f : Flavour.values()) {
            flavourDesires.put(f, flavoursNbt.getInt(f.name()));
        }

        this.tasteDesires = Collections.unmodifiableMap(tasteDesires);
        this.flavourDesires = Collections.unmodifiableMap(flavourDesires);
    }
}
