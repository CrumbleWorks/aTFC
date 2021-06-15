package org.crumbleworks.forge.aTFC.worldgen;

import java.util.function.LongFunction;

import net.minecraft.world.gen.IExtendedNoiseRandom;
import net.minecraft.world.gen.LazyAreaLayerContext;
import net.minecraft.world.gen.area.IArea;
import net.minecraft.world.gen.area.IAreaFactory;
import net.minecraft.world.gen.layer.IslandLayer;
import net.minecraft.world.gen.layer.Layer;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ContinentalLayerUtil {

    public static <T extends IArea, C extends IExtendedNoiseRandom<T>> IAreaFactory<T> createAreaFactories(LongFunction<C> contextFactory) {
        return IslandLayer.INSTANCE.run(contextFactory.apply(1L)); //FIXME this is sorta abbreviated minecraft stuff
    }
    
    public static Layer createGenLayers(long seed) {
        return new Layer(createAreaFactories((l) -> new LazyAreaLayerContext(1, seed, l)));
    }
}
