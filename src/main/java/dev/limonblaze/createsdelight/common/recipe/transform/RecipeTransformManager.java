package dev.limonblaze.createsdelight.common.recipe.transform;

import com.google.common.collect.ImmutableMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A {@linkplain SimplePreparableReloadListener} which runs after {@linkplain RecipeManager},
 * transforms existing recipe to new ones and adds them to the {@linkplain RecipeManager}. <br/>
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class RecipeTransformManager extends SimplePreparableReloadListener<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger("Recipe Transform Manager");
    private static final List<RecipeTransformer<?, ?>> TRANSFORMERS = new ArrayList<>();
    
    private final RecipeManager recipeManager;
    
    public RecipeTransformManager(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }
    
    public static void setup() {
        registerRecipeTransformer(new CookingPotRecipeTransformer());
        registerRecipeTransformer(new CuttingBoardRecipeTransformer());
    }
    
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return null;
    }
    
    @Override
    protected void apply(Void nothing, ResourceManager resourceManager, ProfilerFiller profiler) {
        //resolve RecipeManager's immutable maps to mutable ones
        var typeRecipes = new HashMap<>(recipeManager.recipes);
        for(var entry : typeRecipes.entrySet()) {
            entry.setValue(new HashMap<>(entry.getValue()));
        }
        var locRecipes = new HashMap<>(recipeManager.byName);
        TRANSFORMERS.forEach(transformer -> {
            int count = 0;
            var originalType = transformer.originalType();
            var resultType = transformer.resultType();
            for(var entry : typeRecipes.get(originalType).entrySet()) {
                Recipe<?> original = entry.getValue();
                Recipe<?> result = transformer.apply(original);
                ResourceLocation id = result.getId();
                //won't override existing recipes
                if(result != null && !locRecipes.containsKey(id)) {
                    typeRecipes.computeIfAbsent(result.getType(), $ -> new HashMap<>()).put(id, result);
                    locRecipes.put(id, result);
                    ++count;
                }
            }
            LOGGER.info("Transformed {} recipe(s) from [{}] into [{}].", count, originalType, resultType);
        });
        //overwrite RecipeManager's immutable maps
        recipeManager.byName = ImmutableMap.copyOf(locRecipes);
        for(var entry : typeRecipes.entrySet()) {
            entry.setValue(ImmutableMap.copyOf(entry.getValue()));
        }
        recipeManager.recipes = ImmutableMap.copyOf(typeRecipes);
    }
    
    public static <O extends Recipe<?>, R extends Recipe<?>> void registerRecipeTransformer(RecipeTransformer<O, R> transformer) {
        TRANSFORMERS.add(transformer);
    }
    
}
