package dev.limonblaze.createsdelight.core.mixin.farmersdelight.client;

import dev.limonblaze.createsdelight.compat.farmersdelight.food.FoodItemHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.item.ConsumableItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mixin(value = ConsumableItem.class)
public abstract class ConsumableItemMixin extends Item implements FoodItemHelper {
    
    @Shadow(remap = false) @Final private boolean hasCustomTooltip;
    
    @Shadow(remap = false) @Final private boolean hasFoodEffectTooltip;
    
    public ConsumableItemMixin(Properties properties) {
        super(properties);
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
    
    /**
     * @author LimonBlaze
     * @reason Overwriting this for integration purpose, shouldn't affect original function. <br/>
     * See also {@linkplain FoodItemHelper}.
     */
    @Overwrite
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
