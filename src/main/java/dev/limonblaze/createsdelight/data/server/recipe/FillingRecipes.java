package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllItems;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.material.Fluids;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nonnull;

public class FillingRecipes extends ProcessingRecipes {
    
    GeneratedRecipe
    TOMATO_SAUCE = create(ModHelper.FD.asResource("tomato_sauce"),
        builder -> builder
            .require(CDTags.FluidTag.TOMATO_SAUCE, 250)
            .require(Items.BOWL)
            .output(ModItems.TOMATO_SAUCE.get())
    ),
    YOGURT_BOTTLE = create("yogurt_bottle",
        builder -> builder
            .require(CDTags.FluidTag.YOGURT, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CDItems.YOGURT_BOTTLE.get())
    ),
    CREAM_BOTTLE = create("cream_bottle",
        builder -> builder
            .require(CDTags.FluidTag.CREAM, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CDItems.CREAM_BOTTLE.get())
    ),
    SOUR_CREAM_BOTTLE = create("sour_cream_bottle",
        builder -> builder
            .require(CDTags.FluidTag.SOUR_CREAM, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CDItems.SOUR_CREAM_BOTTLE.get())
    ),
    SALT = create("salt",
        builder -> builder
            .require(Fluids.WATER, 25)
            .require(AllItems.CINDER_FLOUR.get())
            .output(CDItems.SALT.get())
    );
    
    public FillingRecipes(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Filling Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }
    
}
