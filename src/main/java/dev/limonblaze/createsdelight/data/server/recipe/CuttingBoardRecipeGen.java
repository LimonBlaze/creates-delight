package dev.limonblaze.createsdelight.data.server.recipe;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.data.builder.CuttingBoardRecipeBuilder;

import javax.annotation.Nonnull;

public class CuttingBoardRecipeGen extends CDRecipeProvider {
    
    GeneratedRecipe
    CHEESE_FROM_CHEESE_WHEEL = createKnives("cheese_from_cheese_wheel",
        Ingredient.of(CDBlocks.CHEESE_WHEEL.get()),
        CDItems.CHEESE.get(), 4
    );
    
    public CuttingBoardRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Cutting Board Recipes";
    }
    
    public GeneratedRecipe create(ResourceLocation id, Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count) {
        return register(consumer -> CuttingBoardRecipeBuilder.cuttingRecipe(ingredient, tool, mainResult, count).build(consumer, id));
    }
    
    public GeneratedRecipe create(String name, Ingredient ingredient, Ingredient tool, ItemLike mainResult, int count) {
        return create(CreatesDelight.asResource("cutting/" + name), ingredient, tool, mainResult, count);
    }
    
    public GeneratedRecipe createKnives(String name, Ingredient ingredient, ItemLike mainResult, int count) {
        return create(name, ingredient, Ingredient.of(ForgeTags.TOOLS_KNIVES), mainResult, count);
    }
    
}
