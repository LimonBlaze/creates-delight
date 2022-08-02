package dev.limonblaze.createsdelight;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.limonblaze.createsdelight.common.registry.*;
import dev.limonblaze.createsdelight.compat.create.CreatesDelightCreate;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreatesDelight.ID)
public class CreatesDelight {
    
    public static final String ID = "createsdelight";
    
    private static final Logger LOGGER = LoggerFactory.getLogger("Create's Delight");
    private static final NonNullSupplier<CreatesDelightRegistrate> REGISTRATE = CreatesDelightRegistrate.lazy(ID);
    
    public static final CreatesDelightCreativeModeTab CREATIVE_MODE_TAB = new CreatesDelightCreativeModeTab();
    
    public CreatesDelight() {
        CreatesDelightBlocks.register();
        CreatesDelightBlockEntities.register();
        CreatesDelightItems.register();
        CreatesDelightFluids.register();
        CreatesDelightEffects.register();
        CreatesDelightCreate.register();
    }
    
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

    public static CreatesDelightRegistrate registrate() {
        return REGISTRATE.get();
    }
    
}
