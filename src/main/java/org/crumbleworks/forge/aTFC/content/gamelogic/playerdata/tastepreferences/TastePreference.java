package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata.tastepreferences;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

import org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile.Flavour;
import org.crumbleworks.forge.aTFC.content.gamelogic.tasteprofile.Taste;

import net.minecraft.entity.player.PlayerEntity;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class TastePreference {

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

    private final Map<Taste, Integer> tasteDesires;
    private final Map<Flavour, Integer> flavourDesires;

    public TastePreference(long worldSeed, PlayerEntity player) {
        Map<Taste, Integer> tasteDesires = new HashMap<>();
        Map<Flavour, Integer> flavourDesires = new HashMap<>();

        List<String> playerNameNumbers = new ArrayList<>();
        for(char c : player.getName().getString().toCharArray()) {
            playerNameNumbers.add(String.valueOf((int)c));
        }
        String playerNumber = playerNameNumbers.stream()
                .collect(Collectors.joining());
        long parsedPlayerNumber = Long.parseLong(playerNumber);

        Random rand = new Random(worldSeed + parsedPlayerNumber);

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

    public int desireFor(Taste taste) {
        return tasteDesires.get(taste);
    }

    public int desireFor(Flavour flavour) {
        return flavourDesires.get(flavour);
    }
}
