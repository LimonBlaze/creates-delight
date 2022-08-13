package dev.limonblaze.createsdelight.data;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.data.client.lang.PartialLangMerger;
import dev.limonblaze.createsdelight.data.server.Advancements;
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
            generator.addProvider(new Advancements(generator));
            generator.addProvider(new CompactingRecipes(generator));
            generator.addProvider(new CuttingBoardRecipes(generator));
            generator.addProvider(new EmptyingRecipes(generator));
            generator.addProvider(new FillingRecipes(generator));
            generator.addProvider(new ManualApplicationRecipes(generator));
            generator.addProvider(new MixingRecipes(generator));
            generator.addProvider(new SequencedAssemblyRecipes(generator));
            generator.addProvider(new SteamPotRecipes(generator));
            generator.addProvider(new VanillaRecipes(CreatesDelight.ID, generator));
        }
        if(event.includeClient()) {
            generator.addProvider(new PartialLangMerger(generator));
        }
    }
    
}
