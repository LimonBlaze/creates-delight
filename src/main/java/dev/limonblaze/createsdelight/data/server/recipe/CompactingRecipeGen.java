package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightBlocks;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightItems;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.data.DataGenerator;

@SuppressWarnings("unused")
public class CompactingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    CHEESE_WHEEL = create("cheese_wheel",
        builder -> builder
            .require(TagHelper.Fluids.CHEESE, 1000)
            .output(CreatesDelightBlocks.CHEESE_WHEEL.asStack())
    ),
    OMELETTE = create("omelette",
        builder -> builder
            .require(TagHelper.Fluids.EGG, 250)
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightItems.OMELETTE.asStack())
    );
    
    public CompactingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.COMPACTING;
    }
    
}
