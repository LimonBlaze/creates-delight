package dev.limonblaze.createsdelight.common.recipe.transform;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CookingPotRecipeTransformer implements RecipeTransformer<CookingPotRecipe, MixingRecipe> {
    
    @Override
    public RecipeType<CookingPotRecipe> originalType() {
        return ModRecipeTypes.COOKING.get();
    }
    
    @Override
    public RecipeType<MixingRecipe> resultType() {
        return AllRecipeTypes.MIXING.getType();
    }
    
    @Nullable
    @Override
    public MixingRecipe transform(CookingPotRecipe recipe) {
        ItemStack container = recipe.getOutputContainer();
        var builder = new ProcessingRecipeBuilder<>(
            MixingRecipe::new,
            RecipeTransformer.typePrefix("mixing", recipe.getId())
        );
        for(var ingredient : recipe.getIngredients()) {
            builder.require(ingredient);
        }
        if(!container.isEmpty()) builder.require(container.getItem());
        return builder
            .requiresHeat(HeatCondition.HEATED)
            .output(recipe.getResultItem())
            .build();
    }
    
}
