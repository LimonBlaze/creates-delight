package dev.limonblaze.createsdelight.common.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.limonblaze.createsdelight.common.registry.CDRecipeTypes;
import dev.limonblaze.createsdelight.util.SerializationUtils;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.util.RecipeMatcher;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SteamPotRecipe implements Recipe<RecipeWrapper> {
    public static final int INPUT_SLOTS = 6;
    private final ResourceLocation id;
    private final String group;
    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ItemStack container;
    private final float experience;
    private final int cookTime;
    
    public SteamPotRecipe(ResourceLocation id, String group, NonNullList<Ingredient> inputItems, ItemStack output, ItemStack container, float experience, int cookTime) {
        this.id = id;
        this.group = group;
        this.inputItems = inputItems;
        this.output = output;
        if (!container.isEmpty()) {
            this.container = container;
        } else if (!output.getContainerItem().isEmpty()) {
            this.container = output.getContainerItem();
        } else {
            this.container = ItemStack.EMPTY;
        }
        
        this.experience = experience;
        this.cookTime = cookTime;
    }
    
    public boolean isSpecial() {
        return true;
    }
    
    public ResourceLocation getId() {
        return this.id;
    }
    
    public String getGroup() {
        return this.group;
    }
    
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }
    
    public ItemStack getResultItem() {
        return this.output;
    }
    
    public ItemStack getOutputContainer() {
        return this.container;
    }
    
    public ItemStack assemble(RecipeWrapper inv) {
        return this.output.copy();
    }
    
    public float getExperience() {
        return this.experience;
    }
    
    public int getCookTime() {
        return this.cookTime;
    }
    
    public boolean matches(RecipeWrapper inv, Level worldIn) {
        List<ItemStack> inputs = new ArrayList<>();
        int i = 0;
        
        for(int j = 0; j < INPUT_SLOTS; ++j) {
            ItemStack itemstack = inv.getItem(j);
            if (!itemstack.isEmpty()) {
                ++i;
                inputs.add(itemstack);
            }
        }
        
        return i == this.inputItems.size() && RecipeMatcher.findMatches(inputs, this.inputItems) != null;
    }
    
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= this.inputItems.size();
    }
    
    public RecipeSerializer<?> getSerializer() {
        return CDRecipeTypes.STEAM_POT.getSerializer();
    }
    
    public RecipeType<?> getType() {
        return CDRecipeTypes.STEAM_POT.getType();
    }
    
    public static class Serializer extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<SteamPotRecipe> {
        
        public SteamPotRecipe fromJson(ResourceLocation recipeId, JsonObject json) {
            String groupIn = GsonHelper.getAsString(json, "group", "");
            NonNullList<Ingredient> inputItemsIn = NonNullList.create();
            for(JsonElement je : GsonHelper.getAsJsonArray(json, "ingredients")) {
                inputItemsIn.add(Ingredient.fromJson(je));
            }
            if(inputItemsIn.isEmpty()) {
                throw new JsonParseException("No ingredients for steam pot recipe");
            } else if(inputItemsIn.size() > INPUT_SLOTS) {
                throw new JsonParseException("Too many ingredients for steam pot recipe! The max is 6");
            }
            ItemStack outputIn = CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "result"), true);
            ItemStack container = GsonHelper.isValidNode(json, "container") ? CraftingHelper.getItemStack(GsonHelper.getAsJsonObject(json, "container"), true) : ItemStack.EMPTY;
            float experienceIn = GsonHelper.getAsFloat(json, "experience", 0.0F);
            int cookTimeIn = GsonHelper.getAsInt(json, "cookingtime", 200);
            return new SteamPotRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
        }
        
        public void toJson(SteamPotRecipe recipe, JsonObject json) {
            json.addProperty("group", recipe.group);
            JsonArray inputItems = new JsonArray();
            json.add("ingredients", inputItems);
            for(Ingredient ingredient : recipe.inputItems) {
                inputItems.add(ingredient.toJson());
            }
            json.add("result", SerializationUtils.itemStackToJson(recipe.output, true));
            if(!recipe.container.isEmpty()) json.add("container", SerializationUtils.itemStackToJson(recipe.container, true));
            json.addProperty("experience", recipe.experience);
            json.addProperty("cookingtime", recipe.cookTime);
        }
        
        @Nullable
        public SteamPotRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf buffer) {
            String groupIn = buffer.readUtf(32767);
            int i = buffer.readVarInt();
            NonNullList<Ingredient> inputItemsIn = NonNullList.withSize(i, Ingredient.EMPTY);
            
            for(int j = 0; j < inputItemsIn.size(); ++j) {
                inputItemsIn.set(j, Ingredient.fromNetwork(buffer));
            }
            
            ItemStack outputIn = buffer.readItem();
            ItemStack container = buffer.readItem();
            float experienceIn = buffer.readFloat();
            int cookTimeIn = buffer.readVarInt();
            return new SteamPotRecipe(recipeId, groupIn, inputItemsIn, outputIn, container, experienceIn, cookTimeIn);
        }
        
        public void toNetwork(FriendlyByteBuf buffer, SteamPotRecipe recipe) {
            buffer.writeUtf(recipe.group);
            buffer.writeVarInt(recipe.inputItems.size());
    
            for(Ingredient ingredient : recipe.inputItems) {
                ingredient.toNetwork(buffer);
            }
            
            buffer.writeItem(recipe.output);
            buffer.writeItem(recipe.container);
            buffer.writeFloat(recipe.experience);
            buffer.writeVarInt(recipe.cookTime);
        }
    }
    
    public static class Builder {
        private final NonNullList<Ingredient> ingredients = NonNullList.create();
        private final Item result;
        private final Item container;
        private final int count;
        private String group = "";
        private int cookingTime = 200;
        private float experience = 0.35F;
        
        public Builder(ItemLike result, ItemLike container, int count) {
            this.result = result.asItem();
            this.container = container.asItem();
            this.count = count;
        }
        
        @SuppressWarnings("deprecation")
        public Builder(ItemLike result, int count) {
            this.result = result.asItem();
            this.container = result.asItem().getCraftingRemainingItem();
            this.count = count;
        }
        
        public Builder require(ItemLike item) {
            ingredients.add(Ingredient.of(item));
            return this;
        }
        
        public Builder require(Supplier<? extends ItemLike> item) {
            ingredients.add(Ingredient.of(item.get().asItem()));
            return this;
        }
        
        public Builder require(TagKey<Item> tag) {
            ingredients.add(Ingredient.of(tag));
            return this;
        }
        
        public Builder require(Ingredient ingredient) {
            ingredients.add(ingredient);
            return this;
        }
        
        public Builder group(String group) {
            this.group = group;
            return this;
        }
        
        public Builder cookingTime(int cookingTime) {
            this.cookingTime = cookingTime;
            return this;
        }
        
        public Builder experience(float experience) {
            this.experience = experience;
            return this;
        }
        
        public SteamPotRecipe build(ResourceLocation id) {
            return new SteamPotRecipe(id, group, ingredients, new ItemStack(result, count), new ItemStack(container), experience, cookingTime);
        }
        
        public Finished finish(ResourceLocation id) {
            return build(id).new Finished();
        }
        
    }
    
    public class Finished implements FinishedRecipe {
        
        @Override
        public void serializeRecipeData(JsonObject json) {
            ((Serializer) CDRecipeTypes.STEAM_POT.getSerializer()).toJson(SteamPotRecipe.this, json);
        }
    
        @Override
        public ResourceLocation getId() {
            return SteamPotRecipe.this.id;
        }
    
        @Override
        public RecipeSerializer<?> getType() {
            return SteamPotRecipe.this.getSerializer();
        }
    
        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }
    
        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }
        
    }
    
}
