package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.core.duck.ConsumableItemHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.item.MilkBottleItem;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EffectCuringBottleItem extends MilkBottleItem implements ConsumableItemHelper {
    private final boolean hasFoodEffectTooltips;
    
    public EffectCuringBottleItem(Properties properties, boolean hasFoodEffectTooltips) {
        super(properties);
        this.hasFoodEffectTooltips = hasFoodEffectTooltips;
    }
    
    public EffectCuringBottleItem(Properties properties) {
        this(properties, true);
    }
    
    //Cream bottle has food effects, so we have to override milk bottle's setting
    @Override
    public boolean hasFoodEffectTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        return this.hasFoodEffectTooltips;
    }
    
}
