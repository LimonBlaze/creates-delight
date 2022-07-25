package dev.limonblaze.createsdelight.data;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.data.client.lang.PartialLangMerger;
import dev.limonblaze.createsdelight.data.server.tag.CrDlBlockTagsProvider;
import dev.limonblaze.createsdelight.data.server.tag.CrDlItemTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CreatesDelight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreatesDelightData {
    
    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if(event.includeServer()) {
            CrDlBlockTagsProvider blockTags = new CrDlBlockTagsProvider(generator, CreatesDelight.ID, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new CrDlItemTagsProvider(generator, blockTags, CreatesDelight.ID, helper));
        }
        if(event.includeClient()) {
            generator.addProvider(new PartialLangMerger(generator));
        }
    }
    
}
