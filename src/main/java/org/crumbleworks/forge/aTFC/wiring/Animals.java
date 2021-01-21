package org.crumbleworks.forge.aTFC.wiring;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.entities.animals.EurasianCootEntity;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockModels;
import org.crumbleworks.forge.aTFC.dataGeneration.BlockStates;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class Animals implements Wireable {

    public static final RegistryObject<EntityType<EurasianCootEntity>> EURASIAN_COOT_ENTITY = Wireable.ENTITIES
            .register("eurasian_coot", () -> EntityType.Builder
                    .<EurasianCootEntity> create(EurasianCootEntity::new,
                            EntityClassification.CREATURE)
                    .size(0.5f, 0.5f)
                    .build(new ResourceLocation(Main.MOD_ID, "eurasian_coot")
                            .toString()));

    // public static final RegistryObject<Item> EURASIAN_COOT_SPAWN_EGG_ITEM =
    // Wireable.ITEMS
    // .register("eurasian_coot_spawn_egg",
    // () -> new SpawnEggItem(EURASIAN_COOT_ENTITY.get(),
    // 0x090909, 0xC93434,
    // new Item.Properties().group(ItemGroup.MISC)));

    @Override
    public void generateBlockModels(BlockModels bm) {}

    @Override
    public void generateItemModels(ItemModels im) {}

    @Override
    public void generateBlockStates(BlockStates bs) {}

    @Override
    public void generateLootTables(LootTables lt) {}

    @Override
    public void englishTranslations(Translations tren) {
        tren.add(EURASIAN_COOT_ENTITY.get(), "Eurasian Coot");
        // tren.add(EURASIAN_COOT_SPAWN_EGG_ITEM.get(),
        // "Eurasian Coot Spawn Egg");
    }

    @Override
    public void swissTranslations(Translations trch) {
        trch.add(EURASIAN_COOT_ENTITY.get(), "Taucherli");
        // trch.add(EURASIAN_COOT_SPAWN_EGG_ITEM.get(), "Taucherli Spawn
        // Egg");
    }

}
