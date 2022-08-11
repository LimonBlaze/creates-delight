package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;

import javax.annotation.Nonnull;

public class ManualApplicationRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    BLAZE_STOVE = create("blaze_stove",
        builder -> builder
            .require(AllBlocks.BLAZE_BURNER.get())
            .require(Items.IRON_TRAPDOOR)
            .output(CDBlocks.BLAZE_STOVE.get())
    );
    
    public ManualApplicationRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Manual Application Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.ITEM_APPLICATION;
    }
    
}
