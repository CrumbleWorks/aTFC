package org.crumbleworks.forge.aTFC.content;

import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.material.PushReaction;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Materials {

    // Used for pure Tileentity blocks and such
    public static final Material ABSTRACT_BLOCKS = new Material(
            MaterialColor.AIR, false, false, false, false, false, true,
            PushReaction.DESTROY);

    public static final Material CLAY_SOIL = new Material(MaterialColor.GRASS,
            false, true, true, true, false, false, PushReaction.NORMAL);
    public static final Material GRAVEL = new Material(
            MaterialColor.LIGHT_GRAY, false, true, true, true, false, false,
            PushReaction.PUSH_ONLY);
    public static final Material PEAT = new Material(MaterialColor.GRASS,
            false, true, true, true, true, false, PushReaction.NORMAL);
    public static final Material SAND = new Material(MaterialColor.SAND,
            false, true, true, true, false, false, PushReaction.PUSH_ONLY);
    public static final Material SOIL = new Material(MaterialColor.GRASS,
            false, true, true, true, false, false, PushReaction.PUSH_ONLY);
}
