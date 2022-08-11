package dev.limonblaze.createsdelight.data.server.recipe;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.fluids.FluidAttributes;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * This class is a modified copy of {@linkplain com.simibubi.create.foundation.data.recipe.ProcessingRecipeGen}, license is given below. <br/>
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
public abstract class ProcessingRecipeGen extends CDRecipeProvider {
    
    protected static final int BUCKET = FluidAttributes.BUCKET_VOLUME;
    protected static final int BOTTLE = 250;
    
    public ProcessingRecipeGen(DataGenerator generator) {
        super(generator);
    }
    
    /**
     * Create a processing recipe with a single itemstack ingredient, using its id
     * as the name of the recipe
     */
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(
        String namespace,
        Supplier<ItemLike> singleIngredient,
        UnaryOperator<ProcessingRecipeBuilder<T>> transform
    ) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe = c -> {
            ItemLike iItemProvider = singleIngredient.get();
            ResourceLocation itemId = iItemProvider.asItem().getRegistryName();
            assert itemId != null;
            transform
                .apply(new ProcessingRecipeBuilder<>(
                    serializer.getFactory(),
                    new ResourceLocation(namespace, itemId.getPath())
                    ).withItemIngredients(Ingredient.of(iItemProvider))
                ).build(c);
        };
        all.add(generatedRecipe);
        return generatedRecipe;
    }
    
    /**
     * Create a processing recipe with a single itemstack ingredient, using its id
     * as the name of the recipe
     */
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(
        Supplier<ItemLike> singleIngredient,
        UnaryOperator<ProcessingRecipeBuilder<T>> transform
    ) {
        return create(CreatesDelight.ID, singleIngredient, transform);
    }
    
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe createWithDeferredId(
        Supplier<ResourceLocation> name,
        UnaryOperator<ProcessingRecipeBuilder<T>> transform
    ) {
        ProcessingRecipeSerializer<T> serializer = getSerializer();
        GeneratedRecipe generatedRecipe =
            c -> transform.apply(new ProcessingRecipeBuilder<>(serializer.getFactory(), name.get()))
                .build(c);
        all.add(generatedRecipe);
        return generatedRecipe;
    }
    
    /**
     * Create a new processing recipe, with recipe definitions provided by the
     * function
     */
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(
        ResourceLocation name,
        UnaryOperator<ProcessingRecipeBuilder<T>> transform
    ) {
        return createWithDeferredId(() -> name, transform);
    }
    
    /**
     * Create a new processing recipe, with recipe definitions provided by the
     * function
     */
    protected <T extends ProcessingRecipe<?>> GeneratedRecipe create(
        String name,
        UnaryOperator<ProcessingRecipeBuilder<T>> transform
    ) {
        return create(CreatesDelight.asResource(name), transform);
    }
    
    protected abstract IRecipeTypeInfo getRecipeType();
    
    protected <T extends ProcessingRecipe<?>> ProcessingRecipeSerializer<T> getSerializer() {
        return getRecipeType().getSerializer();
    }
    
    protected Supplier<ResourceLocation> idWithSuffix(Supplier<ItemLike> item, String suffix) {
        return () -> {
            ResourceLocation registryName = item.get().asItem().getRegistryName();
            assert registryName != null;
            return CreatesDelight.asResource(registryName.getPath() + suffix);
        };
    }
    
}
