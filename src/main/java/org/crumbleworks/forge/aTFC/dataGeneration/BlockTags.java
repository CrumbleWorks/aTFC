package org.crumbleworks.forge.aTFC.dataGeneration;

import java.nio.file.Path;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
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
public class BlockTags extends TagsProvider<Block> {

    public BlockTags(DataGenerator generatorIn, Registry<Block> registryIn, ExistingFileHelper existingFileHelper) {
        super(generatorIn, registryIn, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        for(Wireable wireable : Main.wireables) {
            wireable.registerForBlockTags(this);
        }
    }
    
    public TagsProvider.Builder<Block> blockTagBuilder(ITag.INamedTag<Block> tag) {
        return tag(tag);
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    protected Path getPath(ResourceLocation id) {
       return this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/tags/blocks/" + id.getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
       return Main.MOD_ID + " Block Tags";
    }
}
