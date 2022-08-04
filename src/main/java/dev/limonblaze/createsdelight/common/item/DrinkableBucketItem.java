package dev.limonblaze.createsdelight.common.item;

import dev.limonblaze.createsdelight.core.duck.ConsumableItemHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;
import vectorwing.farmersdelight.common.Configuration;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class DrinkableBucketItem extends BucketItem implements ConsumableItemHelper {
    private final boolean hasFoodEffectTooltip;
    private final boolean hasCustomTooltip;
    
    public DrinkableBucketItem(Supplier<? extends Fluid> supplier, Properties properties, boolean hasFoodEffectTooltip, boolean hasCustomTooltip) {
        super(supplier, properties);
        this.hasFoodEffectTooltip = hasFoodEffectTooltip;
        this.hasCustomTooltip = hasCustomTooltip;
    }
    
    public DrinkableBucketItem(Supplier<? extends Fluid> supplier, Properties properties, boolean hasFoodEffectTooltip) {
        this(supplier, properties, hasFoodEffectTooltip, false);
    }
    
    public DrinkableBucketItem(Supplier<? extends Fluid> supplier, Properties properties) {
        this(supplier, properties, false, false);
    }
    
    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        InteractionResultHolder<ItemStack> result = super.use(level, player, hand);
        if(result.getResult() == InteractionResult.PASS) {
            ItemStack stack = result.getObject();
            //An inedible bucket is always drinkable while an edible bucket accepts food value limits
            if(!stack.isEdible() || player.canEat(stack.getFoodProperties(player).canAlwaysEat())) {
                return ItemUtils.startUsingInstantly(level, player, hand);
            }
        }
        return result;
    }
    
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        entity.eat(level, stack);
        if (entity instanceof ServerPlayer serverplayer) {
            CriteriaTriggers.CONSUME_ITEM.trigger(serverplayer, stack);
            serverplayer.awardStat(Stats.ITEM_USED.get(this));
        }
        return (entity instanceof Player player) ? getEmptySuccessItem(stack, player) : new ItemStack(Items.BUCKET);
    }
    
    public int getUseDuration(ItemStack stack) {
        return 32;
    }
    
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }
    
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new FluidBucketWrapper(stack);
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
            if(this.hasCustomTooltip) {
                tooltip.addAll(this.getCustomTooltips(stack, level, flag));
            }
            if(this.hasFoodEffectTooltip) {
                TextUtils.addFoodEffectTooltip(stack, tooltip, 1.0F);
            }
        }
    }
    
}
