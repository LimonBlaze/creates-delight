package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightFluids;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.data.DataGenerator;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class EmptyingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    TOMATO_SAUCE_FROM_BOWL = create("tomato_sauce",
        builder -> builder
            .require(ModItems.TOMATO_SAUCE.get())
            .output(CreatesDelightFluids.TOMATO_SAUCE.get(), 250)
    ),
    YOGURT_BOTTLE = create("yogurt_bottle",
        builder -> builder
            .require(TagHelper.Items.BOTTLES$YOGURT)
            .output(CreatesDelightFluids.YOGURT.get(), 250)
    ),
    CREAM_BOTTLE = create("cream_bottle",
        builder -> builder
            .require(TagHelper.Items.BOTTLES$CREAM)
            .output(CreatesDelightFluids.CREAM.get(), 250)
    ),
    SOUR_CREAM_BOTTLE = create("sour_cream_bottle",
        builder -> builder
            .require(TagHelper.Items.BOTTLES$SOUR_CREAM)
            .output(CreatesDelightFluids.SOUR_CREAM.get(), 250)
    );
    
    public EmptyingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Create's Delight: Emptying Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.EMPTYING;
    }
    
}
