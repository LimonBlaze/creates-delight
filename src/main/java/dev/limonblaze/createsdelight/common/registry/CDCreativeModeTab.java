package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CDCreativeModeTab extends CreativeModeTab {
    private final NonNullSupplier<ItemListFillers> fillers = NonNullSupplier.lazy(this::fillers);
    
    public CDCreativeModeTab() {
        super(CreatesDelight.ID);
    }
    
    @Override
    public ItemStack makeIcon() {
        return CDBlocks.STEAM_POT.asStack();
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemList(NonNullList<ItemStack> items) {
        this.fillers.get().fillItemList(items);
    }
    
    private ItemListFillers fillers() {
        ItemListFillers fillers = new ItemListFillers();
        fillers
            //utility
            .block(CDBlocks.    STEAM_POT)
            .block(CDBlocks.    BLAZE_STOVE)
            //ingredients
            .block(CDBlocks.    SALT_BAG)
            .block(CDBlocks.    SUGAR_BAG)
            .fluid(CDFluids.    YOGURT)
            .fluid(CDFluids.    CREAM)
            .fluid(CDFluids.    SOUR_CREAM)
            .fluid(CDFluids.    BUTTER)
            .fluid(CDFluids.    CHEESE)
            .fluid(CDFluids.    TOMATO_SAUCE)
            .fluid(CDFluids.    EGG)
            .item(CDItems.      YOGURT_BOTTLE)
            .item(CDItems.      CREAM_BOTTLE)
            .item(CDItems.      SOUR_CREAM_BOTTLE)
            .item(CDItems.      SALT)
            .item(CDItems.      BUTTER)
            .block(CDBlocks.    CHEESE_WHEEL)
            .item(CDItems.      CHEESE)
            .item(CDItems.      OMELETTE)
            .item(CDItems.      BLAZERONI)
            //sandwich and hamburger
            .item(ModItems.     EGG_SANDWICH)
            .item(ModItems.     CHICKEN_SANDWICH)
            .item(CDItems.      HOT_DOG)
            .item(ModItems.     HAMBURGER)
            .item(CDItems.      CHEESE_BURGER)
            .item(CDItems.      PORK_OMELETTE_BURGER)
            .item(ModItems.     BACON_SANDWICH)
            .item(CDItems.      COD_SANDWICH)
            .item(CDItems.      SALMON_SANDWICH)
            .item(ModItems.     MUTTON_WRAP)
            .item(CDItems.      OMELETTE_RICE);
        return fillers;
    }
    
    @FunctionalInterface
    private interface ItemListFiller {
        
        void fillItemList(CreativeModeTab tab, NonNullList<ItemStack> items);
        
    }
    
    private class ItemListFillers {
        private final NonNullList<NonNullSupplier<ItemListFiller>> entries = NonNullList.create();
        
        private ItemListFillers item(Supplier<? extends Item> entry) {
            this.entries.add(() -> {
                Item item = entry.get();
                //force item to fill into this CreativeModeTab
                return (tab, items) -> item.fillItemCategory(item.getItemCategory(), items);
            });
            return this;
        }
        
        private ItemListFillers block(Supplier<? extends Block> entry) {
            this.entries.add(() -> entry.get()::fillItemCategory);
            return this;
        }
        
        private ItemListFillers fluid(Supplier<? extends Fluid> entry) {
            return this.item(() -> entry.get().getBucket());
        }
        
        private void fillItemList(NonNullList<ItemStack> items) {
            for(var entry : entries) {
                entry.get().fillItemList(CDCreativeModeTab.this, items);
            }
        }
        
    }
    
}
