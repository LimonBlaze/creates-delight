package dev.limonblaze.createsdelight.compat.jei;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.recipe.CuttingBoardDeployingRecipe;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightRecipeTypes;
import dev.limonblaze.createsdelight.compat.jei.category.CuttingBoardDeployingCategory;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.runtime.IIngredientManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CreatesDelightJEI implements IModPlugin {
    
    private static final ResourceLocation ID = CreatesDelight.asResource("jei_plugin");
    
    protected final List<CreateRecipeCategory<?>> allCategories = new ArrayList<>();
    protected IIngredientManager ingredientManager;
    
    private void loadCategories() {
        allCategories.clear();
        allCategories.add(new CreateRecipeCategoryBuilder<>(CuttingBoardDeployingRecipe.class)
            .addTypedRecipes(CreatesDelightRecipeTypes.CUTTING_BOARD_DEPLOYING)
            .catalyst(AllBlocks.DEPLOYER::get)
            .catalyst(AllBlocks.DEPOT::get)
            .catalyst(AllItems.BELT_CONNECTOR::get)
            .doubleItemIcon(AllBlocks.DEPLOYER.get(), ModBlocks.CUTTING_BOARD.get())
            .emptyBackground(177, 70)
            .build("cutting_board_deploying", CuttingBoardDeployingCategory::new)
        );
    }
    
    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }
    
    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        loadCategories();
        registration.addRecipeCategories(allCategories.toArray(IRecipeCategory[]::new));
    }
    
    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        ingredientManager = registration.getIngredientManager();
        allCategories.forEach(c -> c.registerRecipes(registration));
    }
    
    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        allCategories.forEach(c -> c.registerCatalysts(registration));
    }
    
}
