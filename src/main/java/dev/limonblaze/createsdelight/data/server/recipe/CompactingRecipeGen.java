package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.data.DataGenerator;

import javax.annotation.Nonnull;


public class CompactingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    CHEESE_WHEEL = create("cheese_wheel",
        builder -> builder
            .require(CDTags.FluidTag.CHEESE, 1000)
            .output(CDBlocks.CHEESE_WHEEL.asStack())
    ),
    OMELETTE = create("omelette",
        builder -> builder
            .require(CDTags.FluidTag.EGG, 125)
            .require(CDTags.FluidTag.CREAM, 125)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDItems.OMELETTE.asStack())
    );
    
    public CompactingRecipeGen(DataGenerator generator) {
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
