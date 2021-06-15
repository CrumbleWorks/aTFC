package org.crumbleworks.forge.aTFC.content.gamelogic.customstats;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;

import com.mojang.serialization.Lifecycle;

import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.stats.Stats;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.SimpleRegistry;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class aTFCStats {

    public static final RegistryKey<Registry<ResourceLocation>> ATFC_STAT_KEY = RegistryKey
            .createRegistryKey(
                    new ResourceLocation(Main.MOD_ID, "atfc_stat"));
    public static final Registry<ResourceLocation> ATFC_STAT = addRegistry(
            ATFC_STAT_KEY,
            new SimpleRegistry<>(ATFC_STAT_KEY, Lifecycle.experimental()),
            () -> Stats.JUMP, Lifecycle.experimental());
    public static StatType<ResourceLocation> aTFC_CUSTOM = new StatType<>(
            ATFC_STAT);

    private static <T, R extends MutableRegistry<T>> R addRegistry(
            RegistryKey<? extends Registry<T>> registryKey, R instance,
            Supplier<T> objectSupplier, Lifecycle lifecycle) {
        ResourceLocation resourcelocation = registryKey.location();
        Registry.LOADERS.put(resourcelocation, objectSupplier);
        MutableRegistry<R> mutableregistry = (MutableRegistry<R>)Registry.WRITABLE_REGISTRY;
        return (R)mutableregistry.register((RegistryKey)registryKey, instance,
                lifecycle);
    }

    public static Stat<ResourceLocation> registerAtfcStat(String key,
            IStatFormatter formatter) {
        ResourceLocation resourcelocation = new ResourceLocation(Main.MOD_ID,
                key);
        Registry.register(ATFC_STAT, key, resourcelocation);
        return aTFC_CUSTOM.get(resourcelocation, formatter);
    }
}
