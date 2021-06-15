package org.crumbleworks.forge.aTFC.utilities;

import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private static final Logger LOGGER = LoggerFactory.getLogger(Util.class);

    public static final boolean isServerWorld(World world) {
        return world.isClientSide() == false;
    }

    public static final void sendMessageToAllPlayers(World world,
            String message) {
        sendMessageToAllPlayers(world, new StringTextComponent(message));
    }

    public static final void sendMessageToAllPlayers(World world,
            ITextComponent message) {
        for(PlayerEntity player : world.players()) {
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

    public static final int gridSlot2x2up(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getLocation().x, rtr.getLocation().z);
    }
    
    public static final int gridSlot2x2north(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getLocation().x, rtr.getLocation().y);
    }
    
    public static final int gridSlot2x2east(RayTraceResult rtr) {
        return gridSlot2X2(-rtr.getLocation().y, rtr.getLocation().z);
    }
    
    public static final int gridSlot2x2south(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getLocation().x, -rtr.getLocation().y);
    }
    
    public static final int gridSlot2x2west(RayTraceResult rtr) {
        return gridSlot2X2(rtr.getLocation().y, rtr.getLocation().z);
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
        int _a = ((double)Math.round(a) < a ? 1 : 0);
        int _b = ((double)Math.round(b) < b ? 2 : 0);
        LOGGER.debug(
                "Assigning Gridslot from vector-a: {}[rnd{}], vector-b: {}[rnd{}] -> {} + {} = {}",
                a, (double)Math.round(a), b, (double)Math.round(b), _a, _b, _a + _b);
        return _a + _b;
    }
}
