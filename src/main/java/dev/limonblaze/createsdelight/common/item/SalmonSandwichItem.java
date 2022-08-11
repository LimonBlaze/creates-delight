package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.common.registry.CDFoods;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class SalmonSandwichItem extends FoodItem {
    
    public SalmonSandwichItem(Properties properties) {
        super(properties, true);
    }
    
    @Override
    public boolean isEdible() {
        return true;
    }
    
    @Nullable
    @Override
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if(entity != null && entity.isInWaterRainOrBubble()) {
            return CDFoods.SALMON_SANDWICH_UNDERWATER;
        }
        return CDFoods.SALMON_SANDWICH;
    }
    
}