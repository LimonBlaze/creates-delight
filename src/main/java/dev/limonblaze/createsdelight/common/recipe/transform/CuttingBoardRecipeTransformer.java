package dev.limonblaze.createsdelight.common.recipe.transform;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.world.item.crafting.RecipeType;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CuttingBoardRecipeTransformer implements RecipeTransformer<CuttingBoardRecipe, ManualApplicationRecipe> {
    
    @Override
    public RecipeType<CuttingBoardRecipe> originalType() {
        return ModRecipeTypes.CUTTING.get();
    }
    
    @Override
    public RecipeType<ManualApplicationRecipe> resultType() {
        return AllRecipeTypes.ITEM_APPLICATION.getType();
    }
    
    @Nullable
    @Override
    public ManualApplicationRecipe transform(CuttingBoardRecipe recipe) {
        var builder = new ProcessingRecipeBuilder<>(
            ManualApplicationRecipe::new,
            RecipeTransformer.typePrefix("item_application", recipe.getId())
        )
            .require(recipe.getIngredients().get(0))
            .require(recipe.getTool())
            .toolNotConsumed();
        for(ChanceResult cr : recipe.getRollableResults()) {
            builder.output(cr.getChance(), cr.getStack());
        }
        return builder.build();
    }
    
}
