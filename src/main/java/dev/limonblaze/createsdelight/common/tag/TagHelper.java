package dev.limonblaze.createsdelight.common.tag;

import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.core.Registry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;

public class TagHelper {
    
    public static class Blocks {
        
        public static final TagKey<Block> STORAGE_BLOCKS$SUGAR = forge("storage_blocks/sugar");
        public static final TagKey<Block> STORAGE_BLOCKS$SALT = forge("storage_blocks/salt");
        public static final TagKey<Block> STORAGE_BLOCKS$CHEESE = forge("storage_blocks/cheese");
        public static final TagKey<Block> FAN_TRANSPARENT = create("fan_transparent");
        public static final TagKey<Block> FAN_HEATERS = create("fan_heaters");
        public static final TagKey<Block> HEAT_SOURCES = farmersdelight("heat_sources");
        
        public static TagKey<Block> create(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, ModHelper.CR.asResource(path));
        }
        public static TagKey<Block> farmersdelight(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, ModHelper.FD.asResource(path));
        }
        
        public static TagKey<Block> forge(String path) {
            return TagKey.create(Registry.BLOCK_REGISTRY, ModHelper.FG.asResource(path));
        }
    
    }
    
    public static class Items {
        
        public static final TagKey<Item> UPRIGHT_ON_DEPLOYER = mod(ModHelper.CR, "upright_on_deployer");
        public static final TagKey<Item> TOOLS$SWORDS = forge("tools/swords");
        public static final TagKey<Item> TOOLS$AXES = forge("tools/axes");
        public static final TagKey<Item> TOOLS$PICKAXES = forge("tools/pickaxes");
        public static final TagKey<Item> TOOLS$SHOVELS = forge("tools/shovels");
        public static final TagKey<Item> TOOLS$HOES = forge("tools/hoes");
        public static final TagKey<Item> EGG = forge("eggs");
        public static final TagKey<Item> CHEESE = forge("cheese");
        public static final TagKey<Item> BUTTER = forge("butter");
        public static final TagKey<Item> DUSTS$SALT = forge("dusts/salt");
        public static final TagKey<Item> STORAGE_BLOCKS$SUGAR = forge("storage_blocks/sugar");
        public static final TagKey<Item> STORAGE_BLOCKS$SALT = forge("storage_blocks/salt");
        public static final TagKey<Item> STORAGE_BLOCKS$CHEESE = forge("storage_blocks/cheese");
        public static final TagKey<Item> BOTTLES$YOGURT = forge("bottles/yogurt");
        public static final TagKey<Item> BOTTLES$CREAM = forge("bottles/cream");
        public static final TagKey<Item> BOTTLES$SOUR_CREAM = forge("bottles/sour_cream");
        public static final TagKey<Item> BUCKETS$EGG = forge("buckets/egg");
        public static final TagKey<Item> BUCKETS$YOGURT = forge("buckets/yogurt");
        public static final TagKey<Item> BUCKETS$CREAM = forge("buckets/cream");
        public static final TagKey<Item> BUCKETS$SOUR_CREAM = forge("buckets/sour_cream");
        public static final TagKey<Item> BUCKETS$CHEESE = forge("buckets/cheese");
        public static final TagKey<Item> BUCKETS$BUTTER = forge("buckets/butter");
        public static final TagKey<Item> BUCKETS$TOMATO_SAUCE = forge("buckets/tomato_sauce");
        
        public static TagKey<Item> forge(String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, ModHelper.FG.asResource(path));
        }
        
        public static TagKey<Item> mod(ModHelper mod, String path) {
            return TagKey.create(Registry.ITEM_REGISTRY, mod.asResource(path));
        }
        
    }
    
    public static class Fluids {
        
        public static final TagKey<Fluid> EGG = forge("egg");
        public static final TagKey<Fluid> YOGURT = forge("yogurt");
        public static final TagKey<Fluid> CREAM = forge("cream");
        public static final TagKey<Fluid> SOUR_CREAM = forge("sour_cream");
        public static final TagKey<Fluid> CHEESE = forge("cheese");
        public static final TagKey<Fluid> BUTTER = forge("butter");
        public static final TagKey<Fluid> TOMATO_SAUCE = forge("tomato_sauce");
        
        private static TagKey<Fluid> forge(String path) {
            return TagKey.create(Registry.FLUID_REGISTRY, ModHelper.FG.asResource(path));
        }
        
    }
    
}
