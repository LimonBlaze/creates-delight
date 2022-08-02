package dev.limonblaze.createsdelight.compat.create.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CreatesDelightArmInteractionPointTypes {
    
    public static final BlazeStovePoint.Type BLAZE_STOVE = register("blaze_stove", BlazeStovePoint.Type::new);

    private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(CreatesDelight.asResource(id));
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
}
