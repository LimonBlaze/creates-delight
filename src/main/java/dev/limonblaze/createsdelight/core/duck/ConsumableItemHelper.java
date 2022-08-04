package dev.limonblaze.createsdelight.core.duck;

import dev.limonblaze.createsdelight.core.mixin.farmersdelight.client.ConsumableItemMixin;
import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

/**
 * An extension for {@linkplain vectorwing.farmersdelight.common.item.ConsumableItem}. <br/>
 * Allows dynamic custom tooltips and food effect tooltip conditions. <br/>
 * See also {@linkplain ConsumableItemMixin}
 */
public interface ConsumableItemHelper {
    
    default Item self() {
        return (Item) this;
    }
    
    @OnlyIn(Dist.CLIENT)
    default boolean hasCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        return false;
    }
    
    @OnlyIn(Dist.CLIENT)
    default boolean hasFoodEffectTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        return false;
    }
    
    @OnlyIn(Dist.CLIENT)
    default List<Component> getCustomTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        List<Component> components = new ArrayList<>();
        components.add(LangUtils.translate("tooltip", self().getRegistryName()).withStyle(ChatFormatting.BLUE));
        return components;
    }
    
}
