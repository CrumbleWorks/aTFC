package org.crumbleworks.forge.aTFC.subscribers;

import java.util.concurrent.atomic.AtomicInteger;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.utilities.Util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class ForgeContinuousEvents {
    
    static AtomicInteger num = new AtomicInteger();

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        if(event.getEntity() instanceof PlayerEntity && event.getWorld() instanceof ServerWorld) {
            Util.sendMessageToAllPlayers(event.getWorld(), "HALLO GEILE CHEIB: " + num.getAndIncrement());
        }
    }
}
