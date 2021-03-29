package org.crumbleworks.forge.aTFC.content.gamelogic.playerdata;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class WiringAndEvents implements Wireable {

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void registerCapabilities(
                AttachCapabilitiesEvent<Entity> event) {
            if(! (event.getObject() instanceof PlayerEntity)) {
                return;
            }

            // TODO something with capabilites..
        }
    }

    @Override
    public void englishTranslations(Translations tr) {}

    @Override
    public void swissTranslations(Translations tr) {}
}
