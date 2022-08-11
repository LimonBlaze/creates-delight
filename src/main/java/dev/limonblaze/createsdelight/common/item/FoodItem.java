package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.compat.farmersdelight.food.FoodItemHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.Configuration;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class FoodItem extends Item implements FoodItemHelper {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    
    public FoodItem(Properties properties) {
        this(properties, false, false);
    }
    
    public FoodItem(Properties properties, boolean hasFoodEffectTooltip) {
        this(properties, hasFoodEffectTooltip, false);
    }
    
    public FoodItem(Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
        if(!this.isEdible()) throw new IllegalStateException("FoodItem should be edible!");
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        return this.hasCustomTooltip;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean hasFoodEffectTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        return this.hasFoodEffectTooltip;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        if(Configuration.FOOD_EFFECT_TOOLTIP.get()) {
            if(this.hasCustomTooltip || this.hasFoodEffectTooltip) {
                tooltip.add(this.getUseAnimationTooltip(stack, level, flag));
            }
            if(this.hasCustomTooltip) {
                tooltip.addAll(this.getCustomTooltips(stack, level, flag));
            }
            if(this.hasFoodEffectTooltip) {
                tooltip.addAll(this.getFoodEffectTooltips(stack, level, flag));
            }
        }
    }
    
}
