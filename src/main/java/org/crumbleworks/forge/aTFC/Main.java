package org.crumbleworks.forge.aTFC;

import java.util.Set;

import org.crumbleworks.forge.aTFC.wiring.Wireable;
import org.crumbleworks.forge.aTFC.wiring.Wiring;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraftforge.fml.common.Mod;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Main.MOD_ID)
public class Main {

    public static final String MOD_ID = "atfc";

    public static final Reflections reflections = new Reflections();
    public static Set<Wireable> wireables;

    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    public Main() {
        LOGGER.info("Wiring up '{}'", MOD_ID);
        wireables = Wiring.wireUp();
    }
}
