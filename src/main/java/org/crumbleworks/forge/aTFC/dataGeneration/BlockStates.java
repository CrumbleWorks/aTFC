package org.crumbleworks.forge.aTFC.dataGeneration;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.VariantBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;


/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class BlockStates extends BlockStateProvider {

    /**
     * @param gen
     * @param modid
     * @param exFileHelper
     */
    public BlockStates(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, Main.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for(Wireable wireable : Main.wireables) {
            wireable.generateBlockStates(this);
        }
    }

    public VariantBlockStateBuilder simpleState(String name, Block block) {
        return getVariantBuilder(block)
                .partialState().modelForState()
                .modelFile(models().getExistingFile(blockModel(name)))
                .addModel();
    }

    public ResourceLocation blockModel(String name) {
        return modLoc("block/" + name);
    }
}
