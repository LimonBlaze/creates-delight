package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.BlockEntityEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.client.render.block.BlazeStoveRenderer;
import dev.limonblaze.createsdelight.client.render.block.SteamPotRenderer;
import dev.limonblaze.createsdelight.common.block.entity.BlazeStoveBlockEntity;
import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;

public class CDBlockEntities {
    
    public static final CDRegistrate REGISTRATE = CreatesDelight.registrate();
    
    public static final BlockEntityEntry<SteamPotBlockEntity> STEAM_POT = REGISTRATE
        .tileEntity("steam_pot", SteamPotBlockEntity::new)
        .validBlocks(CDBlocks.STEAM_POT)
        .renderer(() -> SteamPotRenderer::new)
        .register();
    
    public static final BlockEntityEntry<BlazeStoveBlockEntity> BLAZE_STOVE = REGISTRATE
        .tileEntity("blaze_stove", BlazeStoveBlockEntity::new)
        .validBlocks(CDBlocks.BLAZE_STOVE)
        .renderer(() -> BlazeStoveRenderer::new)
        .register();
        
    public static void register() {}
    
}
