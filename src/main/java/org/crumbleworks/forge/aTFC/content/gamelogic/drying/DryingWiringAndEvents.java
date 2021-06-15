package org.crumbleworks.forge.aTFC.content.gamelogic.drying;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class DryingWiringAndEvents implements Wireable {

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
    static final class ForgeEvents {

        @SubscribeEvent
        public static void registerCapabilities(
                AttachCapabilitiesEvent<ItemStack> event) {
            if(! (event.getObject().getItem() instanceof Dryable)) {
                return;
            }

            event.addCapability(
                    new ResourceLocation(Main.MOD_ID, "drying"),
                    new DryingCapabilityProvider());
        }
    }

    @Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.MOD)
    static final class ModEvents {

        @SubscribeEvent
        public static void registerCapabilityProvider(
                FMLCommonSetupEvent event) {
            CapabilityManager.INSTANCE.register(Drying.class,
                    new DryingImpl.DryingImplStorage(),
                    DryingImpl::new);
        }
    }
}
