package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.util.registry.Registry;
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
public final class DatageneratorRegisteringEvents {
    
    @SubscribeEvent
    public static void registerDatagenerators(GatherDataEvent event) {
        event.getGenerator().addProvider(new BlockModels(event.getGenerator(),
                event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemModels(event.getGenerator(),
                event.getExistingFileHelper()));

        event.getGenerator().addProvider(new BlockStates(event.getGenerator(),
                event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new TranslationsUSEnglish(event.getGenerator()));
        event.getGenerator().addProvider(
                new TranslationsSchwizerdeutsch(event.getGenerator()));

        event.getGenerator().addProvider(new BlockTags(event.getGenerator(),
                Registry.BLOCK, event.getExistingFileHelper()));
        event.getGenerator().addProvider(new ItemTags(event.getGenerator(),
                Registry.ITEM, event.getExistingFileHelper()));
        event.getGenerator()
                .addProvider(new EntityTypeTags(event.getGenerator(),
                        Registry.ENTITY_TYPE, event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new LootTables(event.getGenerator()));

        event.getGenerator().addProvider(new Recipes(event.getGenerator()));
    }
}
