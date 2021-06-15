package org.crumbleworks.forge.aTFC.worldgen;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.world.WorldEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
@Mod.EventBusSubscriber(modid = Main.MOD_ID, bus = Bus.FORGE)
public class WorldSetupEventsForge {

    @SubscribeEvent
    public static void registerRenderHandlers(WorldEvent.Load event) {
        if(! (event.getWorld() instanceof ClientWorld)) {
            return;
        }

        ((ClientWorld)event.getWorld()).effects
                .setCloudRenderHandler(new ContinentalCloudRenderHandler(
                        ContinentalWorldType.CLOUD_LEVEL));
        ((ClientWorld)event.getWorld()).effects
                .setSkyRenderHandler(new ContinentalSkyRenderHandler());
    }

    @SubscribeEvent
    public static void replaceDimensionTypeImpl(WorldEvent.Load event) {
        DimensionType old = ((World)event.getWorld()).dimensionType;

        ((World)event.getWorld()).dimensionType = new atfcDimenstionType(
                old.fixedTime, old.hasSkyLight(),
                old.hasCeiling(), old.ultraWarm(), old.natural(),
                old.coordinateScale(), old.createDragonFight(),
                old.piglinSafe(), old.bedWorks(),
                old.respawnAnchorWorks(),
                old.hasRaids(), old.logicalHeight(), old.getBiomeZoomer(),
                old.infiniburn, old.effectsLocation(),
                old.ambientLight,
                event.getWorld());
    }
}
