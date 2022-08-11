package dev.limonblaze.createsdelight.common.item;

import net.minecraft.world.item.ItemStack;

public interface CurativeItem {
    
    default ItemStack asCurativeItem(ItemStack self) {
        return self;
    }
    
}
