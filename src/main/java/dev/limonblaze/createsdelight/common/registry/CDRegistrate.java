package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.VirtualFluid;
import com.simibubi.create.foundation.data.CreateTileEntityBuilder;
import com.simibubi.create.foundation.data.VirtualFluidBuilder;
import com.tterrag.registrate.AbstractRegistrate;
import com.tterrag.registrate.builders.BlockEntityBuilder;
import com.tterrag.registrate.builders.FluidBuilder;
import com.tterrag.registrate.providers.ProviderType;
import com.tterrag.registrate.util.nullness.NonNullBiFunction;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.effect.EffectBuilder;
import dev.limonblaze.createsdelight.common.effect.SimpleMobEffect;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.tags.TagKey;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.FarmersDelight;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Predicate;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class CDRegistrate extends AbstractRegistrate<CDRegistrate> {
    
    public CDRegistrate(String modid) {
        super(modid);
    }
    
    public static NonNullSupplier<CDRegistrate> lazy(String modid) {
        return NonNullSupplier.lazy(() -> new CDRegistrate(modid)
            .registerEventListeners(FMLJavaModLoadingContext.get().getModEventBus())
        );
    }
    
    public <T extends BlockEntity> CreateTileEntityBuilder<T, CDRegistrate> tileEntity(
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
    
    public FluidBuilder<VirtualFluid, CDRegistrate> virtualFluid(String name) {
        return entry(name,
            c -> new VirtualFluidBuilder<>(self(), self(), name, c,
                CreatesDelight.asResource("fluid/" + name + "_still"),
                CreatesDelight.asResource("fluid/" + name + "_flow"),
                null, VirtualFluid::new
            ));
    }
    
    public FluidBuilder<ForgeFlowingFluid.Flowing, CDRegistrate> standardFluid(
        String name,
        NonNullBiFunction<FluidAttributes.Builder, Fluid, FluidAttributes> attributesFactory) {
        return fluid(
            name,
            CreatesDelight.asResource("fluid/" + name + "_still"),
            CreatesDelight.asResource("fluid/" + name + "_flow"),
            attributesFactory
        );
    }
    
    public <T extends MobEffect> EffectBuilder<T, CDRegistrate> effect(
        String name,
        NonNullSupplier<T> supplier) {
        return entry(name, callback -> new EffectBuilder<>(supplier, self(), self(), name, callback));
    }
    
    public EffectBuilder<SimpleMobEffect, CDRegistrate> simpleEffect(String name, MobEffectCategory category, int color) {
        return effect(name, () -> new SimpleMobEffect(category, color));
    }
    
    public void blockTagFromBlocks(TagKey<Block> tag, Block... blocks) {
        this.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.tag(tag).add(blocks));
    }
    
    @SafeVarargs
    public final void blockTagFromSuppliers(TagKey<Block> tag, Supplier<? extends Block>... blocks) {
        this.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> {
            for(var block : blocks) prov.tag(tag).add(block.get());
        });
    }
    
    @SafeVarargs
    public final void blockTagFromTags(TagKey<Block> tag, TagKey<Block>... tags) {
        this.addDataGenerator(ProviderType.BLOCK_TAGS, prov -> prov.tag(tag).addTags(tags));
    }
    
    public void itemTagFromItems(TagKey<Item> tag, Item... items) {
        this.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.tag(tag).add(items));
    }
    
    @SafeVarargs
    public final void itemTagFromSuppliers(TagKey<Item> tag, Supplier<? extends Item>... items) {
        this.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
            TagsProvider.TagAppender<Item> appender = prov.tag(tag);
            for(var item : items) appender.add(item.get());
        });
    }
    
    @SafeVarargs
    public final void itemTagFromTags(TagKey<Item> tag, TagKey<Item>... tags) {
        this.addDataGenerator(ProviderType.ITEM_TAGS, prov -> prov.tag(tag).addTags(tags));
    }
    
    public final void itemTagFromFilter(TagKey<Item> tag, Predicate<Item> filter) {
        this.addDataGenerator(ProviderType.ITEM_TAGS, prov -> {
            TagsProvider.TagAppender<Item> appender = prov.tag(tag);
            ForgeRegistries.ITEMS.getValues()
                .stream()
                .filter(filter)
                .forEach(item -> {
                    switch(item.getRegistryName().getNamespace()) {
                        case CreatesDelight.ID, Create.ID, FarmersDelight.MODID -> appender.add(item);
                        default -> appender.addOptional(item.getRegistryName());
                    }
                });
            }
        );
    }
    
}
