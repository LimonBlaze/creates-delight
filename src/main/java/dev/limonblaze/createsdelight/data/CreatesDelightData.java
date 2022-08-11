package dev.limonblaze.createsdelight.data;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.data.client.lang.PartialLangMerger;
import dev.limonblaze.createsdelight.data.server.recipe.*;
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
            generator.addProvider(new CompactingRecipeGen(generator));
            generator.addProvider(new CuttingBoardRecipeGen(generator));
            generator.addProvider(new EmptyingRecipeGen(generator));
            generator.addProvider(new FillingRecipeGen(generator));
            generator.addProvider(new ManualApplicationRecipeGen(generator));
            generator.addProvider(new MixingRecipeGen(generator));
            generator.addProvider(new SequencedAssemblyRecipeGen(generator));
            generator.addProvider(new VanillaRecipeGen(CreatesDelight.ID, generator));
        }
        if(event.includeClient()) {
            generator.addProvider(new PartialLangMerger(generator));
        }
    }
    
}
