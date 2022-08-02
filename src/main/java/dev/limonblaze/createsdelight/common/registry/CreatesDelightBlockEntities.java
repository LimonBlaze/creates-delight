package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.client.render.block.BlazeStoveRenderer;
import dev.limonblaze.createsdelight.common.block.entity.BlazeStoveBlockEntity;

public class CreatesDelightBlockEntities {
    
    public static CreatesDelightRegistrate REGISTRATE = CreatesDelight.registrate();
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(CreatesDelightBlocks.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();
    
    public static void register() {}
    
}
