package dev.limonblaze.createsdelight.compat.jei;

import com.simibubi.create.Create;
import com.simibubi.create.compat.jei.CreateJEI;
import com.simibubi.create.compat.jei.DoubleItemIcon;
import com.simibubi.create.compat.jei.EmptyBackground;
import com.simibubi.create.compat.jei.ItemIcon;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.config.CRecipes;
import com.simibubi.create.foundation.config.ConfigBase;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.util.CrDlLang;
import mezz.jei.api.gui.drawable.IDrawable;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * This class is a modified copy of {@linkplain CreateJEI}.CategoryBuilder to create {@linkplain CreateRecipeCategory}, license is given below. <br/>
 * <br/>
 * MIT License <br/>
 * <br/>
 * Copyright (c) 2019 simibubi <br/>
 * <br/>
 * Permission is hereby granted, free of charge, to any person obtaining a copy <br/>
 * of this software and associated documentation files (the "Software"), to deal <br/>
 * in the Software without restriction, including without limitation the rights <br/>
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell <br/>
 * copies of the Software, and to permit persons to whom the Software is <br/>
 * furnished to do so, subject to the following conditions: <br/>
 * <br/>
 * The above copyright notice and this permission notice shall be included in all <br/>
 * copies or substantial portions of the Software. <br/>
 * <br/>
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR <br/>
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, <br/>
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE <br/>
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER <br/>
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, <br/>
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br/>
 * SOFTWARE. <br/>
 */
@SuppressWarnings("unused")
public class CreateRecipeCategoryBuilder<T extends Recipe<?>> {
    private final Class<? extends T> recipeClass;
    private Predicate<CRecipes> predicate = cRecipes -> true;
    
    private IDrawable background;
    private IDrawable icon;
    
    private final List<Consumer<List<T>>> recipeListConsumers = new ArrayList<>();
    private final List<Supplier<? extends ItemStack>> catalysts = new ArrayList<>();
    
    public CreateRecipeCategoryBuilder(Class<? extends T> recipeClass) {
        this.recipeClass = recipeClass;
    }
    
    public CreateRecipeCategoryBuilder<T> enableIf(Predicate<CRecipes> predicate) {
        this.predicate = predicate;
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> enableWhen(Function<CRecipes, ConfigBase.ConfigBool> configValue) {
        predicate = c -> configValue.apply(c).get();
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> addRecipeListConsumer(Consumer<List<T>> consumer) {
        recipeListConsumers.add(consumer);
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> addRecipes(Supplier<Collection<? extends T>> collection) {
        return addRecipeListConsumer(recipes -> recipes.addAll(collection.get()));
    }
    
    @SuppressWarnings("unchecked")
    public CreateRecipeCategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred) {
        return addRecipeListConsumer(recipes -> CreateJEI.consumeAllRecipes(recipe -> {
            if(pred.test(recipe)) {
                recipes.add((T) recipe);
            }
        }));
    }
    
    public CreateRecipeCategoryBuilder<T> addAllRecipesIf(Predicate<Recipe<?>> pred, Function<Recipe<?>, T> converter) {
        return addRecipeListConsumer(recipes -> CreateJEI.consumeAllRecipes(recipe -> {
            if(pred.test(recipe)) {
                recipes.add(converter.apply(recipe));
            }
        }));
    }
    
    public CreateRecipeCategoryBuilder<T> addTypedRecipes(IRecipeTypeInfo recipeTypeEntry) {
        return addTypedRecipes(recipeTypeEntry::getType);
    }
    
    public CreateRecipeCategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType) {
        return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipes::add, recipeType.get()));
    }
    
    public CreateRecipeCategoryBuilder<T> addTypedRecipes(Supplier<RecipeType<? extends T>> recipeType, Function<Recipe<?>, T> converter) {
        return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipe -> recipes.add(converter.apply(recipe)), recipeType.get()));
    }
    
    public CreateRecipeCategoryBuilder<T> addTypedRecipesIf(Supplier<RecipeType<? extends T>> recipeType, Predicate<Recipe<?>> pred) {
        return addRecipeListConsumer(recipes -> CreateJEI.<T>consumeTypedRecipes(recipe -> {
            if(pred.test(recipe)) {
                recipes.add(recipe);
            }
        }, recipeType.get()));
    }
    
    public CreateRecipeCategoryBuilder<T> addTypedRecipesExcluding(Supplier<RecipeType<? extends T>> recipeType,
                                                                   Supplier<RecipeType<? extends T>> excluded) {
        return addRecipeListConsumer(recipes -> {
            List<Recipe<?>> excludedRecipes = CreateJEI.getTypedRecipes(excluded.get());
            CreateJEI.<T>consumeTypedRecipes(recipe -> {
                for(Recipe<?> excludedRecipe : excludedRecipes) {
                    if(CreateJEI.doInputsMatch(recipe, excludedRecipe)) {
                        return;
                    }
                }
                recipes.add(recipe);
            }, recipeType.get());
        });
    }
    
    public CreateRecipeCategoryBuilder<T> removeRecipes(Supplier<RecipeType<? extends T>> recipeType) {
        return addRecipeListConsumer(recipes -> {
            List<Recipe<?>> excludedRecipes = CreateJEI.getTypedRecipes(recipeType.get());
            recipes.removeIf(recipe -> {
                for(Recipe<?> excludedRecipe : excludedRecipes) {
                    if(CreateJEI.doInputsMatch(recipe, excludedRecipe)) {
                        return true;
                    }
                }
                return false;
            });
        });
    }
    
    public CreateRecipeCategoryBuilder<T> catalystStack(Supplier<ItemStack> supplier) {
        catalysts.add(supplier);
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> catalyst(Supplier<ItemLike> supplier) {
        return catalystStack(() -> new ItemStack(supplier.get()
            .asItem()));
    }
    
    public CreateRecipeCategoryBuilder<T> icon(IDrawable icon) {
        this.icon = icon;
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> itemIcon(ItemLike item) {
        icon(new ItemIcon(() -> new ItemStack(item)));
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> doubleItemIcon(ItemLike item1, ItemLike item2) {
        icon(new DoubleItemIcon(() -> new ItemStack(item1), () -> new ItemStack(item2)));
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> background(IDrawable background) {
        this.background = background;
        return this;
    }
    
    public CreateRecipeCategoryBuilder<T> emptyBackground(int width, int height) {
        background(new EmptyBackground(width, height));
        return this;
    }
    
    public CreateRecipeCategory<T> build(String name, CreateRecipeCategory.Factory<T> factory) {
        Supplier<List<T>> recipesSupplier;
        if(predicate.test(AllConfigs.SERVER.recipes)) {
            recipesSupplier = () -> {
                List<T> recipes = new ArrayList<>();
                for(Consumer<List<T>> consumer : recipeListConsumers)
                    consumer.accept(recipes);
                return recipes;
            };
        } else {
            recipesSupplier = Collections::emptyList;
        }
        
        CreateRecipeCategory.Info<T> info = new CreateRecipeCategory.Info<>(
            new mezz.jei.api.recipe.RecipeType<>(Create.asResource(name), recipeClass),
            CrDlLang.translate("recipe." + name).component(), background, icon, recipesSupplier, catalysts);
        return factory.create(info);
    }
    
}
