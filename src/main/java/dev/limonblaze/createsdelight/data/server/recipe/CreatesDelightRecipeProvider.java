package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.foundation.data.recipe.CreateRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.function.Consumer;

public class CreatesDelightRecipeProvider extends CreateRecipeProvider {
    
    protected static final Logger LOGGER = LoggerFactory.getLogger("Create's Delight Recipe Provider");
    
    public CreatesDelightRecipeProvider(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_200404_1_) {
        all.forEach(c -> c.register(p_200404_1_));
        LOGGER.info(getName() + " registered " + all.size() + " recipe" + (all.size() == 1 ? "" : "s"));
    }
    
    public static class Marker {}
    
}
