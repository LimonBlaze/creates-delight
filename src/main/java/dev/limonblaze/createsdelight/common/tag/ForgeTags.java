package dev.limonblaze.createsdelight.common.tag;

import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class ForgeTags {
    public static final String FORGE_ID = "forge";
    public static final TagKey<Item> ITEM_TOOLS$SWORDS = item("tools/swords");
    public static final TagKey<Item> ITEM_TOOLS$AXES = item("tools/axes");
    public static final TagKey<Item> ITEM_TOOLS$PICKAXES = item("tools/pickaxes");
    public static final TagKey<Item> ITEM_TOOLS$SHOVELS = item("tools/shovels");
    public static final TagKey<Item> ITEM_TOOLS$HOES = item("tools/hoes");
    
    private static TagKey<Item> item(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, new ResourceLocation(FORGE_ID, path));
    }
}
