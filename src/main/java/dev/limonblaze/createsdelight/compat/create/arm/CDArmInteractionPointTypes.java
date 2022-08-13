package dev.limonblaze.createsdelight.compat.create.arm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public class CDArmInteractionPointTypes {
    
    public static final StovePoint.Type STOVE = register(
        ModHelper.FD.asResource("stove"), StovePoint.Type::new);
    public static final SkilletPoint.Type SKILLET = register(
        ModHelper.FD.asResource("skillet"), SkilletPoint.Type::new);
    public static final CookingPotPoint.Type COOKING_POT = register(
        ModHelper.FD.asResource("cooking_pot"), CookingPotPoint.Type::new);
    public static final SteamPotPoint.Type STEAM_POT = register(
        CreatesDelight.asResource("steam_pot"), SteamPotPoint.Type::new);
    public static final BlazeStovePoint.Type BLAZE_STOVE = register(
        CreatesDelight.asResource("blaze_stove"), BlazeStovePoint.Type::new);

    private static <T extends ArmInteractionPointType> T register(ResourceLocation id, Function<ResourceLocation, T> factory) {
        T type = factory.apply(id);
        ArmInteractionPointType.register(type);
        return type;
    }
    
    public static void register() {}
    
}
