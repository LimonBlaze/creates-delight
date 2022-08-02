package dev.limonblaze.createsdelight.common.event;

import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.recipe.transform.RecipeTransformManager;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = CreatesDelight.ID)
public class ForgeBusEventHandler {
    
    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {
        event.addListener(new RecipeTransformManager(event.getServerResources().getRecipeManager()));
    }
    
}
