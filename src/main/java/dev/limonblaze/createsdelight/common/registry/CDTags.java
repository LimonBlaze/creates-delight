package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.AllItems;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.item.DrinkableBucketItem;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.Tag;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.common.Tags;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import vectorwing.farmersdelight.common.item.ConsumableItem;
import vectorwing.farmersdelight.common.registry.ModItems;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import java.util.Collections;

public class CDTags {
    
    public static class BlockTag {
    
        public static final TagKey<Block> FAN_HEATERS = create("fan_heaters");
        public static final TagKey<Block> FAN_TRANSPARENT = create("fan_transparent");
        public static final TagKey<Block> STORAGE_BLOCKS$CHEESE = forge("storage_blocks/cheese");
        public static final TagKey<Block> STORAGE_BLOCKS$SALT = forge("storage_blocks/salt");
        public static final TagKey<Block> STORAGE_BLOCKS$SUGAR = forge("storage_blocks/sugar");
        
        public static TagKey<Block> create(String path) {
            return optional(ForgeRegistries.BLOCKS, ModHelper.CR.asResource(path));
        }
        
        public static TagKey<Block> forge(String path) {
            return optional(ForgeRegistries.BLOCKS, ModHelper.FG.asResource(path));
        }
        
        public static void register(CDRegistrate registrate) {
            registrate.blockTagFromTags(Tags.Blocks.STORAGE_BLOCKS,
                STORAGE_BLOCKS$CHEESE,
                STORAGE_BLOCKS$SALT,
                STORAGE_BLOCKS$SUGAR);
        }
    
    }
    
    public static class ItemTag {
        
        public static final TagKey<Item> UPRIGHT_ON_BELT = create("upright_on_belt");
        public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = create("upright_on_deployer");
        public static final TagKey<Item> STEAMED_STUFFED_BUNS = createsdelight("steamed_stuffed_buns");
        public static final TagKey<Item> INCOMPLETE_FOOD$HAMBURGERS = createsdelight("incomplete_food/hamburgers");
        public static final TagKey<Item> INCOMPLETE_FOOD$PIES = createsdelight("incomplete_food/pies");
        public static final TagKey<Item> INCOMPLETE_FOOD$SANDWICHES = createsdelight("incomplete_food/sandwiches");
        public static final TagKey<Item> INCOMPLETE_FOOD$WRAPS = createsdelight("incomplete_food/wraps");
        public static final TagKey<Item> BUTTER = forge("butter");
        public static final TagKey<Item> CABBAGES = forge("cabbages");
        public static final TagKey<Item> CARROTS = forge("carrots");
        public static final TagKey<Item> CHEESE = forge("cheese");
        public static final TagKey<Item> EGGS = forge("eggs");
        public static final TagKey<Item> ONIONS = forge("onions");
        public static final TagKey<Item> PEPPERONI = forge("pepperoni");
        public static final TagKey<Item> POTATOES = forge("potatoes");
        public static final TagKey<Item> RICE = forge("rice");
        public static final TagKey<Item> SALT = forge("salt");
        public static final TagKey<Item> TOMATOES = forge("tomatoes");
        public static final TagKey<Item> WHEAT_DOUGH = forge("wheat_dough");
        public static final TagKey<Item> WHEAT_FLOUR = forge("wheat_flour");
        public static final TagKey<Item> BOTTLES$YOGURT = forge("bottles/yogurt");
        public static final TagKey<Item> BOTTLES$CREAM = forge("bottles/cream");
        public static final TagKey<Item> BOTTLES$SOUR_CREAM = forge("bottles/sour_cream");
        public static final TagKey<Item> DOUGH$WHEAT = forge("dough/wheat");
        public static final TagKey<Item> DUSTS$SALT = forge("dusts/salt");
        public static final TagKey<Item> FOODS$HAS_CONTAINER = forge("foods/has_container");
        public static final TagKey<Item> STORAGE_BLOCKS$CHEESE = forge("storage_blocks/cheese");
        public static final TagKey<Item> STORAGE_BLOCKS$SALT = forge("storage_blocks/salt");
        public static final TagKey<Item> STORAGE_BLOCKS$SUGAR = forge("storage_blocks/sugar");
        
