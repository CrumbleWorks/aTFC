package org.crumbleworks.forge.aTFC.wiring;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.blocks.Soil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;

/**
 * Extending classes should each be built around 1 single subject, e.g.
 * {@link Soil} registers blocks, items, etc. in regards to dirt.
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public abstract class Wiring {

    private static final Logger LOGGER = LoggerFactory
            .getLogger(Wiring.class);

    private static final Set<DeferredRegister<?>> registries = new HashSet<>();
    static {
        registries.add(Wireable.BLOCKS);
        registries.add(Wireable.ITEMS);
    }

    public static final Set<Wireable> wireUp() {
        Set<Class<? extends Wireable>> subTypes = Main.reflections
                .getSubTypesOf(Wireable.class);

        // TODO https://github.com/CrumbleWorks/aTFC/issues/2

        // we need to trigger classloading for our subclasses (that define
        // only static stuff...)
        Set<Wireable> wireables = new HashSet<>();
        for(Class<? extends Wireable> subType : subTypes) {
            if(subType.isInterface() || Modifier.isAbstract(subType.getModifiers())) {
                continue;
            }
            
            try {
                wireables.add(subType.getConstructor().newInstance());
            } catch(InstantiationException | IllegalAccessException
                    | IllegalArgumentException | InvocationTargetException
                    | NoSuchMethodException | SecurityException e) {
                LOGGER.error("Could not initialize '{}'", subType, e);
            }
        }

        for(DeferredRegister<?> r : registries) {
            r.register(FMLJavaModLoadingContext.get().getModEventBus());
        }

        return wireables;
    }
}
