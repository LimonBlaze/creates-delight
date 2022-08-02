package dev.limonblaze.createsdelight.common.registry;

import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.core.Registry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.EnumSet;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CreatesDelightCreativeModeTab extends CreativeModeTab {
    
    public CreatesDelightCreativeModeTab() {
        super(CreatesDelight.ID);
    }
    
    @Override
    public ItemStack makeIcon() {
        return CreatesDelightBlocks.BLAZE_STOVE.asStack();
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void fillItemList(NonNullList<ItemStack> items) {
        EnumSet.allOf(CreatesDelightSections.class)
            .stream()
            .flatMap(s -> CreatesDelight.registrate().getAll(s, Registry.ITEM_REGISTRY).stream())
            .forEachOrdered(entry -> entry.get().fillItemCategory(this, items));
    }
    
}
