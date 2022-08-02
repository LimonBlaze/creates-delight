package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightItems;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class FillingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    TOMATO_SAUCE = create(ModHelper.FD.asResource("tomato_sauce"),
        builder -> builder
            .require(TagHelper.Fluids.TOMATO_SAUCE, 250)
            .require(Items.BOWL)
            .output(ModItems.TOMATO_SAUCE.get())
    ),
    YOGURT_BOTTLE = create("yogurt_bottle",
        builder -> builder
            .require(TagHelper.Fluids.YOGURT, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CreatesDelightItems.YOGURT_BOTTLE.get())
    ),
    CREAM_BOTTLE = create("cream_bottle",
        builder -> builder
            .require(TagHelper.Fluids.CREAM, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CreatesDelightItems.CREAM_BOTTLE.get())
    ),
    SOUR_CREAM_BOTTLE = create("sour_cream_bottle",
        builder -> builder
            .require(TagHelper.Fluids.SOUR_CREAM, 250)
            .require(Items.GLASS_BOTTLE)
            .output(CreatesDelightItems.SOUR_CREAM_BOTTLE.get())
    );
    
    public FillingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Create's Delight: Filling Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.FILLING;
    }
    
}
