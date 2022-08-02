package dev.limonblaze.createsdelight.common.effect;

import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.AbstractBuilder;
import com.tterrag.registrate.builders.BuilderCallback;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import com.tterrag.registrate.util.nullness.NonnullType;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class EffectBuilder<T extends MobEffect, P> extends AbstractBuilder<MobEffect, T, P, EffectBuilder<T, P>> {
    
    private final NonNullSupplier<T> supplier;
    
    public EffectBuilder(NonNullSupplier<T> supplier, AbstractRegistrate<?> owner, P parent, String name, BuilderCallback callback) {
        super(owner, parent, name, callback, Registry.MOB_EFFECT_REGISTRY);
        this.supplier = supplier;
    }
    
    public EffectBuilder<T, P> lang(String name) {
        return lang(MobEffect::getDescriptionId, name);
    }
    
    @Override
    protected @NonnullType T createEntry() {
        return supplier.get();
    }
    
}
