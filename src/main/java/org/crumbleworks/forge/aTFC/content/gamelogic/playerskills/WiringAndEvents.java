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

    public static final KeyBinding SKILLS_KEY = new KeyBinding(
            "key.atfc.skillsKey", GLFW.GLFW_KEY_K,
            "key.atfc.categories.atfc_bindings");
    static Stat<ResourceLocation> OPENED_SKILLS = null;

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void handleKeyBindings(ClientTickEvent event) {
            if(SKILLS_KEY.isPressed()
                    && Minecraft.getInstance().currentScreen == null) {
                Minecraft.getInstance().displayGuiScreen(new SkillsScreen());
            }
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
    static final class ModEvents {

        @SubscribeEvent(priority = EventPriority.LOWEST)
        public static void registerAtfcStatsForge(
                RegistryEvent.Register<StatType<?>> event) {
            OPENED_SKILLS = aTFCStats.registerAtfcStat("opened_skills",
                    IStatFormatter.DEFAULT);
        }

        @SubscribeEvent
        public static void registerMessage(FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                Networking.registerMessageType(SkillsCheckMessage.class);
            });
        }

        @SubscribeEvent
        public static void registerKeyBindings(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ClientRegistry.registerKeyBinding(SKILLS_KEY);
            });
        }
    }

    @Override
    public void englishTranslations(Translations tr) {
        tr.add("key.atfc.skillsKey", "Show Skills");
        tr.add("stat.atfc.opened_skills", "Looked at skills screen");

        tr.add("gui.atfc.skills.title", "Skills");
        tr.add("gui.atfc.skills.gen_smithing", "General Smithing");
        tr.add("gui.atfc.skills.tol_smithing", "Tool Smithing");
        tr.add("gui.atfc.skills.arm_smithing", "Armor Smithing");
        tr.add("gui.atfc.skills.wpn_smithing", "Weapon Smithing");
        tr.add("gui.atfc.skills.agriculture", "Agriculture");
        tr.add("gui.atfc.skills.cooking", "Cooking");
        tr.add("gui.atfc.skills.prospecting", "Prospecting");
        tr.add("gui.atfc.skills.butchering", "Butchering");
        
        tr.add("gui.atfc.skills.novice_level", "Novice");
        tr.add("gui.atfc.skills.adept_level", "Adept");
        tr.add("gui.atfc.skills.expert_level", "Expert");
        tr.add("gui.atfc.skills.master_level", "Master");
    }

    @Override
    public void swissTranslations(Translations tr) {
        tr.add("key.atfc.skillsKey", "Zeig d Skills");
        tr.add("stat.atfc.opened_skills", "D Skills liebgäuglet");

        tr.add("gui.atfc.skills.title", "Fähigkeitä");
        tr.add("gui.atfc.skills.gen_smithing", "Allgemeins Schmiidä");
        tr.add("gui.atfc.skills.tol_smithing", "Wärchzüüg Schmiidä");
        tr.add("gui.atfc.skills.arm_smithing", "Rüschtigä Schmiidä");
        tr.add("gui.atfc.skills.wpn_smithing", "Waffä Schmiidä");
        tr.add("gui.atfc.skills.agriculture", "Landwirtschaft");
        tr.add("gui.atfc.skills.cooking", "Chochä");
        tr.add("gui.atfc.skills.prospecting", "Prospektierä");
        tr.add("gui.atfc.skills.butchering", "Metzgä");
        
        tr.add("gui.atfc.skills.novice_level", "Noviz");
        tr.add("gui.atfc.skills.adept_level", "Adept");
        tr.add("gui.atfc.skills.expert_level", "Expärt");
        tr.add("gui.atfc.skills.master_level", "Meischter");
    }
}
