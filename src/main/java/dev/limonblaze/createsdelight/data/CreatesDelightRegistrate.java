package dev.limonblaze.createsdelight.data;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

import java.util.function.BiFunction;

public class CreatesDelightRegistrate extends CreateRegistrate {
    
    public CreatesDelightRegistrate(String modid) {
        super(modid);
    }
    
    /**
     * Override all hardcoded reference to {@linkplain Create#asResource(String)}
     */
    @Override
    public <T extends ForgeFlowingFluid> FluidBuilder<T, CreateRegistrate> virtualFluid(
        String name,
        BiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory,
        NonNullFunction<ForgeFlowingFluid.Properties, T> factory
    ) {
        return entry(name,
            c -> new VirtualFluidBuilder<>(self(), self(), name, c,
                CreatesDelight.asResource("fluid/" + name + "_still"),
                CreatesDelight.asResource("fluid/" + name + "_flow"),
                attributesFactory, factory
            ));
    }
    
    @Override
    public FluidBuilder<VirtualFluid, CreateRegistrate> virtualFluid(String name) {
        return entry(name,
            c -> new VirtualFluidBuilder<>(self(), self(), name, c,
                CreatesDelight.asResource("fluid/" + name + "_still"),
                CreatesDelight.asResource("fluid/" + name + "_flow"),
                null, VirtualFluid::new
            ));
    }
    
    @Override
    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardFluid(String name) {
        return fluid(name,
            CreatesDelight.asResource("fluid/" + name + "_still"),
            CreatesDelight.asResource("fluid/" + name + "_flow")
        );
    }
    
    @Override
    public FluidBuilder<ForgeFlowingFluid.Flowing, CreateRegistrate> standardFluid(
        String name,
        NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory
    ) {
        return fluid(name,
            CreatesDelight.asResource("fluid/" + name + "_still"),
            CreatesDelight.asResource("fluid/" + name + "_flow"),
            attributesFactory
        );
    }
    
}
