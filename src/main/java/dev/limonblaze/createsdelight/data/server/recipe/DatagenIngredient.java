package dev.limonblaze.createsdelight.data.server.recipe;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.Nonnull;
import java.util.stream.Stream;

public class DatagenIngredient extends Ingredient {
    
    private final ResourceLocation id;
    private final boolean tag;
    
    public DatagenIngredient(ResourceLocation id, boolean tag) {
        super(Stream.empty());
        this.id = id;
        this.tag = tag;
    }
    
    @Override
    @Nonnull
    public JsonElement toJson() {
        JsonObject jsonobject = new JsonObject();
        jsonobject.addProperty(tag ? "tag" : "item", id.toString());
        return jsonobject;
    }
    
}
