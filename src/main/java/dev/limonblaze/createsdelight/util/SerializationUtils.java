package dev.limonblaze.createsdelight.util;

import com.google.gson.JsonObject;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class SerializationUtils {
    
    public static JsonObject itemStackToJson(ItemStack stack, boolean keepNbt) {
        JsonObject json = new JsonObject();
        json.addProperty("item", stack.getItem().getRegistryName().toString());
        int count = stack.getCount();
        if(count > 1) json.addProperty("count", count);
        if(keepNbt) {
            CompoundTag tag = stack.serializeNBT();
            tag.remove("id");
            tag.remove("Count");
            json.addProperty("nbt", tag.toString());
        }
        return json;
    }
    
    public static JsonObject itemStackToJson(ItemStack stack) {
        return itemStackToJson(stack, false);
    }
    
}
