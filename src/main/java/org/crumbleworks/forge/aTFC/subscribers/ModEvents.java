package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsSchwizerdeutsch;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsUSEnglish;

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
        event.getGenerator().addProvider(new BlockStates(event.getGenerator(),
                event.getExistingFileHelper()));
        event.getGenerator().addProvider(new BlockModels(event.getGenerator(),
                event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModels(event.getGenerator(),
                event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new TranslationsUSEnglish(event.getGenerator()));
        event.getGenerator().addProvider(
                new TranslationsSchwizerdeutsch(event.getGenerator()));
    }
}
