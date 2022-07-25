package dev.limonblaze.createsdelight;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreatesDelight.ID)
public class CreatesDelight {
    public static final String ID = "createsdelight";
    private static final Logger LOGGER = LoggerFactory.getLogger("Create's Delight");

    public CreatesDelight() {
    
    }
    
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

}
