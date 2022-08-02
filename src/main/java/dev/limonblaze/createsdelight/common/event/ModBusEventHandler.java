package dev.limonblaze.createsdelight.common.event;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.recipe.transform.RecipeTransformManager;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod.EventBusSubscriber(modid = CreatesDelight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBusEventHandler {
    
    @SubscribeEvent
    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(RecipeTransformManager::setup);
    }

}