        public static TagKey<Item> create(String path) {
            return optional(ForgeRegistries.ITEMS, ModHelper.CR.asResource(path));
        }
    
        public static TagKey<Item> createsdelight(String path) {
            return optional(ForgeRegistries.ITEMS, CreatesDelight.asResource(path));
        }
    
        public static TagKey<Item> forge(String path) {
            return optional(ForgeRegistries.ITEMS, ModHelper.FG.asResource(path));
        }
    
        @SuppressWarnings("deprecation")
        public static void register(CDRegistrate registrate) {
            registrate.itemTagFromTags(UPRIGHT_ON_BELT, FOODS$HAS_CONTAINER);
            registrate.itemTagFromSuppliers(UPRIGHT_ON_BELT,
                ModItems.BACON_SANDWICH,
                ModItems.CHICKEN_SANDWICH,
                ModItems.EGG_SANDWICH,
                ModItems.HAMBURGER,
                ModItems.MUTTON_WRAP);
            registrate.itemTagFromTags(UPRIGHT_ON_DEPLOYER, ForgeTags.TOOLS);
            registrate.itemTagFromItems(FOODS$HAS_CONTAINER,
                Items.BEETROOT_SOUP,
                Items.MUSHROOM_STEW,
                Items.RABBIT_STEW,
                Items.SUSPICIOUS_STEW,
                Items.HONEY_BOTTLE,
                Items.MILK_BUCKET);
            registrate.itemTagFromSuppliers(FOODS$HAS_CONTAINER, () -> AllItems.BUILDERS_TEA.get());
            registrate.itemTagFromFilter(FOODS$HAS_CONTAINER, item ->
                item instanceof ConsumableItem && item.hasCraftingRemainingItem() ||
                item instanceof DrinkableBucketItem);
            registrate.itemTagFromSuppliers(CABBAGES, ModItems.CABBAGE);
            registrate.itemTagFromItems(CARROTS, Items.CARROT);
            registrate.itemTagFromItems(EGGS, Items.EGG);
            registrate.itemTagFromSuppliers(ONIONS, ModItems.ONION);
            registrate.itemTagFromItems(POTATOES, Items.POTATO);
            registrate.itemTagFromSuppliers(RICE, ModItems.RICE);
            registrate.itemTagFromSuppliers(TOMATOES, ModItems.TOMATO);
            registrate.itemTagFromSuppliers(WHEAT_DOUGH, () -> AllItems.DOUGH.get(), ModItems.WHEAT_DOUGH);
            registrate.itemTagFromSuppliers(WHEAT_FLOUR, () -> AllItems.WHEAT_FLOUR.get());
            registrate.itemTagFromSuppliers(DOUGH$WHEAT, ModItems.WHEAT_DOUGH);
            registrate.itemTagFromTags(Tags.Items.DUSTS, DUSTS$SALT);
            registrate.itemTagFromTags(Tags.Items.STORAGE_BLOCKS,
                STORAGE_BLOCKS$CHEESE,
                STORAGE_BLOCKS$SALT,
                STORAGE_BLOCKS$SUGAR);
        }
    }
    
    public static class FluidTag {
        
        public static final TagKey<Fluid> BUTTER = forge("butter");
        public static final TagKey<Fluid> CHEESE = forge("cheese");
        public static final TagKey<Fluid> CREAM = forge("cream");
        public static final TagKey<Fluid> EGG = forge("egg");
        public static final TagKey<Fluid> SOUR_CREAM = forge("sour_cream");
        public static final TagKey<Fluid> TOMATO_SAUCE = forge("tomato_sauce");
        public static final TagKey<Fluid> YOGURT = forge("yogurt");
        
        private static TagKey<Fluid> forge(String path) {
            return optional(ForgeRegistries.FLUIDS, ModHelper.FG.asResource(path));
        }
        
    }
    
    public static <T extends IForgeRegistryEntry<T>> TagKey<T> optional(IForgeRegistry<T> registry, ResourceLocation id) {
        return registry.tags().createOptionalTagKey(id, Collections.emptySet());
    }
    
}
