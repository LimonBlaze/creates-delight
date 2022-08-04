package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.AllFluids;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightBlocks;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightFluids;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightItems;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.ForgeMod;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nonnull;

@SuppressWarnings("unused")
public class MixingRecipeGen extends ProcessingRecipeGen {
    
    GeneratedRecipe
    TOMATO_SAUCE = create("tomato_sauce",
        builder -> builder
            .require(ModItems.TOMATO.get())
            .require(ModItems.TOMATO.get())
            .output(CreatesDelightFluids.TOMATO_SAUCE.get(), 250)),
    YOGURT = create("yogurt",
        builder -> builder
            .require(ForgeMod.MILK.get(), 1000)
            .require(Items.SUGAR)
            .require(Items.NETHER_WART)
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.YOGURT.get(), 1000)),
    CREAM_AND_CHEESE = create("cream_and_cheese",
        builder -> builder
            .require(ForgeMod.MILK.get(), 250)
            .require(CreatesDelightItems.SALT.get())
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.CREAM.get(), 125)
            .output(CreatesDelightFluids.CHEESE.get(), 125)),
    SOUR_CREAM = create("sour_cream",
        builder -> builder
            .require(TagHelper.Fluids.CREAM, 1000)
            .require(Items.SUGAR)
            .require(Items.NETHER_WART)
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.SOUR_CREAM.get(), 1000)),
    SOUR_CREAM_AND_CHEESE = create("sour_cream_and_cheese",
        builder -> builder
            .require(TagHelper.Fluids.YOGURT, 250)
            .require(CreatesDelightItems.SALT.get())
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.SOUR_CREAM.get(), 125)
            .output(CreatesDelightFluids.CHEESE.get(), 125)),
    EGG = create("egg",
        builder -> builder
            .require(TagHelper.Items.EGG)
            .output(CreatesDelightFluids.EGG.get(), 125)
            .output(0.25F, Items.BONE_MEAL)),
    MELT_CHEESE = create("melt_cheese",
        builder -> builder
            .require(TagHelper.Items.CHEESE)
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.CHEESE.get(), 250)),
    MELT_CHEESE_WHEEL = create("melt_cheese_wheel",
        builder -> builder
            .require(CreatesDelightBlocks.CHEESE_WHEEL.get())
            .requiresHeat(HeatCondition.HEATED)
            .output(CreatesDelightFluids.CHEESE.get(), 1000));
    
    public MixingRecipeGen(DataGenerator dataGenerator) {
        super(dataGenerator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Create's Delight: Mixing Recipes";
    }
    
    @Override
    protected IRecipeTypeInfo getRecipeType() {
        return AllRecipeTypes.MIXING;
    }
    
}
