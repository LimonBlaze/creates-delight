package dev.limonblaze.createsdelight.compat.create.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CreatesDelightArmInteractionPointTypes {
    
    public static final BlazeStovePoint.Type BLAZE_STOVE = register(
        CreatesDelight.asResource("blaze_stove"), BlazeStovePoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = register(
        ModHelper.FD.asResource("cooking_pot"), CookingPotPoint.Type::new);

    private static <T extends ArmInteractionPointType> T register(ResourceLocation id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(id);
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
}
