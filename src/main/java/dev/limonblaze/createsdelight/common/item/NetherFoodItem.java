package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class NetherFoodItem extends ConditionedFoodItem {
    
    public NetherFoodItem(Properties properties, FoodProperties conditionedFood) {
        super(properties, conditionedFood);
    }
    
    @Override
    public boolean canUseConditionedFood(ItemStack stack, @Nullable LivingEntity entity) {
        return entity != null && entity.level.dimensionType().ultraWarm();
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public List<Component> getCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        List<Component> tooltips = new ArrayList<>();
        tooltips.add(LangUtils
            .translate("tooltip")
            .suffix("food")
            .suffix("effect")
            .suffix("in_nether")
            .toComponent().withStyle(ChatFormatting.DARK_RED));
        return tooltips;
    }
    
}
