package org.crumbleworks.forge.aTFC.content;

import org.crumbleworks.forge.aTFC.Main;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.EntityTypeTags;
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

        public static final IOptionalNamedTag<Block> GRASS_COVERABLES = tag(
                "grass_coverables");

        private static IOptionalNamedTag<Block> tag(String name) {
            return BlockTags
                    .createOptional(new ResourceLocation(Main.MOD_ID, name));
        }
    }

    public static class Items {

        public static final IOptionalNamedTag<Item> CLAY = tag("clay");
        public static final IOptionalNamedTag<Item> CLAY_BALL = tag("clay_ball");
        public static final IOptionalNamedTag<Item> FLINT = tag("flint");
        public static final IOptionalNamedTag<Item> GRAVEL = tag("gravel");
        public static final IOptionalNamedTag<Item> SAND = tag("sand");
        public static final IOptionalNamedTag<Item> SOIL = tag("soil");
        
        public static final IOptionalNamedTag<Item> SHOVELS = tag(
                "tools/shovels");
        public static final IOptionalNamedTag<Item> HATCHETS = tag(
                "tools/hatchets");

        private static IOptionalNamedTag<Item> tag(String name) {
            return ItemTags
                    .createOptional(new ResourceLocation(Main.MOD_ID, name));
        }
    }

    public static class EntityTypes {

        public static final IOptionalNamedTag<EntityType<?>> POULTRY = tag(
                "poultry");

        private static IOptionalNamedTag<EntityType<?>> tag(String name) {
            return EntityTypeTags
                    .createOptional(new ResourceLocation(Main.MOD_ID, name));
        }
    }
}
