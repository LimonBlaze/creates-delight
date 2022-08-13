package dev.limonblaze.createsdelight.data.server.recipe;

import dev.limonblaze.createsdelight.common.recipe.SteamPotRecipe;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.Nonnull;
import java.util.function.UnaryOperator;

public class SteamPotRecipes extends CDRecipeProvider {
    
    GeneratedRecipe
    STEAMED_BUN = create(CDItems.STEAMED_BUN.get(),
        builder -> builder
            .require(CommonIngredients.WHEAT_DOUGH)
            .experience(0.1F)
    ),
    STEAMED_CUSTARD_BUN = create(CDItems.STEAMED_CUSTARD_BUN.get(),
    builder -> builder
        .require(CommonIngredients.WHEAT_DOUGH)
        .require(CDTags.ItemTag.EGGS)
        .require(CDTags.ItemTag.BUTTER)
        .require(Items.SUGAR)
        .require(ForgeTags.MILK)
        .experience(0.35F)
    ),
    STEAMED_CHICKEN_BUN = create(CDItems.STEAMED_CHICKEN_BUN.get(),
    builder -> builder
        .require(CommonIngredients.WHEAT_DOUGH)
        .require(ForgeTags.RAW_CHICKEN)
        .require(CommonIngredients.CARROT)
        .require(CommonIngredients.SALT)
        .experience(0.35F)
    ),
    STEAMED_PORK_BUN = create(CDItems.STEAMED_PORK_BUN.get(),
    builder -> builder
        .require(CommonIngredients.WHEAT_DOUGH)
        .require(ForgeTags.RAW_PORK)
        .require(CommonIngredients.CABBAGE)
        .require(CommonIngredients.SALT)
        .experience(0.35F)
    ),
    STEAMED_BEEF_BUN = create(CDItems.STEAMED_BEEF_BUN.get(),
    builder -> builder
        .require(CommonIngredients.WHEAT_DOUGH)
        .require(ForgeTags.RAW_BEEF)
        .require(CommonIngredients.TOMATO)
        .require(CommonIngredients.SALT)
        .experience(0.35F)
    ),
    STEAMED_MUTTON_BUN = create(CDItems.STEAMED_MUTTON_BUN.get(),
    builder -> builder
        .require(CommonIngredients.WHEAT_DOUGH)
        .require(ForgeTags.RAW_MUTTON)
        .require(CommonIngredients.ONION)
        .require(CommonIngredients.SALT)
        .experience(0.35F)
    );
    
    public SteamPotRecipes(DataGenerator generator) {
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
