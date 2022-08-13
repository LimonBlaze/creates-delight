package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class UnderwaterFoodItem extends ConditionedFoodItem {
    
    public UnderwaterFoodItem(Properties properties, FoodProperties conditionedFood) {
        super(properties, conditionedFood);
    }
    
    @Override
    public boolean canUseConditionedFood(ItemStack stack, @Nullable LivingEntity entity) {
        return entity != null && entity.isEyeInFluid(FluidTags.WATER);
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public List<Component> getCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        List<Component> tooltips = new ArrayList<>();
        tooltips.add(LangUtils
            .translate("tooltip")
            .suffix("food")
            .suffix("effect")
            .suffix("underwater")
            .toComponent().withStyle(ChatFormatting.DARK_AQUA));
        return tooltips;
    }
    
}
