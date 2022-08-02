package dev.limonblaze.createsdelight.common.recipe.transform;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import javax.annotation.Nullable;
import java.util.function.Function;

public interface RecipeTransformer<O extends Recipe<?>, R extends Recipe<?>> extends Function<Recipe<?>, Recipe<?>> {
    
    RecipeType<O> originalType();
    
    RecipeType<R> resultType();
    
    @Nullable
    R transform(O recipe);
    
    @SuppressWarnings("unchecked")
    @Nullable
    default Recipe<?> apply(Recipe<?> recipe) {
        if(recipe.getType() == originalType()) {
            return transform((O) recipe);
        }
        return null;
    }
    
    static ResourceLocation typePrefix(String type, ResourceLocation location) {
        return new ResourceLocation(location.getNamespace(), type + "/" + location.getPath());
    }
    
}
