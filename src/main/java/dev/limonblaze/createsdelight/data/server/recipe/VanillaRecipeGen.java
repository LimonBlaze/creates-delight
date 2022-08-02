package dev.limonblaze.createsdelight.data.server.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightBlocks;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightItems;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * This class is a modified copy of {@linkplain com.simibubi.create.foundation.data.recipe.StandardRecipeGen}, license is given below. <br/>
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
public class VanillaRecipeGen extends CreatesDelightRecipeProvider {
    
    private final String modid;
    private String currentFolder = "";
    
    Marker INGREDIENT = enterFolder("ingredient");
    
    GeneratedRecipe
    SUGAR_BAG = create(CreatesDelightBlocks.SUGAR_BAG)
        .unlockedBy(() -> Items.SUGAR)
        .viaShapeless(b -> b.requires(Items.SUGAR, 9)),
    SUGAR_FROM_BAG = create(() -> Items.SUGAR)
        .returns(9)
        .withSuffix("from_bag")
        .unlockedBy(CreatesDelightBlocks.SUGAR_BAG)
        .viaShapeless(b -> b.requires(CreatesDelightBlocks.SUGAR_BAG.get())),
    SALT_BAG = create(CreatesDelightBlocks.SALT_BAG)
        .unlockedBy(CreatesDelightItems.SALT)
        .viaShapeless(b -> b.requires(Ingredient.of(TagHelper.Items.DUSTS$SALT), 9)),
    SALT_FROM_BAG = create(CreatesDelightItems.SALT)
        .returns(9)
        .withSuffix("from_bag")
        .unlockedBy(CreatesDelightBlocks.SALT_BAG)
        .viaShapeless(b -> b.requires(CreatesDelightBlocks.SALT_BAG.get())),
    CHEESE_WHEEL = create(CreatesDelightBlocks.CHEESE_WHEEL)
        .unlockedBy(CreatesDelightItems.CHEESE)
        .viaShaped(b -> b
        .define('c', CreatesDelightItems.CHEESE.get())
        .pattern("cc")
        .pattern("cc"));
    
    public VanillaRecipeGen(String modid, DataGenerator generator) {
        super(generator);
        this.modid = modid;
    }
    
    @Override
    @Nonnull
    public String getName() {
        return "Create's Delight: Vanilla Recipes";
    }
    
    public Marker enterFolder(String folder) {
        currentFolder = folder;
        return new Marker();
    }
    
    public GeneratedRecipeBuilder create(Supplier<ItemLike> result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }
    
    public GeneratedRecipeBuilder create(ResourceLocation result) {
        return new GeneratedRecipeBuilder(currentFolder, result);
    }
    
    public GeneratedRecipeBuilder create(ItemProviderEntry<? extends ItemLike> result) {
        return create(result::get);
    }
    
    public GeneratedRecipe createSpecial(Supplier<? extends SimpleRecipeSerializer<?>> serializer, String recipeType,
                                  String path) {
        ResourceLocation location = new ResourceLocation(modid, recipeType + "/" + currentFolder + "/" + path);
        return register(consumer -> {
            SpecialRecipeBuilder b = SpecialRecipeBuilder.special(serializer.get());
            b.save(consumer, location.toString());
        });
    }
    
    public class GeneratedRecipeBuilder {
        
        protected final String path;
        protected String suffix;
        protected Supplier<ItemLike> result;
        protected ResourceLocation compatDatagenOutput;
        protected final List<ICondition> recipeConditions;
        
        protected Supplier<ItemPredicate> unlockedBy;
        protected int amount;
        
        private GeneratedRecipeBuilder(String path) {
            this.path = path;
            this.recipeConditions = new ArrayList<>();
            this.suffix = "";
            this.amount = 1;
        }
        
        public GeneratedRecipeBuilder(String path, Supplier<ItemLike> result) {
            this(path);
            this.result = result;
        }
        
        public GeneratedRecipeBuilder(String path, ResourceLocation result) {
            this(path);
            this.compatDatagenOutput = result;
        }
        
        public GeneratedRecipeBuilder returns(int amount) {
            this.amount = amount;
            return this;
        }
        
        public GeneratedRecipeBuilder unlockedBy(Supplier<? extends ItemLike> item) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                .of(item.get())
                .build();
            return this;
        }
        
        public GeneratedRecipeBuilder unlockedByTag(Supplier<TagKey<Item>> tag) {
            this.unlockedBy = () -> ItemPredicate.Builder.item()
                .of(tag.get())
                .build();
            return this;
        }
        
        public GeneratedRecipeBuilder whenModLoaded(String modid) {
            return withCondition(new ModLoadedCondition(modid));
        }
        
        public GeneratedRecipeBuilder whenModMissing(String modid) {
            return withCondition(new NotCondition(new ModLoadedCondition(modid)));
        }
        
        public GeneratedRecipeBuilder withCondition(ICondition condition) {
            recipeConditions.add(condition);
            return this;
        }
        
        public GeneratedRecipeBuilder withSuffix(String suffix) {
            this.suffix = suffix;
            return this;
        }
    
        public GeneratedRecipe viaShaped(UnaryOperator<ShapedRecipeBuilder> builder) {
            return register(consumer -> {
                ShapedRecipeBuilder b = builder.apply(ShapedRecipeBuilder.shaped(result.get(), amount));
                if (unlockedBy.get() != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createLocation("crafting"));
            });
        }
    
