package dev.limonblaze.createsdelight.compat.create.ponder;

import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import vectorwing.farmersdelight.common.registry.ModBlocks;

public class CDPonders {
    
    public static void registerTags(FMLClientSetupEvent event) {
        PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
            .add(ModBlocks.STOVE.get())
            .add(ModBlocks.SKILLET.get())
            .add(ModBlocks.COOKING_POT.get())
            .add(CDBlocks.STEAM_POT)
            .add(CDBlocks.BLAZE_STOVE);
    }
    
}
