package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.ItemEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.item.EffectCuringBottleItem;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

@SuppressWarnings("unused")
public class CreatesDelightItems {
    
    private static final CreatesDelightRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final CreatesDelightSections INGREDIENT_CONTAINER = REGISTRATE.enterSection(CreatesDelightSections.INGREDIENT_CONTAINER);
    
    public static final ItemEntry<EffectCuringBottleItem> YOGURT_BOTTLE = REGISTRATE.item("yogurt_bottle", EffectCuringBottleItem::new)
        .properties(p -> p
            .food(CreatesDelightFoods.YOGURT_BOTTLE)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16))
        .tag(TagHelper.Items.BOTTLES$YOGURT)
        .register();
    
    public static final ItemEntry<EffectCuringBottleItem> CREAM_BOTTLE = REGISTRATE.item("cream_bottle", EffectCuringBottleItem::new)
        .properties(p -> p
            .food(CreatesDelightFoods.CREAM_BOTTLE)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16))
        .tag(TagHelper.Items.BOTTLES$CREAM)
        .register();
    
    public static final ItemEntry<EffectCuringBottleItem> SOUR_CREAM_BOTTLE = REGISTRATE.item("sour_cream_bottle", EffectCuringBottleItem::new)
        .properties(p -> p
            .food(CreatesDelightFoods.SOUR_CREAM_BOTTLE)
            .craftRemainder(Items.GLASS_BOTTLE)
            .stacksTo(16))
        .tag(TagHelper.Items.BOTTLES$SOUR_CREAM)
        .register();
    
    public static final CreatesDelightSections INGREDIENT = REGISTRATE.enterSection(CreatesDelightSections.INGREDIENT);
    
    public static final ItemEntry<Item> CHEESE = REGISTRATE.item("cheese", Item::new)
        .properties(p -> p.food(CreatesDelightFoods.CHEESE))
        .tag(TagHelper.Items.CHEESE)
        .register();
    
    public static final ItemEntry<Item> BUTTER = REGISTRATE.item("butter", Item::new)
        .properties(p -> p.food(CreatesDelightFoods.BUTTER))
        .tag(TagHelper.Items.BUTTER)
        .register();
    
    public static final ItemEntry<Item> OMELETTE = REGISTRATE.item("omelette", Item::new)
        .properties(p -> p.food(CreatesDelightFoods.OMELETTE))
        .register();
    
    public static final ItemEntry<Item> SALT = REGISTRATE.item("salt", Item::new)
        .tag(TagHelper.Items.DUSTS$SALT)
        .register();
    
    public static void register() {}
    
}
