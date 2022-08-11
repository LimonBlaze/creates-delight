package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.RegistryEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.effect.SimpleMobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class CDMobEffects {
    
    public static final CDRegistrate REGISTRATE = CreatesDelight.registrate();
    
    public static final RegistryEntry<SimpleMobEffect> GREASY = REGISTRATE.simpleEffect("greasy",
        MobEffectCategory.NEUTRAL,
        0xFFFFCC)
        .lang("Greasy")
        .register();
    
    public static final RegistryEntry<SimpleMobEffect> APPETIZING = REGISTRATE.simpleEffect("appetizing",
        MobEffectCategory.BENEFICIAL,
        0xFFCCCC)
        .lang("Appetizing")
        .register();
    
    public static void register() {}
    
}
