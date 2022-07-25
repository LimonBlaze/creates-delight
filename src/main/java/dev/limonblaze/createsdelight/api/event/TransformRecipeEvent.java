package dev.limonblaze.createsdelight.api.event;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.eventbus.api.Cancelable;
import net.minecraftforge.eventbus.api.GenericEvent;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Cancelable
public class TransformRecipeEvent<T extends Recipe<?>> extends GenericEvent<T> {
    private final Recipe<?> original;
    private T transformed;
    
    public TransformRecipeEvent(Recipe<?> original, T transformed) {
        this.original = original;
        this.transformed = transformed;
    }
    
    public Recipe<?> getOriginal() {
        return original;
    }
    
    public T getTransformed() {
        return transformed;
    }
    
    public void setTransformed(T transformed) {
        this.transformed = transformed;
    }
    
}
