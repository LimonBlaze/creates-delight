package dev.limonblaze.createsdelight.compat.farmersdelight;

import dev.limonblaze.createsdelight.common.item.FoodItem;
import dev.limonblaze.createsdelight.common.registry.CDFoods;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.RegistryObject;
import vectorwing.farmersdelight.FarmersDelight;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.registry.ModItems;

public class CDFarmersDelightPlugin {
    
    public static void commonBootstrap(IEventBus mod, IEventBus forge) {
        mod.addGenericListener(Item.class, EventPriority.LOW, CDFarmersDelightPlugin::registerFoodOverrides);
    }
    
    public static void registerFoodOverrides(final RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        registry.registerAll(
            foodOverride(ModItems.CAKE_SLICE,       FoodValues.CAKE_SLICE),
            foodOverride(ModItems.APPLE_PIE_SLICE,  FoodValues.PIE_SLICE),
            foodOverride(ModItems.SWEET_BERRY_CHEESECAKE_SLICE, FoodValues.PIE_SLICE),
            foodOverride(ModItems.CHOCOLATE_PIE_SLICE,          FoodValues.PIE_SLICE),
            foodOverride(ModItems.EGG_SANDWICH,     CDFoods.EGG_SANDWICH),
            foodOverride(ModItems.CHICKEN_SANDWICH, CDFoods.CHICKEN_SANDWICH),
            foodOverride(ModItems.HAMBURGER,        CDFoods.HAMBURGER),
            foodOverride(ModItems.BACON_SANDWICH,   CDFoods.BACON_SANDWICH),
            foodOverride(ModItems.MUTTON_WRAP,      CDFoods.MUTTON_WRAP)
        );
    }
    
    private static Item foodOverride(RegistryObject<? extends Item> original, FoodProperties food) {
        return new FoodItem(new Item.Properties().food(food).tab(FarmersDelight.CREATIVE_TAB), true, false)
            .setRegistryName(original.getId());
    }
    
}
