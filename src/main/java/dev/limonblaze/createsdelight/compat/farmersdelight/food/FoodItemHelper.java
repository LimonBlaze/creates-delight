package dev.limonblaze.createsdelight.compat.farmersdelight.food;

import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.mojang.datafixers.util.Pair;
import dev.limonblaze.createsdelight.core.mixin.farmersdelight.client.ConsumableItemMixin;
import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * An extension for {@linkplain vectorwing.farmersdelight.common.item.ConsumableItem}. <br/>
 * Allows dynamic custom tooltips and food effect tooltip conditions. <br/>
 * See also {@linkplain ConsumableItemMixin}
 */
public interface FoodItemHelper {
    
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
        components.add(LangUtils.translateId("tooltip", self().getRegistryName()).toComponent().withStyle(ChatFormatting.BLUE));
        return components;
    }
    
    @OnlyIn(Dist.CLIENT)
    default List<Component> getFoodEffectTooltips(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        List<Component> tooltips = new ArrayList<>();
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null) return tooltips;
        List<Pair<MobEffectInstance, Float>> effectEntries = stack.getFoodProperties(player).getEffects();
        if(effectEntries.isEmpty()) {
            tooltips.add(new TranslatableComponent("effect.none").withStyle(ChatFormatting.GRAY));
            return tooltips;
        }
        boolean showChance = flag.isAdvanced();
        Multimap<Attribute, AttributeModifier> attributeMap = Multimaps.newListMultimap(new HashMap<>(), ArrayList::new);
        for(var effectEntry : effectEntries) {
            MobEffectInstance effectInstance = effectEntry.getFirst();
            MobEffect effect = effectInstance.getEffect();
            Map<Attribute, AttributeModifier> effectAttributeModifiers = effect.getAttributeModifiers();
            if(!effectAttributeModifiers.isEmpty()) {
                for(Map.Entry<Attribute, AttributeModifier> attributeEntry : effectAttributeModifiers.entrySet()) {
                    AttributeModifier basicModifier = attributeEntry.getValue();
                    AttributeModifier amplifiedModifier = new AttributeModifier(
                        basicModifier.getName(),
                        effect.getAttributeModifierValue(effectInstance.getAmplifier(), basicModifier),
                        basicModifier.getOperation()
                    );
                    attributeMap.put(attributeEntry.getKey(), amplifiedModifier);
                }
            }
            TranslatableComponent effectTooltip = new TranslatableComponent(effect.getDescriptionId());
            if(effectInstance.getAmplifier() > 0) {
                effectTooltip = new TranslatableComponent("potion.withAmplifier", effectTooltip, new TranslatableComponent("potion.potency." + effectInstance.getAmplifier()));
            }
            if(effectInstance.getDuration() > 20) {
                effectTooltip = new TranslatableComponent("potion.withDuration", effectTooltip, MobEffectUtil.formatDuration(effectInstance, 1));
            }
            if(showChance) {
                float chance = effectEntry.getSecond();
                if(chance < 1) {
                    effectTooltip.append(LangUtils.translate("tooltip")
                        .suffix("food").suffix("effect").suffix("chance")
                        .arg(ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(100 * effectEntry.getSecond())).toComponent());
                }
            }
            tooltips.add(effectTooltip.withStyle(effect.getCategory().getTooltipFormatting()));
        }
        if(!attributeMap.isEmpty()) {
            tooltips.add(TextComponent.EMPTY);
            tooltips.add(new TranslatableComponent("potion.whenDrank").withStyle(ChatFormatting.DARK_PURPLE));
            attributeMap.forEach((attribute, modifier) -> {
                double actualValue = modifier.getAmount();
                double displayValue;
                if(modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE
                && modifier.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                    displayValue = modifier.getAmount();
                } else {
                    displayValue = modifier.getAmount() * 100.0D;
                }
                if(actualValue > 0.0D) {
                    tooltips.add(new TranslatableComponent(
                        "attribute.modifier.plus." + modifier.getOperation().toValue(),
                        ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(displayValue),
                        new TranslatableComponent(attribute.getDescriptionId())
                    ).withStyle(ChatFormatting.BLUE));
                } else if(actualValue < 0.0D) {
                    displayValue *= -1.0D;
                    tooltips.add(new TranslatableComponent(
                        "attribute.modifier.take." + modifier.getOperation().toValue(),
                        ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(displayValue),
                        new TranslatableComponent(attribute.getDescriptionId())
                    ).withStyle(ChatFormatting.RED));
                }
            });
        }
        return tooltips;
    }
    
    @OnlyIn(Dist.CLIENT)
    default Component getUseAnimationTooltip(ItemStack stack, @Nullable Level level, TooltipFlag flag) {
        LangUtils.Builder builder = LangUtils.translate("tooltip").suffix("food");
        return (self().getUseAnimation(stack) == UseAnim.DRINK
            ? builder.suffix("when_drank").toComponent()
            : builder.suffix("when_ate").toComponent()).withStyle(ChatFormatting.GRAY);
    }
    
}
