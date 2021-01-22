package org.crumbleworks.forge.aTFC.content;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.Tags.IOptionalNamedTag;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Tags {
    
    public static class Blocks {
        public static final IOptionalNamedTag<Block> GRASS_COVERABLES = tag("grass_coverables");
        
        private static IOptionalNamedTag<Block> tag(String name)
        {
            return BlockTags.createOptional(new ResourceLocation(Main.MOD_ID, name));
        }
    }
    
    public static class Items {
        public static final IOptionalNamedTag<Item> SHOVELS = tag("tools/shovels");
        
        private static IOptionalNamedTag<Item> tag(String name)
        {
            return ItemTags.createOptional(new ResourceLocation(Main.MOD_ID, name));
        }
    }
}
