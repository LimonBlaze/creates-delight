package dev.limonblaze.createsdelight.data.server.recipe;

import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.stream.Stream;

public class CommonIngredients {
    
    //food
    public static final Ingredient CABBAGE = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.CABBAGES),
        new Ingredient.TagValue(ForgeTags.CROPS_CABBAGE),
        new Ingredient.TagValue(ForgeTags.SALAD_INGREDIENTS_CABBAGE)
    ));
    public static final Ingredient CARROT = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.CARROTS),
        new Ingredient.TagValue(ForgeTags.VEGETABLES_CARROT)
    ));
    public static final Ingredient ONION = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.ONIONS),
        new Ingredient.TagValue(ForgeTags.VEGETABLES_ONION),
        new Ingredient.TagValue(ForgeTags.CROPS_ONION)
    ));
    public static final Ingredient POTATO = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.POTATOES),
       new Ingredient.TagValue(ForgeTags.VEGETABLES_POTATO)
    ));
    public static final Ingredient RICE = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.RICE),
        new Ingredient.TagValue(ForgeTags.GRAIN_RICE)
    ));
    public static final Ingredient SALT = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.SALT),
        new Ingredient.TagValue(CDTags.ItemTag.DUSTS$SALT)
    ));
    public static final Ingredient TOMATO = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.TOMATOES),
        new Ingredient.TagValue(ForgeTags.VEGETABLES_TOMATO),
        new Ingredient.TagValue(ForgeTags.CROPS_TOMATO)
    ));
    public static final Ingredient WHEAT_FLOUR = Ingredient.fromValues(Stream.of(
        new Ingredient.TagValue(CDTags.ItemTag.forge("wheat_flour")),
        new Ingredient.TagValue(CDTags.ItemTag.forge("flour/wheat"))
    ));
    
    //metal
    public static final Ingredient GOLD_PLATE = Ingredient.of(CDTags.ItemTag.forge("plates/gold"));
    public static final Ingredient COPPER_NUGGET = Ingredient.of(CDTags.ItemTag.forge("nuggets/copper"));
    public static final Ingredient COPPER_INGOT = Ingredient.of(Items.COPPER_INGOT);
    
}
