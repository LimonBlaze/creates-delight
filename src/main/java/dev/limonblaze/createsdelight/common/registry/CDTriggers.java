package dev.limonblaze.createsdelight.common.registry;

import dev.limonblaze.createsdelight.common.advancement.BuiltinTrigger;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.resources.ResourceLocation;

import java.util.LinkedList;
import java.util.List;

public class CDTriggers {
    
    private static final List<CriterionTrigger<?>> TRIGGERS = new LinkedList<>();
    
    public static BuiltinTrigger addSimple(ResourceLocation id) {
        return add(new BuiltinTrigger(id));
    }
    
    private static <T extends CriterionTrigger<?>> T add(T instance) {
        TRIGGERS.add(instance);
        return instance;
    }
    
    public static void register() {
        TRIGGERS.forEach(CriteriaTriggers::register);
    }
    
}
