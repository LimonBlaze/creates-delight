package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateTileEntityBuilder;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.Builder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.util.entry.RegistryEntry;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.effect.EffectBuilder;
import dev.limonblaze.createsdelight.common.effect.SimpleMobEffect;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.IForgeRegistryEntry;
import net.minecraftforge.registries.RegistryObject;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.stream.Collectors;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CreatesDelightRegistrate extends AbstractRegistrate<CreatesDelightRegistrate> {
    
    public CreatesDelightRegistrate(String modid) {
        super(modid);
    }
    
    public static NonNullSupplier<CreatesDelightRegistrate> lazy(String modid) {
        return NonNullSupplier.lazy(() -> new CreatesDelightRegistrate(modid)
            .registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus())
        );
    }
    
    private static final Map<RegistryEntry<?>, CreatesDelightSections> ENTRY_SECTION_MAP = new IdentityHashMap<>();
    private CreatesDelightSections section;
    
    public CreatesDelightSections enterSection(CreatesDelightSections section) {
        this.section = section;
        return section;
    }
    
    public CreatesDelightSections currentSection() {
        return section;
    }
    
    @Override
    protected <R extends IForgeRegistryEntry<R>, T extends R> RegistryEntry<T> accept(
        String name,
        ResourceKey<? extends Registry<R>> type, Builder<R, T, ?, ?> builder, NonNullSupplier<? extends T> creator,
        NonNullFunction<RegistryObject<T>, ? extends RegistryEntry<T>> entryFactory
    ) {
        RegistryEntry<T> ret = super.accept(name, type, builder, creator, entryFactory);
        ENTRY_SECTION_MAP.put(ret, currentSection());
        return ret;
    }
    
    public CreatesDelightSections getSection(RegistryEntry<?> entry) {
        return ENTRY_SECTION_MAP.getOrDefault(entry, CreatesDelightSections.MISC);
    }
    
    public CreatesDelightSections getSection(IForgeRegistryEntry<?> entry) {
        return ENTRY_SECTION_MAP.entrySet()
            .stream()
            .filter(e -> e.getKey()
                .get() == entry)
            .map(Map.Entry::getValue)
            .findFirst()
            .orElse(CreatesDelightSections.MISC);
    }
    
    public <R extends IForgeRegistryEntry<R>> Collection<RegistryEntry<R>> getAll(
        CreatesDelightSections section,
        ResourceKey<? extends Registry<R>> registryType
    ) {
        return this.getAll(registryType)
            .stream()
            .filter(e -> getSection(e) == section)
            .collect(Collectors.toList());
    }
    
    public <T extends BlockEntity> CreateTileEntityBuilder<T, CreatesDelightRegistrate> tileEntity(
        String name, BlockEntityBuilder.BlockEntityFactory<T> factory
    ) {
        return this.tileEntity(this.self(), name, factory);
    }
    
    public <T extends BlockEntity, P> CreateTileEntityBuilder<T, P> tileEntity(
        P parent, String name, BlockEntityBuilder.BlockEntityFactory<T> factory
    ) {
        return (CreateTileEntityBuilder<T, P>) this.entry(name, callback ->
            CreateTileEntityBuilder.create(this, parent, name, callback, factory));
    }
    
    public FluidBuilder<VirtualFluid, CreatesDelightRegistrate> virtualFluid(String name) {
        return entry(name,
            c -> new VirtualFluidBuilder<>(self(), self(), name, c,
                CreatesDelight.asResource("fluid/" + name + "_still"),
                CreatesDelight.asResource("fluid/" + name + "_flow"),
                null, VirtualFluid::new
            ));
    }
    
    public FluidBuilder<ForgeFlowingFluid.Flowing, CreatesDelightRegistrate> standardFluid(
        String name,
        NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
        return fluid(
            name,
            CreatesDelight.asResource("fluid/" + name + "_still"),
            CreatesDelight.asResource("fluid/" + name + "_flow"),
            attributesFactory
        );
    }
    
    public <T extends MobEffect> EffectBuilder<T, CreatesDelightRegistrate> effect(
        String name,
        NonNullSupplier<T> supplier) {
        return entry(name, callback -> new EffectBuilder<>(supplier, self(), self(), name, callback));
    }
    
    public EffectBuilder<SimpleMobEffect, CreatesDelightRegistrate> simpleEffect(String name, MobEffectCategory category, int color) {
        return effect(name, () -> new SimpleMobEffect(category, color));
    }
    
}
