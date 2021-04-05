package org.crumbleworks.forge.aTFC.utilities;

import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Util {

    public static final boolean isServerWorld(World world) {
        return world.isRemote() == false;
    }

    public static final void sendMessageToAllPlayers(World world,
            String message) {
        sendMessageToAllPlayers(world, new StringTextComponent(message));
    }

    public static final void sendMessageToAllPlayers(World world,
            ITextComponent message) {
        for(PlayerEntity player : world.getPlayers()) {
            player.sendMessage(message, null);
        }
    }

    public static final void sendMessageToPlayer(PlayerEntity player,
            String message) {
        sendMessageToPlayer(player, new StringTextComponent(message));
    }

    public static final void sendMessageToPlayer(PlayerEntity player,
            ITextComponent message) {
        player.sendMessage(message, null);
    }

    /**
     * @param marker
     *            an interface that marks the {@link Block Blocks} you want
     * @return a set of blocks that implement the <code>marker</code>
     */
    public static final Set<Block> getBlocks(Class<?> marker) {
        return ForgeRegistries.BLOCKS.getValues().stream()
                .filter(b -> marker.isInstance(b))
                .collect(Collectors.toSet());
    }

    /**
     * @param marker
     *            an interface that marks the {@link Item Items} you want
     * @return a set of items that implement the <code>marker</code>
     */
    public static final Set<Item> getItems(Class<?> marker) {
        return ForgeRegistries.ITEMS.getValues().stream()
                .filter(i -> marker.isInstance(i))
                .collect(Collectors.toSet());
    }

    public static final int gridSlot2x2XZ(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getHitVec().x, rtr.getHitVec().z);
    }

    public static final int gridSlot2x2XY(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getHitVec().x, rtr.getHitVec().y);
    }

    public static final int gridSlot2x2ZY(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getHitVec().z, rtr.getHitVec().y);
    }

    /**
     * Determines a position in a grid
     * 
     * <pre>
     *  0 1
     *  2 3
     * </pre>
     * 
     * by testing if the points (<code>double</code>) on each axis are closer
     * to the next smaller (left/up) or larger (right/down) integer.
     * 
     * @param a
     *            the first axis
     * @param b
     *            the second axis
     * @return a number from 0 to 3
     */
    public static final int gridSlot2X2(double a, double b) {
        return (Math.round(a) < a ? 1 : 0) + (Math.round(b) < b ? 2 : 0);
    }
}
