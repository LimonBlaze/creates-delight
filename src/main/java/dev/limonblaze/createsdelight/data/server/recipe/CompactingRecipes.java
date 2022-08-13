package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nonnull;


public class CompactingRecipes extends ProcessingRecipes {
    
    GeneratedRecipe
    BUTTER = create("butter",
        builder -> builder
            .require(CDTags.FluidTag.BUTTER, 250)
            .output(CDItems.BUTTER.asStack())
    ),
    CHEESE_WHEEL = create("cheese_wheel",
        builder -> builder
            .require(CDTags.FluidTag.CHEESE, 1000)
            .output(CDBlocks.CHEESE_WHEEL.asStack())
    ),
    HONEY_COOKIE = create("honey_cookie",
        builder -> builder
            .require(AllTags.AllFluidTags.HONEY.tag, 250)
            .require(CommonIngredients.WHEAT_FLOUR)
            .require(CommonIngredients.WHEAT_FLOUR)
            .requiresHeat(HeatCondition.HEATED)
            .output(ModItems.HONEY_COOKIE.get(), 8)
    ),
    SWEET_BERRY_COOKIE = create("sweet_berry_cookie",
        builder -> builder
            .require(Items.SWEET_BERRIES)
            .require(CommonIngredients.WHEAT_FLOUR)
            .require(CommonIngredients.WHEAT_FLOUR)
            .requiresHeat(HeatCondition.HEATED)
            .output(ModItems.SWEET_BERRY_COOKIE.get(), 8)
    ),
    OMELETTE = create("omelette",
        builder -> builder
            .require(CDTags.FluidTag.EGG, 125)
            .require(CDTags.FluidTag.CREAM, 125)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDItems.OMELETTE.asStack())
    );
    
    public CompactingRecipes(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Compacting Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
    
}
