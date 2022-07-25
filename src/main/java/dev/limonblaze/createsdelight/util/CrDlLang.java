package dev.limonblaze.createsdelight.util;

import com.simibubi.create.foundation.utility.LangBuilder;
import com.simibubi.create.foundation.utility.LangNumberFormat;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import java.util.ArrayList;
import java.util.List;

public class CrDlLang {
    
    public static List<Component> translatedOptions(String prefix, String... keys) {
        List<Component> result = new ArrayList<>(keys.length);
        for (String key : keys)
            result.add(translate((prefix != null ? prefix + "." : "") + key).component());
        return result;
    }
    
    public static Component empty() {
        return TextComponent.EMPTY;
    }
    
    public static LangBuilder builder() {
        return new LangBuilder(CreatesDelight.ID);
    }
    
    public static LangBuilder builder(String namespace) {
        return new LangBuilder(namespace);
    }
    
    public static LangBuilder blockName(BlockState state) {
        return builder().add(state.getBlock()
            .getName());
    }
    
    public static LangBuilder itemName(ItemStack stack) {
        return builder().add(stack.getHoverName()
            .copy());
    }
    
    public static LangBuilder fluidName(FluidStack stack) {
        return builder().add(stack.getDisplayName()
            .copy());
    }
    
    public static LangBuilder number(double d) {
        return builder().text(LangNumberFormat.format(d));
    }
    
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
    
    public static LangBuilder text(String text) {
        return builder().text(text);
    }
    
}
