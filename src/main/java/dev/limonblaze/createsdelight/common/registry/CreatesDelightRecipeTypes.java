package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Optional;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = CreatesDelight.ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public enum CreatesDelightRecipeTypes implements IRecipeTypeInfo {
    ;
    
    private final ResourceLocation id;
    private final Supplier<RecipeSerializer<?>> serializerSupplier;
    private final Supplier<RecipeType<?>> typeSupplier;
    private RecipeSerializer<?> serializer;
    private RecipeType<?> type;
    
    CreatesDelightRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, Supplier<RecipeType<?>> typeSupplier) {
        this.id = CreatesDelight.asResource(Lang.asId(name()));
        this.serializerSupplier = serializerSupplier;
        this.typeSupplier = typeSupplier;
    }
    
    CreatesDelightRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier, RecipeType<?> existingType) {
        this(serializerSupplier, () -> existingType);
    }
    
    CreatesDelightRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        this.id = CreatesDelight.asResource(Lang.asId(name()));
        this.serializerSupplier = serializerSupplier;
        this.typeSupplier = () -> simpleType(id);
    }
    
    CreatesDelightRecipeTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
        this(processingSerializer(processingFactory));
    }
    
    @Override
    public ResourceLocation getId() {
        return id;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeSerializer<?>> T getSerializer() {
        return (T) serializer;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <T extends RecipeType<?>> T getType() {
        return (T) type;
    }
    
    private static Supplier<RecipeSerializer<?>> processingSerializer(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> factory) {
        return () -> new ProcessingRecipeSerializer<>(factory);
    }
    
    public static <T extends Recipe<?>> RecipeType<T> simpleType(ResourceLocation id) {
        String stringId = id.toString();
        return Registry.register(Registry.RECIPE_TYPE, id, new RecipeType<T>() {
            @Override
            public String toString() {
                return stringId;
            }
        });
    }
    
    public <C extends Container, T extends Recipe<C>> Optional<T> find(C inv, Level world) {
        return world.getRecipeManager()
            .getRecipeFor(getType(), inv, world);
    }
    
    @SubscribeEvent
    public static void register(RegistryEvent.Register<RecipeSerializer<?>> event) {
        for(var r : values()) {
            r.serializer = r.serializerSupplier.get();
            r.type = r.typeSupplier.get();
            r.serializer.setRegistryName(r.id);
            event.getRegistry()
                .register(r.serializer);
        }
    }
    
}
