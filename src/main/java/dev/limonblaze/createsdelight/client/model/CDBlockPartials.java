package dev.limonblaze.createsdelight.client.model;

import com.jozufozu.flywheel.core.PartialModel;
import dev.limonblaze.createsdelight.CreatesDelight;

public class CDBlockPartials {
    
    public static final PartialModel BLAZE_STOVE_HAT = block("blaze_stove/hat");
    public static final PartialModel STEAM_POT_OUTER = block("steam_pot/block_outer");
    
    private static PartialModel block(String path) {
        return new PartialModel(CreatesDelight.asResource("block/" + path));
    }
    
    private static PartialModel entity(String path) {
        return new PartialModel(CreatesDelight.asResource("entity/" + path));
    }
    
    public static void bootstrap() {}
    
}
