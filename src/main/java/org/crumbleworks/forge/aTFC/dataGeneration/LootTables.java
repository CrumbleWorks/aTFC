package org.crumbleworks.forge.aTFC.dataGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootParameterSets;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.loot.ValidationTracker;
import net.minecraft.util.ResourceLocation;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class LootTables extends LootTableProvider {

    private final List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> lootTables = new ArrayList<>();

    /**
     * @param dataGeneratorIn
     */
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    // override this so we don't validate minecraft loottables and get errors
    @Override
    protected void validate(Map<ResourceLocation, LootTable> map,
            ValidationTracker validationtracker) {}

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
        for(Wireable wireable : Main.wireables) {
            wireable.generateLootTables(this);
        }

        return lootTables;
    }

    public final void addBlock(String name, Builder lootTableBuilder) {
        lootTables.add(Pair.of(() -> (c) -> {
            c.accept(new ResourceLocation(Main.MOD_ID, "blocks/" + name),
                    lootTableBuilder);
        }, LootParameterSets.BLOCK));
    }

    public final void addEntity(String name, Builder lootTableBuilder) {
        lootTables.add(Pair.of(() -> (c) -> {
            c.accept(new ResourceLocation(Main.MOD_ID, "entities/" + name),
                    lootTableBuilder);
        }, LootParameterSets.ENTITY));
    }

    public final void addChest(String name, Builder lootTableBuilder) {
        lootTables.add(Pair.of(() -> (c) -> {
            c.accept(new ResourceLocation(Main.MOD_ID, "chests/" + name),
                    lootTableBuilder);
        }, LootParameterSets.CHEST));
    }

    public final void addFishing(String name, Builder lootTableBuilder) {
        lootTables.add(Pair.of(() -> (c) -> {
            c.accept(
                    new ResourceLocation(Main.MOD_ID,
                            "gameplay/fishing/" + name),
                    lootTableBuilder);
        }, LootParameterSets.FISHING));
    }

    public final void addGift(String name, Builder lootTableBuilder) {
        lootTables.add(Pair.of(() -> (c) -> {
            c.accept(new ResourceLocation(Main.MOD_ID, "gameplay/" + name),
                    lootTableBuilder);
        }, LootParameterSets.GIFT));
    }
}
