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

        ((ClientWorld)event.getWorld()).field_239131_x_
                .setCloudRenderHandler(new ContinentalCloudRenderHandler(
                        ContinentalWorldType.CLOUD_LEVEL));
        ((ClientWorld)event.getWorld()).field_239131_x_
                .setSkyRenderHandler(new ContinentalSkyRenderHandler());
    }

    @SubscribeEvent
    public static void replaceDimensionTypeImpl(WorldEvent.Load event) {
        DimensionType old = ((World)event.getWorld()).dimensionType;

        ((World)event.getWorld()).dimensionType = new atfcDimenstionType(
                old.fixedTime, old.hasSkyLight(),
                old.getHasCeiling(), old.isUltrawarm(), old.isNatural(),
                old.getCoordinateScale(), old.doesHasDragonFight(),
                old.isPiglinSafe(), old.doesBedWork(),
                old.doesRespawnAnchorWorks(),
                old.isHasRaids(), old.getLogicalHeight(), old.getMagnifier(),
                old.infiniburn, old.getEffects(),
                old.ambientLight,
                event.getWorld());
    }
}
