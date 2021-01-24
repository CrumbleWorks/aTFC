package org.crumbleworks.forge.aTFC.content.itemgroups;

import java.util.function.Supplier;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;


/**
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ModItemGroup extends ItemGroup {

    private final Supplier<Item> iconSupplier;

    public ModItemGroup(final String identifier,
            final Supplier<Item> iconSupplier) {
        super(Main.MOD_ID + "." + identifier);
        this.iconSupplier = iconSupplier;
    }

    @Override
    public ItemStack createIcon() {
        return new ItemStack(iconSupplier.get());
    }
}
