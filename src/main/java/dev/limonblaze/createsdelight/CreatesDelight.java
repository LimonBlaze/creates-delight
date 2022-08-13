package dev.limonblaze.createsdelight;

import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.limonblaze.createsdelight.client.model.CDBlockPartials;
import dev.limonblaze.createsdelight.common.registry.*;
import dev.limonblaze.createsdelight.compat.create.CDCreatePlugin;
import dev.limonblaze.createsdelight.compat.farmersdelight.CDFarmersDelightPlugin;
import dev.limonblaze.createsdelight.data.server.Advancements;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Mod(CreatesDelight.ID)
public class CreatesDelight {
    
    public static final String ID = "createsdelight";
    
    private static final Logger LOGGER = LoggerFactory.getLogger("Create's Delight");
    private static final NonNullSupplier<CDRegistrate> REGISTRATE = CDRegistrate.lazy(ID);
    
    public static final CDCreativeModeTab CREATIVE_MODE_TAB = new CDCreativeModeTab();
    
    public CreatesDelight() {
        //register game elements
        CDBlocks.register();
        CDBlockEntities.register();
        CDFluids.register();
        CDItems.register();
        CDMobEffects.register();
        CDMenus.register();
        //bootstrap
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        IEventBus forgeBus = MinecraftForge.EVENT_BUS;
        CreatesDelight.commonBootstrap(modBus, forgeBus);
        CDCreatePlugin.commonBootstrap(modBus, forgeBus);
        CDFarmersDelightPlugin.commonBootstrap(modBus, forgeBus);
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CreatesDelight.clientBootstrap(modBus, forgeBus));
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> CDCreatePlugin.clientBootstrap(modBus, forgeBus));
    }
    
    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }

    public static CDRegistrate registrate() {
        return REGISTRATE.get();
    }
    
    private static void commonBootstrap(IEventBus mod, IEventBus forge) {
        mod.addGenericListener(RecipeSerializer.class, CDRecipeTypes::register);
        mod.<FMLCommonSetupEvent>addListener(event -> event.enqueueWork(() -> {
            Advancements.register();
            CDTriggers.register();
        }));
    }
    
    private static void clientBootstrap(IEventBus mod, IEventBus forge) {
        CDBlockPartials.bootstrap();
    }
    
}
