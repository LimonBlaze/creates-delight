package dev.limonblaze.createsdelight.common.registry;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import vectorwing.farmersdelight.common.registry.ModEffects;

public class CDFoods {
    
    //ingredient bucket
    public static final FoodProperties EGG_BUCKET = builder(4, 0.2F).build();
    public static final FoodProperties TOMATO_SAUCE_BUCKET = builder(8, 0.4F).build();
    public static final FoodProperties YOGURT_BUCKET = builder(4, 0.2F)
        .effect(() -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 800), 1.0F).build();
    public static final FoodProperties CREAM_BUCKET = builder(8, 0.4F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 300), 1.0F)
        .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 300), 1.0F).build();
    public static final FoodProperties SOUR_CREAM_BUCKET = builder(8, 0.4F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 300), 1.0F)
        .effect(() -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 300), 1.0F).build();
    //ingredient bottle
    public static final FoodProperties YOGURT_BOTTLE = builder(1, 0.2F)
        .effect(() -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 200), 1.0F).build();
    public static final FoodProperties CREAM_BOTTLE = builder(2, 0.4F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 100), 1.0F)
        .effect(() -> new MobEffectInstance(MobEffects.SLOW_FALLING, 100), 1.0F).build();
    public static final FoodProperties SOUR_CREAM_BOTTLE = builder(2, 0.4F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 100), 1.0F)
        .effect(() -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 100), 1.0F).build();
    //ingredient block
    public static final FoodProperties CHEESE_WHEEL = builder(8, 0.8F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 600), 1.0F).build();
    //ingredient item
    public static final FoodProperties BUTTER = builder(2, 0.8F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 200), 1.0F).build();
    public static final FoodProperties CHEESE = builder(2, 0.8F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 200), 1.0F).build();
    public static final FoodProperties OMELETTE = builder(6, 0.4F).build();
    public static final FoodProperties BLAZERONI = builder(6, 0.4F).build();
    //sandwich and hamburger
    public static final FoodProperties EGG_SANDWICH = builder(8, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300), 1.0F).build();
    public static final FoodProperties CHICKEN_SANDWICH = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600), 1.0F).build();
    public static final FoodProperties HOT_DOG = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 300), 1.0F)
        .effect(() -> new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 300), 1.0F).build();
    public static final FoodProperties HAMBURGER = builder(11, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600), 1.0F).build();
    public static final FoodProperties CHEESE_BURGER = builder(11, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600), 1.0F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 600), 1.0F).build();
    public static final FoodProperties PORK_OMELETTE_BURGER = builder(11, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600), 1.0F)
        .effect(() -> new MobEffectInstance(CDMobEffects.GREASY.get(), 600), 1.0F).build();
    public static final FoodProperties BACON_SANDWICH = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600), 1.0F).build();
    public static final FoodProperties COD_SANDWICH = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 600), 1.0F).build();
    public static final FoodProperties COD_SANDWICH_UNDERWATER = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.WATER_BREATHING, 1200), 1.0F).build();
    public static final FoodProperties SALMON_SANDWICH = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 300), 1.0F).build();
    public static final FoodProperties SALMON_SANDWICH_UNDERWATER = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.CONDUIT_POWER, 600), 1.0F).build();
    public static final FoodProperties MUTTON_WRAP = builder(10, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 300), 1.0F).build();
    //bowl foods
    public static final FoodProperties OMELETTE_RICE = builder(16, 0.8F)
        .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 3600), 1.0F)
        .effect(() -> new MobEffectInstance(ModEffects.NOURISHMENT.get(), 6000), 1).build();
    
    public static FoodProperties.Builder builder(int nutrition, float saturationMod) {
        return new FoodProperties.Builder().nutrition(nutrition).saturationMod(saturationMod);
    }
    
}
