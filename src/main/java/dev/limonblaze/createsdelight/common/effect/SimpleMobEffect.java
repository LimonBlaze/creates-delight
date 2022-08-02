package dev.limonblaze.createsdelight.common.effect;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A simple {@linkplain MobEffect} implementation which has no effect and a public constructor.
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SimpleMobEffect extends MobEffect {
    
    public SimpleMobEffect(MobEffectCategory category, int color) {
        super(category, color);
    }
    
    @Override
    public void applyEffectTick(LivingEntity livingEntity, int amplifier) {
        //NO-OP
    }
    
    @Override
    public void applyInstantenousEffect(@Nullable Entity source, @Nullable Entity indirectSource, LivingEntity livingEntity, int amplifier, double health) {
        //NO-OP
    }
    
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return false;
    }
    
}
