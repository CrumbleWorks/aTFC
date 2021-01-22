package org.crumbleworks.forge.aTFC.dataGeneration;

import java.nio.file.Path;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ITag;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.common.data.ExistingFileHelper;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class ItemTags extends TagsProvider<Item> {

    public ItemTags(DataGenerator generatorIn, Registry<Item> registryIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, registryIn, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        for(Wireable wireable : Main.wireables) {
            wireable.registerForItemTags(this);
        }
    }
    
    public TagsProvider.Builder<Item> itemTagBuilder(ITag.INamedTag<Item> tag) {
        return getOrCreateBuilder(tag);
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    protected Path makePath(ResourceLocation id) {
       return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/items/" + id.getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
       return Main.MOD_ID + " Item Tags";
    }
}
