package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;

import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
public final class ModEvents {

    @SubscribeEvent
    public static void registerDatagenerators(GatherDataEvent event) {
        event.getGenerator().addProvider(new BlockModels(event.getGenerator(),
                event.getExistingFileHelper()));
    }
}
