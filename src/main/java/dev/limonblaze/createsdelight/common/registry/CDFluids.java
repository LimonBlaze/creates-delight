package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.tterrag.registrate.util.entry.FluidEntry;
import dev.limonblaze.createsdelight.CreatesDelight;

public class CDFluids {
    
    private static final CDRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final FluidEntry<VirtualFluid> EGG = REGISTRATE.virtualFluid("egg")
        .lang(f -> "fluid.createsdelight.egg", "Egg")
        .tag(CDTags.FluidTag.EGG)
        .register();
    
    public static final FluidEntry<VirtualFluid> TOMATO_SAUCE = REGISTRATE.virtualFluid("tomato_sauce")
        .lang(f -> "fluid.createsdelight.tomato_sauce", "Tomato Sauce")
        .tag(CDTags.FluidTag.TOMATO_SAUCE)
        .register();
    
    public static final FluidEntry<VirtualFluid> YOGURT = REGISTRATE.virtualFluid("yogurt")
        .lang(f -> "fluid.createsdelight.yogurt", "Yogurt")
        .tag(CDTags.FluidTag.YOGURT)
        .register();
    
    public static final FluidEntry<VirtualFluid> CREAM = REGISTRATE.virtualFluid("cream")
        .lang(f -> "fluid.createsdelight.cream", "Cream")
        .tag(CDTags.FluidTag.CREAM)
        .register();
    
    public static final FluidEntry<VirtualFluid> SOUR_CREAM = REGISTRATE.virtualFluid("sour_cream")
        .lang(f -> "fluid.createsdelight.sour_cream", "Sour Cream")
        .tag(CDTags.FluidTag.SOUR_CREAM)
        .register();
    
    public static final FluidEntry<VirtualFluid> BUTTER = REGISTRATE.virtualFluid("butter")
        .lang(f -> "fluid.createsdelight.butter", "Butter")
        .tag(CDTags.FluidTag.BUTTER)
        .register();
    
    public static final FluidEntry<VirtualFluid> CHEESE = REGISTRATE.virtualFluid("cheese")
        .lang(f -> "fluid.createsdelight.cheese", "Cheese")
        .tag(CDTags.FluidTag.CHEESE)
        .register();
    
    public static void register() {}
    
}
