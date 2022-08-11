package dev.limonblaze.createsdelight.data.server.recipe;

import dev.limonblaze.createsdelight.common.recipe.SteamPotRecipe;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class SteamPotRecipeGen extends CDRecipeProvider {
    
    public SteamPotRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Steam Pot Recipes";
    }
    
    protected GeneratedRecipe create(ResourceLocation id, ItemLike result, ItemLike container, int count, UnaryOperator<SteamPotRecipe.Builder> transform) {
        GeneratedRecipe generated = c -> c.accept(transform
            .apply(new SteamPotRecipe.Builder(result, container, count))
            .finish(id)
        );
        all.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe create(ResourceLocation id, ItemLike result, int count, UnaryOperator<SteamPotRecipe.Builder> transform) {
        GeneratedRecipe generated = c -> c.accept(transform
            .apply(new SteamPotRecipe.Builder(result, count))
            .finish(id)
        );
        all.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe create(ItemLike result, int count, UnaryOperator<SteamPotRecipe.Builder> transform) {
        GeneratedRecipe generated = c -> c.accept(transform
            .apply(new SteamPotRecipe.Builder(result, count))
            .finish(result.asItem().getRegistryName())
        );
        all.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe create(ResourceLocation id, ItemLike result, UnaryOperator<SteamPotRecipe.Builder> transform) {
        GeneratedRecipe generated = c -> c.accept(transform
            .apply(new SteamPotRecipe.Builder(result, 1))
            .finish(id)
        );
        all.add(generated);
        return generated;
    }
    
    protected GeneratedRecipe create(ItemLike result, UnaryOperator<SteamPotRecipe.Builder> transform) {
        GeneratedRecipe generated = c -> c.accept(transform
            .apply(new SteamPotRecipe.Builder(result, 1))
            .finish(result.asItem().getRegistryName())
        );
        all.add(generated);
        return generated;
    }
    
}
