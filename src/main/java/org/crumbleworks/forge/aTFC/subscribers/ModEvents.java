package org.crumbleworks.forge.aTFC.subscribers;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.entities.animals.EurasianCootEntity;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockTags;
import org.crumbleworks.forge.aTFC.dataGeneration.EntityTypeTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemTags;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsSchwizerdeutsch;
import org.crumbleworks.forge.aTFC.dataGeneration.TranslationsUSEnglish;
import org.crumbleworks.forge.aTFC.wiring.Animals;

import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
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
        event.getGenerator().addProvider(new EntityTypeTags(event.getGenerator(),
                Registry.ENTITY_TYPE, event.getExistingFileHelper()));

        event.getGenerator()
                .addProvider(new LootTables(event.getGenerator()));
    }

    @SubscribeEvent
    public static void registerAttributeMaps(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            GlobalEntityTypeAttributes.put(Animals.EURASIAN_COOT_ENTITY.get(),
                    EurasianCootEntity.getCustomAttributes().create());
        });
    }

}
