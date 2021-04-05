package org.crumbleworks.forge.aTFC.content.items;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public interface Weighty {

    /** around 28 grams */
    public static int OUNCE = 1;
    /** around 110 grams */
    public static int QUARTER_POUND = 4 * OUNCE;
    /** around 225g, 1/2lb */
    public static int HALF_POUND = 2 * QUARTER_POUND;
    /** around 450g, 1lb */
    public static int POUND = 2 * HALF_POUND;
    /** around 900g (almost a metric kg), 2lb */
    public static int TWO_POUND = 2 * POUND;
    /** around 2-2.5kg */
    public static int FIVE_POUND = 5 * POUND;
    /** around 4-5kg */
    public static int TEN_POUND = 10 * POUND;
    /** around 25kg */
    public static int BUSHEL = 50 * POUND;
    /** around 50kg */
    public static int HUNDREDWEIGHT = 100 * POUND;
    /** around 450kg */
    public static int HALF_TON = 1000 * POUND;
    /** around 900kg */
    public static int SHORT_TON = 2000 * POUND;
    /** around a metric ton */
    public static int LONG_TON = 2240 * POUND;

    /**
     * @return the weight of this item in ounces
     */
    int getWeight();
}
