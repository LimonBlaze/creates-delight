package dev.limonblaze.createsdelight.common.item;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public abstract class ConditionedFoodItem extends FoodItem {
    protected final FoodProperties conditionedFood;
    
    public ConditionedFoodItem(Properties properties, FoodProperties conditionedFood) {
        super(properties, true, true);
        this.conditionedFood = conditionedFood;
    }
    
    public abstract boolean canUseConditionedFood(ItemStack stack, @Nullable LivingEntity entity);
    
    @Override
    public boolean isEdible() {
        return true;
    }
    
    @Nullable
    @Override
    public FoodProperties getFoodProperties(ItemStack stack, @Nullable LivingEntity entity) {
        if(this.canUseConditionedFood(stack, entity)) {
            return conditionedFood;
        }
        return super.getFoodProperties(stack, entity);
    }
    
}
