package org.crumbleworks.forge.aTFC.wiring.entities.passive;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.Tags;
import org.crumbleworks.forge.aTFC.content.entities.passive.EurasianCootEntity;
import org.crumbleworks.forge.aTFC.content.itemgroups.ItemGroups;
import org.crumbleworks.forge.aTFC.dataGeneration.EntityTypeTags;
import org.crumbleworks.forge.aTFC.dataGeneration.ItemModels;
import org.crumbleworks.forge.aTFC.dataGeneration.LootTables;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.loot.ConstantRange;
import net.minecraft.loot.ItemLootEntry;
import net.minecraft.loot.LootPool;
import net.minecraft.loot.LootTable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.RegistryObject;

public class EurasianCoot implements Wireable {

    private static final String name_eurasian_coot = "eurasian_coot";
    private static final String name_eurasian_coot_feather_item = "eurasian_coot_feather";

    public static final RegistryObject<EntityType<EurasianCootEntity>> EURASIAN_COOT_ENTITY = Wireable.ENTITIES
            .register(name_eurasian_coot, () -> EntityType.Builder
                    .<EurasianCootEntity> create(EurasianCootEntity::new,
                            EntityClassification.CREATURE)
                    .size(0.5f, 0.5f)
                    .build(new ResourceLocation(Main.MOD_ID,
                            name_eurasian_coot).toString()));

    public static final RegistryObject<SoundEvent> EURASIAN_COOT_SOUND_AMBIENT = Wireable.SOUNDS
            .register("eurasian_coot.ambient",
                    () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,
                            "eurasian_coot.ambient")));
    public static final RegistryObject<SoundEvent> EURASIAN_COOT_SOUND_HURT = Wireable.SOUNDS
            .register("eurasian_coot.hurt",
                    () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,
                            "eurasian_coot.hurt")));
    public static final RegistryObject<SoundEvent> EURASIAN_COOT_SOUND_DEATH = Wireable.SOUNDS
            .register("eurasian_coot.death",
                    () -> new SoundEvent(new ResourceLocation(Main.MOD_ID,
                            "eurasian_coot.death")));

    public static final RegistryObject<Item> EURASIAN_COOT_FEATHER_ITEM = Wireable.ITEMS
            .register(name_eurasian_coot_feather_item, () -> new Item(
                    new Item.Properties().group(ItemGroups.MATERIALS)));

    // TODO Doesn't work right now. Forge says the EntityType can't be found
    // when initialising the spawn egg.
    // public static final RegistryObject<Item> EURASIAN_COOT_SPAWN_EGG_ITEM =
    // Wireable.ITEMS
    // .register("eurasian_coot_spawn_egg",
    // () -> new SpawnEggItem(EURASIAN_COOT_ENTITY.get(),
    // 0x090909, 0xC93434,
    // new Item.Properties().group(ItemGroup.MISC)));

    @Override
    public void generateItemModels(ItemModels im) {
        im.getBuilder(name_eurasian_coot_feather_item)
                .parent(im.getExistingFile(im.mcLoc("item/generated")))
                .texture("layer0", im.modLoc("item/eurasian_coot_feather"));
    }

    @Override
    public void generateLootTables(LootTables lt) {
        lt.addEntity(name_eurasian_coot, LootTable.builder()
                .addLootPool(LootPool.builder().name(name_eurasian_coot)
                        .rolls(ConstantRange.of(1)).addEntry(ItemLootEntry
                                .builder(EURASIAN_COOT_FEATHER_ITEM.get()))));
    }

    @Override
    public void englishTranslations(Translations tren) {
        tren.add(EURASIAN_COOT_ENTITY.get(), "Eurasian Coot");

        // tren.add(EURASIAN_COOT_SPAWN_EGG_ITEM.get(),
        // "Eurasian Coot Spawn Egg");

        tren.add(subtitleKey(EURASIAN_COOT_SOUND_AMBIENT.get()),
                "Eurasian Coot chirps");
        tren.add(subtitleKey(EURASIAN_COOT_SOUND_HURT.get()),
                "Eurasian Coot hurts");
        tren.add(subtitleKey(EURASIAN_COOT_SOUND_DEATH.get()),
                "Eurasian Coot dies");

        tren.add(EURASIAN_COOT_FEATHER_ITEM.get(), "Eurasian Coot Feather");
    }

    @Override
    public void swissTranslations(Translations trch) {
        trch.add(EURASIAN_COOT_ENTITY.get(), "Taucherli");

        // trch.add(EURASIAN_COOT_SPAWN_EGG_ITEM.get(), "Taucherli Spawn
        // Egg");

        trch.add(subtitleKey(EURASIAN_COOT_SOUND_AMBIENT.get()),
                "Taucherli zwitscheret");
        trch.add(subtitleKey(EURASIAN_COOT_SOUND_HURT.get()),
                "Taucherli het Aua");
        trch.add(subtitleKey(EURASIAN_COOT_SOUND_DEATH.get()),
                "Taucherli stirbt");

        trch.add(EURASIAN_COOT_FEATHER_ITEM.get(), "Taucherli F\\u00c4dere");
    }

    @Override
    public void registerForEntityTypeTags(EntityTypeTags et) {
        et.itemTagBuilder(Tags.EntityTypes.POULTRY)
                .add(EURASIAN_COOT_ENTITY.get());
    }

    // TODO Besser mache
    private String subtitleKey(SoundEvent se) {
        return "subtitles.atfc.entity." + se.getName().getPath();
    }

}
