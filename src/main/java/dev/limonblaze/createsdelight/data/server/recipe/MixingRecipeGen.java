package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.common.registry.CDFluids;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nonnull;

public class MixingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    TOMATO_SAUCE = create("tomato_sauce",
        builder -> builder
            .require(ModItems.TOMATO.get())
            .require(ModItems.TOMATO.get())
            .output(CDFluids.TOMATO_SAUCE.get(), 250)),
    YOGURT = create("yogurt",
        builder -> builder
            .require(ForgeMod.MILK.get(), 1000)
            .require(Items.SUGAR)
            .require(Items.NETHER_WART)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.YOGURT.get(), 1000)),
    CREAM_AND_CHEESE = create("cream_and_cheese",
        builder -> builder
            .require(ForgeMod.MILK.get(), 250)
            .require(CommonIngredients.SALT)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.CREAM.get(), 125)
            .output(CDFluids.CHEESE.get(), 125)),
    SOUR_CREAM = create("sour_cream",
        builder -> builder
            .require(CDTags.FluidTag.CREAM, 1000)
            .require(Items.SUGAR)
            .require(Items.NETHER_WART)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.SOUR_CREAM.get(), 1000)),
    SOUR_CREAM_AND_CHEESE = create("sour_cream_and_cheese",
        builder -> builder
            .require(CDTags.FluidTag.YOGURT, 250)
            .require(CommonIngredients.SALT)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.SOUR_CREAM.get(), 125)
            .output(CDFluids.CHEESE.get(), 125)),
    MELT_CHEESE = create("melt_cheese",
        builder -> builder
            .require(CDTags.ItemTag.CHEESE)
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.CHEESE.get(), 250)),
    MELT_CHEESE_WHEEL = create("melt_cheese_wheel",
        builder -> builder
            .require(CDBlocks.CHEESE_WHEEL.get())
            .requiresHeat(HeatCondition.HEATED)
            .output(CDFluids.CHEESE.get(), 1000)),
    EGG = create("egg",
        builder -> builder
            .require(CDTags.ItemTag.EGGS)
            .output(CDFluids.EGG.get(), 125)
            .output(0.25F, Items.BONE_MEAL)),
    BLAZERONI = create("blazeroni",
        builder -> builder
            .require(ForgeTags.RAW_BEEF)
            .require(ForgeTags.RAW_PORK)
            .require(CommonIngredients.SALT)
            .require(Items.BLAZE_POWDER)
            .output(CDItems.BLAZERONI.asStack(2)));
    
    public MixingRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Mixing Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
    
}
