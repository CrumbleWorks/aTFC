package org.crumbleworks.forge.aTFC.dataGeneration;

import java.util.function.Consumer;

import org.crumbleworks.forge.aTFC.Main;
import org.crumbleworks.forge.aTFC.wiring.Wireable;

import net.minecraft.advancements.criterion.EnterBlockTrigger;
import net.minecraft.advancements.criterion.InventoryChangeTrigger;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.block.Block;
import net.minecraft.data.CookingRecipeBuilder;
import net.minecraft.data.CustomRecipeBuilder;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.IFinishedRecipe;
import net.minecraft.data.RecipeProvider;
import net.minecraft.data.ShapedRecipeBuilder;
import net.minecraft.data.ShapelessRecipeBuilder;
import net.minecraft.data.SingleItemRecipeBuilder;
import net.minecraft.data.SmithingRecipeBuilder;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.CookingRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.tags.ITag;
import net.minecraft.util.IItemProvider;

/**
 * TODO
 *
 * @author Michael Stocker
 * @since CURRENT_VERSION
 */
public class Recipes extends RecipeProvider {

    public Recipes(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerRecipes(Consumer<IFinishedRecipe> consumer) {
        for(Wireable wireable : Main.wireables) {
            wireable.registerRecipes(this, consumer);
        }
    }

    /*
     * MINECRAFT BASE RECIPE BUILDERS
     */

    public CookingRecipeBuilder cookingRecipe(Ingredient ingredient,
            IItemProvider result, float experience, int cookingTime,
            CookingRecipeSerializer<?> serializer) {
        return CookingRecipeBuilder.cookingRecipe(ingredient, result,
                experience, cookingTime, serializer);
    }

    public CookingRecipeBuilder blastingRecipe(Ingredient ingredient,
            IItemProvider result, float experience, int cookingTime) {
        return CookingRecipeBuilder.blastingRecipe(ingredient, result,
                experience, cookingTime);
    }

    public CookingRecipeBuilder smeltingRecipe(Ingredient ingredient,
            IItemProvider result, float experience, int cookingTime) {
        return CookingRecipeBuilder.smeltingRecipe(ingredient, result,
                experience, cookingTime);
    }

    public CustomRecipeBuilder customRecipe(
            SpecialRecipeSerializer<?> serializer) {
        return CustomRecipeBuilder.customRecipe(serializer);
    }

    public ShapedRecipeBuilder shapedRecipe(IItemProvider result, int count) {
        return ShapedRecipeBuilder.shapedRecipe(result, count);
    }

    public ShapedRecipeBuilder shapedRecipe(IItemProvider result) {
        return ShapedRecipeBuilder.shapedRecipe(result);
    }

    public ShapelessRecipeBuilder shapelessRecipe(IItemProvider result,
            int count) {
        return ShapelessRecipeBuilder.shapelessRecipe(result, count);
    }

    public ShapelessRecipeBuilder shapelessRecipe(IItemProvider result) {
        return ShapelessRecipeBuilder.shapelessRecipe(result);
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Ingredient ingredient,
            IItemProvider result) {
        return SingleItemRecipeBuilder.stonecuttingRecipe(ingredient, result);
    }

    public SingleItemRecipeBuilder stonecuttingRecipe(Ingredient ingredient,
            IItemProvider result, int count) {
        return SingleItemRecipeBuilder.stonecuttingRecipe(ingredient, result,
                count);
    }

    public SmithingRecipeBuilder smithingRecipe(Ingredient base,
            Ingredient addition, Item output) {
        return SmithingRecipeBuilder.smithingRecipe(base, addition, output);
    }

    /*
     * TRIGGERS
     */

    public EnterBlockTrigger.Instance triggerWhenEnteringBlock(Block block) {
        return RecipeProvider.enteredBlock(block);
    }

    public InventoryChangeTrigger.Instance triggerWhenHasItem(
            IItemProvider item) {
        return RecipeProvider.hasItem(item);
    }

    public InventoryChangeTrigger.Instance triggerWhenHasAnyOf(
            ITag<Item> tag) {
        return RecipeProvider.hasItem(tag);
    }

    public InventoryChangeTrigger.Instance triggerWhenHasAnyIn(
            ItemPredicate... predicate) {
        return RecipeProvider.hasItem(predicate);
    }

    @Override
    public String getName() {
        return Main.MOD_ID + " " + super.getName();
    }
}
