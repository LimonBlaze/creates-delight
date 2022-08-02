package dev.limonblaze.createsdelight.common.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;

public class CreatesDelightFoods {
    
    public static final FoodProperties YOGURT_BOTTLE = food(1, 0.2F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.APPETIZING.get(), 300), 1.0F).build();
    public static final FoodProperties YOGURT_BUCKET = food(4, 0.2F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.APPETIZING.get(), 1200), 1.0F).build();
    public static final FoodProperties CREAM_BOTTLE = food(2, 0.4F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.GREASY.get(), 300), 1.0F).build();
    public static final FoodProperties CREAM_BUCKET = food(8, 0.4F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.GREASY.get(), 1200), 1.0F).build();
    public static final FoodProperties SOUR_CREAM_BOTTLE = food(2, 0.3F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.APPETIZING.get(), 300), 1.0F).build();
    public static final FoodProperties SOUR_CREAM_BUCKET = food(8, 0.3F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.APPETIZING.get(), 1200), 1.0F).build();
    public static final FoodProperties CHEESE = food(2, 0.8F).build();
    public static final FoodProperties CHEESE_WHEEL = food(8, 0.8F).build();
    public static final FoodProperties BUTTER = food(2, 0.8F)
        .effect(() -> new MobEffectInstance(CreatesDelightEffects.GREASY.get(), 600), 1.0F).build();
    public static final FoodProperties TOMATO_SAUCE_BUCKET = food(8, 0.4F).build();
    public static final FoodProperties EGG_BUCKET = food(4, 0.1F).build();
    public static final FoodProperties OMELETTE = food(6, 0.6F).build();
    
    public static FoodProperties.Builder food(int nutrition, float saturationMod) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
    }
    
}
