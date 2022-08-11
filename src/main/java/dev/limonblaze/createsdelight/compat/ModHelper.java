package dev.limonblaze.createsdelight.compat;

import dev.limonblaze.createsdelight.data.server.recipe.DatagenIngredient;
import net.minecraft.resources.ResourceLocation;

public enum ModHelper {
    FG("forge"),
    FD("farmersdelight"),
    CR("create");
    
    public final String id;
    
    ModHelper(String id) {
        this.id = id;
    }
    
    public ResourceLocation asResource(String path) {
        return new ResourceLocation(id, path);
    }
    
    public DatagenIngredient ingredient(String name, boolean tag) {
        return new DatagenIngredient(asResource(name), tag);
    }
    
}
