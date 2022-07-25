package dev.limonblaze.createsdelight.common.tag;

import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CrDlTags {
    public static final TagKey<Item> ITEM_UPRIGHT_ON_DEPLOYER = item("item_upright_on_deployer");
    
    private static TagKey<Item> item(String path) {
        return TagKey.create(Registry.ITEM_REGISTRY, CreatesDelight.asResource(path));
    }
    
}
