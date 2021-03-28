package org.crumbleworks.forge.aTFC.utilities;

import java.util.Set;
import java.util.stream.Collectors;

import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
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
    
    public static final void sendMessageToPlayer(PlayerEntity player, String message) {
        sendMessageToPlayer(player, new StringTextComponent(message));
    }
    
    public static final void sendMessageToPlayer(PlayerEntity player, ITextComponent message) {
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
}
