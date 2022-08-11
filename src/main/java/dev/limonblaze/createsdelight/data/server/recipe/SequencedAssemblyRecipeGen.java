package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.content.contraptions.components.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.contraptions.fluids.actors.FillingRecipe;
import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyRecipeBuilder;
import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class SequencedAssemblyRecipeGen extends CreateRecipeProvider {
    
    public SequencedAssemblyRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    GeneratedRecipe
    APPLE_PIE = create("apple_pie",
        builder -> builder
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(CDItems.INCOMPLETE_APPLE_PIE.get())
            .addOutput(ModItems.APPLE_PIE.get(), 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.APPLE))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.SUGAR))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.WHEAT_FLOUR))
    ),
    SWEET_BERRY_CHEESE_CAKE = create("sweet_berry_cheese_cake",
        builder -> builder
            .require(ModItems.PIE_CRUST.get())
            .transitionTo(CDItems.INCOMPLETE_SWEET_BERRY_CHEESECAKE.get())
            .addOutput(ModItems.SWEET_BERRY_CHEESECAKE.get(), 1)
            .loops(2)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.SWEET_BERRIES))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.SWEET_BERRIES))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.CHEESE, 250))
    ),
    EGG_SANDWICH = create("egg_sandwich",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_EGG_SANDWICH.get())
            .addOutput(ModItems.EGG_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_EGGS))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_EGGS))
    ),
    CHICKEN_SANDWICH = create("chicken_sandwich",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_CHICKEN_SANDWICH.get())
            .addOutput(ModItems.CHICKEN_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_CHICKEN))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.CARROT))
    ),
    HOT_DOG = create("hot_dog",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_HOT_DOG.get())
            .addOutput(CDItems.HOT_DOG.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CDTags.ItemTag.PEPPERONI))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.CHEESE, 125))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.SOUR_CREAM, 125))
    ),
    HAMBURGER = create("hamburger",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_HAMBURGER.get())
            .addOutput(ModItems.HAMBURGER.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ModItems.BEEF_PATTY.get()))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.TOMATO))
    ),
    CHEESE_BURGER = create("cheese_burger",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_CHEESE_BURGER.get())
            .addOutput(CDItems.CHEESE_BURGER.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CDTags.ItemTag.CHEESE))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ModItems.BEEF_PATTY.get()))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.TOMATO))
    ),
    PORK_OMELETTE_BURGER = create("pork_omelette_burger",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_PORK_OMELETTE_BURGER.get())
            .addOutput(CDItems.PORK_OMELETTE_BURGER.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CDItems.OMELETTE.get()))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.COOKED_PORKCHOP))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.CHEESE, 250))
    ),
    BACON_SANDWICH = create("bacon_sandwich",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_BACON_SANDWICH.get())
            .addOutput(ModItems.BACON_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_BACON))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.TOMATO))
    ),
    COD_SANDWICH = create("cod_sandwich",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_COD_SANDWICH.get())
            .addOutput(CDItems.COD_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_FISHES_COD))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.KELP))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.CREAM, 250))
    ),
    SALMON_SANDWICH = create("salmon_sandwich",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_SALMON_SANDWICH.get())
            .addOutput(CDItems.SALMON_SANDWICH.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_FISHES_SALMON))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(Items.SEA_PICKLE))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.TOMATO))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.SOUR_CREAM, 250))
    ),
    MUTTON_WRAP = create("mutton_wrap",
        builder -> builder
            .require(ForgeTags.BREAD)
            .transitionTo(CDItems.INCOMPLETE_SALMON_SANDWICH.get())
            .addOutput(ModItems.MUTTON_WRAP.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.COOKED_MUTTON))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(ForgeTags.SALAD_INGREDIENTS))
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CommonIngredients.ONION))
    ),
    OMELETTE_RICE = create("omelette_rice",
        builder -> builder
            .require(ModItems.FRIED_RICE.get())
            .transitionTo(CDItems.INCOMPLETE_OMELETTE_RICE.get())
            .addOutput(CDItems.OMELETTE_RICE.get(), 1)
            .loops(1)
            .addStep(DeployerApplicationRecipe::new, b -> b.require(CDItems.OMELETTE.get()))
            .addStep(FillingRecipe::new, b -> b.require(CDTags.FluidTag.TOMATO_SAUCE, 125))
    );
    
    protected GeneratedRecipe create(String name, UnaryOperator<SequencedAssemblyRecipeBuilder> transform) {
        GeneratedRecipe generatedRecipe =
            c -> transform.apply(new SequencedAssemblyRecipeBuilder(CreatesDelight.asResource(name)))
                .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Sequenced Assembly Recipes";
    }
    
}
