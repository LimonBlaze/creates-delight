package dev.limonblaze.createsdelight.data;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.data.client.lang.PartialLangMerger;
import dev.limonblaze.createsdelight.data.server.recipe.*;
import dev.limonblaze.createsdelight.data.server.tag.BlockTagGen;
import dev.limonblaze.createsdelight.data.server.tag.ItemTagGen;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = CreatesDelight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CreatesDelightData {
    
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        ExistingFileHelper helper = event.getExistingFileHelper();
        if(event.includeServer()) {
            BlockTagGen blockTags = new BlockTagGen(generator, CreatesDelight.ID, helper);
            generator.addProvider(blockTags);
            generator.addProvider(new ItemTagGen(generator, blockTags, CreatesDelight.ID, helper));
            generator.addProvider(new VanillaRecipeGen(CreatesDelight.ID, generator));
            generator.addProvider(new CuttingBoardRecipeGen(generator));
            generator.addProvider(new MixingRecipeGen(generator));
            generator.addProvider(new CompactingRecipeGen(generator));
            generator.addProvider(new FillingRecipeGen(generator));
            generator.addProvider(new EmptyingRecipeGen(generator));
        }
        if(event.includeClient()) {
            generator.addProvider(new PartialLangMerger(generator));
        }
    }
    
}
