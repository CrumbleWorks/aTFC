package org.crumbleworks.forge.aTFC.content.gamelogic.playerskills;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.content.gamelogic.customstats.aTFCStats;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.networking.Networking;
import org.crumbleworks.forge.aTFC.wiring.Wireable;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.stats.IStatFormatter;
import net.minecraft.stats.Stat;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class WiringAndEvents implements Wireable {

    public static final KeyBinding STATS_KEY = new KeyBinding(
            "key.atfc.statsButton", GLFW.GLFW_KEY_K,
            "key.atfc.categories.atfc_bindings");
    static Stat<ResourceLocation> PRESSED_K = null;

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void handleKeyBindings(ClientTickEvent event) {
            if(STATS_KEY.isPressed() && Minecraft.getInstance().currentScreen == null) {
                Minecraft.getInstance().displayGuiScreen(new StatsScreen());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
    static final class ModEvents {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerAtfcStatsForge(
                RegistryEvent.Register<StatType<?>> event) {
            PRESSED_K = aTFCStats.registerAtfcStat("pressed_k",
                    IStatFormatter.DEFAULT);
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                Networking.registerMessageType(StatCheckMessage.class);
            });
        }

        @SubscribeEvent
        public static void registerKeyBindings(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ClientRegistry.registerKeyBinding(STATS_KEY);
            });
        }
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add("key.atfc.statsButton", "Show Skills");
        tr.add("stat.atfc.pressed_k", "Looked at skills screen");

        tr.add("gui.atfc.stats.title", "Skills");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add("key.atfc.statsButton", "Zeig d Skills");
        tr.add("stat.atfc.pressed_k", "D Skills liebgäuglet");

        tr.add("gui.atfc.stats.title", "Skills");
    }
}
