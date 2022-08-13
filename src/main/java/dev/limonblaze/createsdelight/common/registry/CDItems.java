package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.itemAssembly.SequencedAssemblyItem;
import com.tterrag.registrate.builders.ItemBuilder;
import com.tterrag.registrate.util.entry.ItemEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.item.*;
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
        PORK_OMELETTE_BURGER = foodUpright("pork_omelette_burger", CDFoods.PORK_OMELETTE_BURGER, true, false).register(),
        HAM_WRAP = foodUpright("ham_wrap", CDFoods.HAM_WRAP, true, false).register(),
        STEAMED_BUN = food("steamed_bun", Foods.BREAD, false, false).register(),
        STEAMED_CUSTARD_BUN = food("steamed_custard_bun", CDFoods.STEAMED_STUFFED_BUN, true, false).tag(CDTags.ItemTag.STEAMED_STUFFED_BUNS).register(),
        STEAMED_CHICKEN_BUN = food("steamed_chicken_bun", CDFoods.STEAMED_STUFFED_BUN, true, false).tag(CDTags.ItemTag.STEAMED_STUFFED_BUNS).register(),
        STEAMED_PORK_BUN = food("steamed_pork_bun", CDFoods.STEAMED_STUFFED_BUN, true, false).tag(CDTags.ItemTag.STEAMED_STUFFED_BUNS).register(),
        STEAMED_BEEF_BUN = food("steamed_beef_bun", CDFoods.STEAMED_STUFFED_BUN, true, false).tag(CDTags.ItemTag.STEAMED_STUFFED_BUNS).register(),
        STEAMED_MUTTON_BUN = food("steamed_mutton_bun", CDFoods.STEAMED_STUFFED_BUN, true, false).tag(CDTags.ItemTag.STEAMED_STUFFED_BUNS).register();
    
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
    
    public static final ItemEntry<UnderwaterFoodItem>
        COD_SANDWICH = REGISTRATE.item("cod_sandwich", p -> new UnderwaterFoodItem(p, CDFoods.COD_SANDWICH_UNDERWATER))
            .properties(p -> p.food(CDFoods.FISH_SANDWICH))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
            .register(),
        SALMON_SANDWICH = REGISTRATE.item("salmon_sandwich", p -> new UnderwaterFoodItem(p, CDFoods.SALMON_SANDWICH_UNDERWATER))
            .properties(p -> p.food(CDFoods.FISH_SANDWICH))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
            .register();
    
    public static final ItemEntry<NetherFoodItem> NETHER_WRAP = REGISTRATE.item("nether_wrap", p -> new NetherFoodItem(p, CDFoods.NETHER_WRAP_IN_NETHER))
        .properties(p -> p.food(CDFoods.NETHER_WRAP))
        .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
        .register();
    
    public static final ItemEntry<SequencedAssemblyItem>
        INCOMPLETE_APPLE_PIE = assemblyPie("apple_pie", FoodValues.PIE_CRUST),
        INCOMPLETE_SWEET_BERRY_CHEESECAKE = assemblyPie("sweet_berry_cheesecake", FoodValues.PIE_CRUST),
        INCOMPLETE_EGG_SANDWICH = assemblySandwich("egg_sandwich", Foods.BREAD),
        INCOMPLETE_CHICKEN_SANDWICH = assemblySandwich("chicken_sandwich", Foods.BREAD),
        INCOMPLETE_HOT_DOG = assemblySandwich("hot_dog", Foods.BREAD),
        INCOMPLETE_HAMBURGER = assemblyHamburger("hamburger", Foods.BREAD),
        INCOMPLETE_CHEESE_BURGER = assemblyHamburger("cheese_burger", Foods.BREAD),
        INCOMPLETE_PORK_OMELETTE_BURGER = assemblyHamburger("pork_omelette_burger", Foods.BREAD),
        INCOMPLETE_BACON_SANDWICH = assemblySandwich("bacon_sandwich", Foods.BREAD),
        INCOMPLETE_COD_SANDWICH = assemblySandwich("cod_sandwich", Foods.BREAD),
        INCOMPLETE_SALMON_SANDWICH = assemblySandwich("salmon_sandwich", Foods.BREAD),
        INCOMPLETE_MUTTON_WRAP = assemblyWrap("mutton_wrap", Foods.BREAD),
        INCOMPLETE_HAM_WRAP = assemblyWrap("ham_wrap", Foods.BREAD),
        INCOMPLETE_NETHER_WRAP = assemblyWrap("nether_wrap", Foods.BREAD),
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
    
    private static ItemEntry<SequencedAssemblyItem> assemblyHamburger(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT, CDTags.ItemTag.INCOMPLETE_FOOD$HAMBURGERS)
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> assemblyPie(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT, CDTags.ItemTag.INCOMPLETE_FOOD$PIES)
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> assemblySandwich(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT, CDTags.ItemTag.INCOMPLETE_FOOD$SANDWICHES)
            .register();
    }
    
    private static ItemEntry<SequencedAssemblyItem> assemblyWrap(String name, FoodProperties food) {
        return REGISTRATE.item("incomplete_" + name, SequencedAssemblyItem::new)
            .properties(p -> p.food(food))
            .tag(CDTags.ItemTag.UPRIGHT_ON_BELT, CDTags.ItemTag.INCOMPLETE_FOOD$WRAPS)
            .register();
    }
    
}