        public GeneratedRecipe viaShapeless(UnaryOperator<ShapelessRecipeBuilder> builder) {
            return register(consumer -> {
                ShapelessRecipeBuilder b = builder.apply(ShapelessRecipeBuilder.shapeless(result.get(), amount));
                if (unlockedBy.get() != null)
                    b.unlockedBy("has_item", inventoryTrigger(unlockedBy.get()));
                b.save(consumer, createLocation("crafting"));
            });
        }
    
        public GeneratedCookingRecipeBuilder viaCooking(Supplier<? extends ItemLike> item) {
            return unlockedBy(item).viaCookingIngredient(() -> Ingredient.of(item.get()));
        }
    
        public GeneratedCookingRecipeBuilder viaCookingTag(Supplier<TagKey<Item>> tag) {
            return unlockedByTag(tag).viaCookingIngredient(() -> Ingredient.of(tag.get()));
        }
    
        public GeneratedCookingRecipeBuilder viaCookingIngredient(Supplier<Ingredient> ingredient) {
            return new VanillaRecipeGen.GeneratedCookingRecipeBuilder(this.path, ingredient);
        }
        
        public ResourceLocation createSimpleLocation(String recipeType) {
            return new ResourceLocation(modid, recipeType + "/" + getRegistryName().getPath() + suffix);
        }
    
        public ResourceLocation createLocation(String recipeType) {
            return new ResourceLocation(modid, recipeType + "/" + path + "/" + getRegistryName().getPath() + suffix);
        }
    
        public ResourceLocation getRegistryName() {
            return compatDatagenOutput == null ? result.get().asItem().getRegistryName() : compatDatagenOutput;
        }
        
    }
    
    public class GeneratedCookingRecipeBuilder extends GeneratedRecipeBuilder {

        protected final Supplier<Ingredient> ingredient;
        protected float exp;
        protected int cookingTime;
        
        private static final SimpleCookingSerializer<?>
            FURNACE = RecipeSerializer.SMELTING_RECIPE,
            SMOKER = RecipeSerializer.SMOKING_RECIPE,
            BLAST = RecipeSerializer.BLASTING_RECIPE,
            CAMPFIRE = RecipeSerializer.CAMPFIRE_COOKING_RECIPE;
        
        public GeneratedCookingRecipeBuilder(String path, Supplier<Ingredient> ingredient) {
            super(path);
            this.ingredient = ingredient;
            cookingTime = 200;
            exp = 0;
        }
    
        public GeneratedCookingRecipeBuilder duration(int duration) {
            cookingTime = duration;
            return this;
        }
    
        public GeneratedCookingRecipeBuilder xp(float xp) {
            exp = xp;
            return this;
        }
    
        public GeneratedRecipe inFurnace() {
            return inFurnace(b -> b);
        }
    
        @SuppressWarnings("SameReturnValue")
        public GeneratedRecipe inFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
            create(FURNACE, builder, 1);
            return null;
        }
    
        public GeneratedRecipe inSmoker() {
            return inSmoker(b -> b);
        }
    
        @SuppressWarnings("SameReturnValue")
        public GeneratedRecipe inSmoker(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
            create(FURNACE, builder, 1);
            create(CAMPFIRE, builder, 3);
            create(SMOKER, builder, .5f);
            return null;
        }
    
        public GeneratedRecipe inBlastFurnace() {
            return inBlastFurnace(b -> b);
        }
    
        @SuppressWarnings("SameReturnValue")
        public GeneratedRecipe inBlastFurnace(UnaryOperator<SimpleCookingRecipeBuilder> builder) {
            create(FURNACE, builder, 1);
            create(BLAST, builder, .5f);
            return null;
        }
    
        public void create(SimpleCookingSerializer<?> serializer,
                           UnaryOperator<SimpleCookingRecipeBuilder> builder,
                           float cookingTimeModifier
        ) {
            register(consumer -> {
                boolean isOtherMod = compatDatagenOutput != null;
                
                SimpleCookingRecipeBuilder b = builder.apply(
                    SimpleCookingRecipeBuilder.cooking(
                        ingredient.get(),
                        isOtherMod ? Items.DIRT : result.get(),
                        exp,
                        (int) (cookingTime * cookingTimeModifier),
                        serializer
                    )
                );
                if (unlockedBy.get() != null)
                    b.unlockedBy("has_item", RecipeProvider.inventoryTrigger(unlockedBy.get()));
                assert serializer.getRegistryName() != null;
                b.save(result -> consumer.accept(isOtherMod 
                    ? new ModdedCookingRecipeResult(result, compatDatagenOutput, recipeConditions)
                    : result
                ), createSimpleLocation(serializer.getRegistryName().getPath()));
            });
        }
        
    }
    
    @MethodsReturnNonnullByDefault
    @ParametersAreNonnullByDefault
    public record ModdedCookingRecipeResult(FinishedRecipe wrapped, ResourceLocation outputOverride, List<ICondition> conditions) implements FinishedRecipe {
        
        @Override
        public ResourceLocation getId() {
            return wrapped.getId();
        }
        
        @Override
        public RecipeSerializer<?> getType() {
            return wrapped.getType();
        }
        
        @Override
        public JsonObject serializeAdvancement() {
            return wrapped.serializeAdvancement();
        }
        
        @Override
        public ResourceLocation getAdvancementId() {
            return wrapped.getAdvancementId();
        }
        
        @Override
        public void serializeRecipeData(JsonObject object) {
            wrapped.serializeRecipeData(object);
            object.addProperty("result", outputOverride.toString());
            
            JsonArray conds = new JsonArray();
            conditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            object.add("conditions", conds);
        }
        
    }
    
}
