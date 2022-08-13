package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.compat.farmersdelight.food.FoodItemHelper;
import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.PotionEvent;
import vectorwing.farmersdelight.common.item.DrinkableItem;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CurativeBottleItem extends DrinkableItem implements CurativeItem, FoodItemHelper {
    
    public CurativeBottleItem(Properties properties, boolean hasFoodEffectTooltips) {
        super(properties, hasFoodEffectTooltips, true);
    }
    
    public CurativeBottleItem(Properties properties) {
        this(properties, true);
    }
    
    public void affectConsumer(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack curativeItem = this.asCurativeItem(stack);
        ArrayList<MobEffect> curableEffects = new ArrayList<>();
        for(MobEffectInstance instance : entity.getActiveEffects()) {
            if(instance.isCurativeItem(curativeItem)) curableEffects.add(instance.getEffect());
        }
        if (curableEffects.size() > 0) {
            MobEffectInstance toRemove = entity.getEffect(curableEffects.get(level.random.nextInt(curableEffects.size())));
            if(toRemove != null && !MinecraftForge.EVENT_BUS.post(new PotionEvent.PotionRemoveEvent(entity, toRemove))) {
                entity.removeEffect(toRemove.getEffect());
            }
        }
    }
    
    public ItemStack asCurativeItem(ItemStack self) {
        return new ItemStack(Items.MILK_BUCKET);
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public List<Component> getCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        List<Component> tooltips = new ArrayList<>();
        tooltips.add(LangUtils
            .translate("tooltip")
            .suffix("food")
            .suffix("effect")
            .suffix("clear_one")
            .toComponent().withStyle(ChatFormatting.BLUE));
        return tooltips;
    }
    
}
