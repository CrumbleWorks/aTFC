package org.crumbleworks.forge.aTFC.content.gamelogic.customstats;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.dataGeneration.Translations;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.stats.StatType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.registries.ForgeRegistries;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class WiringAndEvents implements Wireable {

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void replaceGui(GuiOpenEvent event) {
            Screen oldGui = event.getGui();

            if(oldGui instanceof StatsScreen
                    && ! (oldGui instanceof ATFCStatsScreen)) {
                event.setGui(new ATFCStatsScreen(
                        ((StatsScreen)oldGui).parentScreen,
                        Minecraft.getInstance().player.getStats()));
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
    static final class ModEvents {

        @SubscribeEvent(priority = EventPriority.HIGHEST)
        public static void registerAtfcStatsForge(
                RegistryEvent.Register<StatType<?>> event) {
            aTFCStats.aTFC_CUSTOM.setRegistryName(
                    new ResourceLocation(Main.MOD_ID, "stats"));
            ForgeRegistries.STAT_TYPES.register(aTFCStats.aTFC_CUSTOM);
        }
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add("menu.atfc.stats.atfc_tab", "aTFC");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add("menu.atfc.stats.atfc_tab", "aTFC");
    }
}
