package org.crumbleworks.forge.aTFC.dataGeneration;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import com.mojang.datafixers.util.Pair;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.loot.LootParameterSet;
import net.minecraft.loot.LootTable.Builder;
import net.minecraft.util.ResourceLocation;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class LootTables extends LootTableProvider {

    /**
     * @param dataGeneratorIn
     */
    public LootTables(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> getTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, Builder>>>, LootParameterSet>> lootTables = new ArrayList<>();

        for(Wireable wireable : Main.wireables) {
            lootTables.addAll(wireable.generateLootTables());
        }

        lootTables.addAll(super.getTables());
        return lootTables;
    }
    
    public static LootParameterSet createParams(String name, Consumer<LootParameterSet.Builder> lootConsumer) {
        LootParameterSet.Builder lootParamBuilder = new LootParameterSet.Builder();
        lootConsumer.accept(lootParamBuilder);
        LootParameterSet lootparameterset = lootParamBuilder.build();
        return lootparameterset;
    }
}
