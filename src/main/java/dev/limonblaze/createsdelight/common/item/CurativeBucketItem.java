package dev.limonblaze.createsdelight.common.item;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CurativeBucketItem extends DrinkableBucketItem implements CurativeItem {
    
    public CurativeBucketItem(Supplier<? extends Fluid> supplier, Properties properties, boolean hasFoodEffectTooltip) {
        super(supplier, properties, hasFoodEffectTooltip, true);
    }
    
    public CurativeBucketItem(Supplier<? extends Fluid> supplier, Properties properties) {
        this(supplier, properties, true);
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if(!level.isClientSide) entity.curePotionEffects(this.asCurativeItem(stack));
        return super.finishUsingItem(stack, level, entity);
    }
    
    public ItemStack asCurativeItem(ItemStack self) {
        return new ItemStack(Items.MILK_BUCKET);
    }
    
}
