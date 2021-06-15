package org.crumbleworks.forge.aTFC.content.gamelogic.playercharacter;

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
public class CharacterWiringAndEvents implements Wireable {

    public static final KeyBinding CHARACTER_KEY = new KeyBinding(
            "key.atfc.characterKey", GLFW.GLFW_KEY_C,
            "key.atfc.categories.atfc_bindings");
    static Stat<ResourceLocation> OPENED_CHARACTER = null;

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void handleKeyBindings(ClientTickEvent event) {
            if(CHARACTER_KEY.consumeClick()
                    && Minecraft.getInstance().screen == null) {
                Minecraft.getInstance().setScreen(new CharacterScreen());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
    static final class ModEvents {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerAtfcStatsForge(
                RegistryEvent.Register<StatType<?>> event) {
            OPENED_CHARACTER = aTFCStats.registerAtfcStat("opened_character",
                    IStatFormatter.DEFAULT);
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                Networking.registerMessageType(CharacterCheckMessage.class);
            });
        }

        @SubscribeEvent
        public static void registerKeyBindings(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ClientRegistry.registerKeyBinding(CHARACTER_KEY);
            });
        }
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add("key.atfc.characterKey", "Show Personal Infos");
        tr.add("stat.atfc.opened_character", "Looked at character screen");

        tr.add("gui.atfc.character.title", "Character");
        
        tr.add("gui.atfc.character.taste_prefs.title", "Taste Preferences");
        tr.add("gui.atfc.character.taste_prefs.sweet", "Sweet");
        tr.add("gui.atfc.character.taste_prefs.sour", "Sour");
        tr.add("gui.atfc.character.taste_prefs.salty", "Salty");
        tr.add("gui.atfc.character.taste_prefs.bitter", "Bitter");
        tr.add("gui.atfc.character.taste_prefs.umami", "Umami");
        
        tr.add("gui.atfc.character.flavour_prefs.title", "Flavour Preferences");
        tr.add("gui.atfc.character.flavour_prefs.smoked", "Smoky");
        tr.add("gui.atfc.character.flavour_prefs.charred", "Charred");
        tr.add("gui.atfc.character.flavour_prefs.spicy", "Spicy");
        tr.add("gui.atfc.character.flavour_prefs.slimy", "Slimy");
        tr.add("gui.atfc.character.flavour_prefs.dry", "Dry");
        tr.add("gui.atfc.character.flavour_prefs.chewy", "Chewy");
        tr.add("gui.atfc.character.flavour_prefs.tender", "Tender");
        tr.add("gui.atfc.character.flavour_prefs.hot", "Hot");
        tr.add("gui.atfc.character.flavour_prefs.cold", "Cold");
        tr.add("gui.atfc.character.flavour_prefs.fatty", "Fatty");
        tr.add("gui.atfc.character.flavour_prefs.lean", "Lean");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add("key.atfc.characterKey", "Zeig min Charakter");
        tr.add("stat.atfc.opened_character", "Min Charakter agluegt");

        tr.add("gui.atfc.character.title", "Charakter");
        
        tr.add("gui.atfc.character.taste_prefs.title", "Gschmacksvorzüg");
        tr.add("gui.atfc.character.taste_prefs.sweet", "Süäss");
        tr.add("gui.atfc.character.taste_prefs.sour", "Suur");
        tr.add("gui.atfc.character.taste_prefs.salty", "Salzig");
        tr.add("gui.atfc.character.taste_prefs.bitter", "Bitter");
        tr.add("gui.atfc.character.taste_prefs.umami", "U(h)mami");
        
        tr.add("gui.atfc.character.flavour_prefs.title", "Aromavorzüg");
        tr.add("gui.atfc.character.flavour_prefs.smoked", "Gräucheret");
        tr.add("gui.atfc.character.flavour_prefs.charred", "Verbrönnt");
        tr.add("gui.atfc.character.flavour_prefs.spicy", "Scharf");
        tr.add("gui.atfc.character.flavour_prefs.slimy", "Schleimig");
        tr.add("gui.atfc.character.flavour_prefs.dry", "Trochä");
        tr.add("gui.atfc.character.flavour_prefs.chewy", "Chätschig");
        tr.add("gui.atfc.character.flavour_prefs.tender", "Zart");
        tr.add("gui.atfc.character.flavour_prefs.hot", "Heiss");
        tr.add("gui.atfc.character.flavour_prefs.cold", "Chalt");
        tr.add("gui.atfc.character.flavour_prefs.fatty", "Fettig");
        tr.add("gui.atfc.character.flavour_prefs.lean", "Mager");
    }
}
