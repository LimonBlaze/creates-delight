package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.item.CodSandwichItem;
import dev.limonblaze.createsdelight.common.item.CurativeBottleItem;
import dev.limonblaze.createsdelight.common.item.FoodItem;
import dev.limonblaze.createsdelight.common.item.SalmonSandwichItem;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import vectorwing.farmersdelight.common.FoodValues;
import vectorwing.farmersdelight.common.item.ConsumableItem;

public class CDItems {
    
    private static final CDRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final ItemEntry<Item>
        SALT = REGISTRATE.item("salt", Item::new)
            .tag(CDTags.ItemTag.SALT, CDTags.ItemTag.DUSTS$SALT).register();
    
    public static final ItemEntry<FoodItem>
        BUTTER = food("butter", CDFoods.BUTTER, true, false).tag(CDTags.ItemTag.BUTTER).register(),
        CHEESE = food("cheese", CDFoods.CHEESE, true, false).tag(CDTags.ItemTag.CHEESE).register(),
        OMELETTE = food("omelette", CDFoods.OMELETTE, false, false).register(),
        BLAZERONI = food("blazeroni", CDFoods.BLAZERONI, false, false).tag(CDTags.ItemTag.PEPPERONI).register(),
        HOT_DOG = foodUpright("hot_dog", CDFoods.HOT_DOG, true, false).register(),
        CHEESE_BURGER = foodUpright("cheese_burger", CDFoods.CHEESE_BURGER, true, false).register(),
        PORK_OMELETTE_BURGER = foodUpright("pork_omelette_burger", CDFoods.PORK_OMELETTE_BURGER, true, false).register();
    
    public static final ItemEntry<ConsumableItem>
        OMELETTE_RICE = foodConsumable("omelette_rice", CDFoods.OMELETTE_RICE, Items.BOWL, true, false).register();
    
    public static final ItemEntry<CurativeBottleItem>
        YOGURT_BOTTLE = REGISTRATE.item("yogurt_bottle", CurativeBottleItem::new)
            .properties(p -> p
                .food(CDFoods.YOGURT_BOTTLE)
                .craftRemainder(Items.GLASS_BOTTLE)
                .stacksTo(16))
            .tag(CDTags.ItemTag.BOTTLES$YOGURT)
            .register(),
        CREAM_BOTTLE = REGISTRATE.item("cream_bottle", CurativeBottleItem::new)
            .properties(p -> p
                .food(CDFoods.CREAM_BOTTLE)
                .craftRemainder(Items.GLASS_BOTTLE)
                .stacksTo(16))
            .tag(CDTags.ItemTag.BOTTLES$CREAM)
            .register(),
        SOUR_CREAM_BOTTLE = REGISTRATE.item("sour_cream_bottle", CurativeBottleItem::new)
            .properties(p -> p
                .food(CDFoods.SOUR_CREAM_BOTTLE)
                .craftRemainder(Items.GLASS_BOTTLE)
                .stacksTo(16))
            .tag(CDTags.ItemTag.BOTTLES$SOUR_CREAM)
            .register();
    
    public static final ItemEntry<CodSandwichItem> COD_SANDWICH = REGISTRATE.item("cod_sandwich", CodSandwichItem::new)
        .properties(p -> p.food(CDFoods.COD_SANDWICH))
        .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
        .register();
    
    public static final ItemEntry<SalmonSandwichItem> SALMON_SANDWICH = REGISTRATE.item("salmon_sandwich", SalmonSandwichItem::new)
        .properties(p -> p.food(CDFoods.SALMON_SANDWICH))
        .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
        .register();
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_APPLE_PIE = assemblyUpright("apple_pie", FoodValues.PIE_CRUST),
        INCOMPLETE_SWEET_BERRY_CHEESECAKE = assemblyUpright("sweet_berry_cheesecake", FoodValues.PIE_CRUST),
        INCOMPLETE_EGG_SANDWICH = assemblyUpright("egg_sandwich", Foods.BREAD),
        INCOMPLETE_CHICKEN_SANDWICH = assemblyUpright("chicken_sandwich", Foods.BREAD),
        INCOMPLETE_HOT_DOG = assemblyUpright("hot_dog", Foods.BREAD),
        INCOMPLETE_HAMBURGER = assemblyUpright("hamburger", Foods.BREAD),
        INCOMPLETE_CHEESE_BURGER = assemblyUpright("cheese_burger", Foods.BREAD),
        INCOMPLETE_PORK_OMELETTE_BURGER = assemblyUpright("pork_omelette_burger", Foods.BREAD),
        INCOMPLETE_BACON_SANDWICH = assemblyUpright("bacon_sandwich", Foods.BREAD),
        INCOMPLETE_COD_SANDWICH = assemblyUpright("cod_sandwich", Foods.BREAD),
        INCOMPLETE_SALMON_SANDWICH = assemblyUpright("salmon_sandwich", Foods.BREAD),
        INCOMPLETE_OMELETTE_RICE = assemblyUpright("omelette_rice", FoodValues.COOKED_RICE);
    
    public static void register() {
        CDTags.ItemTag.register(REGISTRATE);
    }
    
    private static ItemBuilder<FoodItem, CDRegistrate> food(String name, FoodProperties food, boolean hasFoodEffect, boolean hasCustomTooltip) {
        return REGISTRATE.item(name, p -> new FoodItem(p, hasFoodEffect, hasCustomTooltip))
            .properties(p -> p.food(food));
    }
    
    private static ItemBuilder<ConsumableItem, CDRegistrate> foodConsumable(String name, FoodProperties food, Item remainder, boolean hasFoodEffect, boolean hasCustomTooltip) {
        return REGISTRATE.item(name, p -> new ConsumableItem(p, hasFoodEffect, hasCustomTooltip))
            .properties(p -> p.food(food).craftRemainder(remainder).stacksTo(16));
    }
    
    private static ItemBuilder<FoodItem, CDRegistrate> foodUpright(String name, FoodProperties food, boolean hasFoodEffect, boolean hasCustomTooltip) {
        return REGISTRATE.item(name, p -> new FoodItem(p, hasFoodEffect, hasCustomTooltip))
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT);
    }
    
    private static ItemEntry<SequencedAssemblyItem> assembly(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> assemblyUpright(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
            .register();
    }
    
}