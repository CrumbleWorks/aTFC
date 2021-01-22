package org.crumbleworks.forge.aTFC.dataGeneration;

import java.nio.file.Path;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.TagsProvider;
import net.minecraft.entity.EntityType;
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
public class EntityTypeTags extends TagsProvider<EntityType<?>> {

    public EntityTypeTags(DataGenerator generatorIn,
            Registry<EntityType<?>> registryIn,
            ExistingFileHelper existingFileHelper) {
        super(generatorIn, registryIn, Main.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerTags() {
        for(Wireable wireable : Main.wireables) {
            wireable.registerForEntityTypeTags(this);
        }
    }

    public TagsProvider.Builder<EntityType<?>> itemTagBuilder(
            ITag.INamedTag<EntityType<?>> tag) {
        return getOrCreateBuilder(tag);
    }

    /**
     * Resolves a Path for the location to save the given tag.
     */
    protected Path makePath(ResourceLocation id) {
        return this.generator.getOutputFolder()
                .resolve("data/" + id.getNamespace() + "/tags/entity_types/"
                        + id.getPath() + ".json");
    }

    /**
     * Gets a name for this provider, to use in logging.
     */
    public String getName() {
        return "aTFC Entity Type Tags";
    }
}
