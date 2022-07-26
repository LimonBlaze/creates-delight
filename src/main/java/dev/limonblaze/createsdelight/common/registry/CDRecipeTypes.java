package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeSerializer;
import com.simibubi.create.foundation.utility.Lang;
import com.simibubi.create.foundation.utility.recipe.IRecipeTypeInfo;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.recipe.CuttingBoardDeployingRecipe;
import dev.limonblaze.createsdelight.common.recipe.SteamPotRecipe;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.event.RegistryEvent;

import java.util.function.Supplier;

public enum CDRecipeTypes implements IRecipeTypeInfo {
    CUTTING_BOARD_DEPLOYING(CuttingBoardDeployingRecipe::new),
    STEAM_POT(SteamPotRecipe.Serializer::new);
    
    private final ResourceLocation id;
    private final Supplier<RecipeSerializer<?>> serializerSupplier;
    private final Supplier<RecipeType<?>> typeSupplier;
    private RecipeSerializer<?> serializer;
    private RecipeType<?> type;
    
    CDRecipeTypes(Supplier<RecipeSerializer<?>> serializerSupplier) {
        this.id = CreatesDelight.asResource(Lang.asId(name()));
        this.serializerSupplier = serializerSupplier;
        this.typeSupplier = () -> simpleType(id);
    }
    
    CDRecipeTypes(ProcessingRecipeBuilder.ProcessingRecipeFactory<?> processingFactory) {
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
    
    public static void register(RegistryEvent.Register<RecipeSerializer<?>> event) {
        for(var r : values()) {
            r.type = r.typeSupplier.get();
            r.serializer = r.serializerSupplier.get().setRegistryName(r.id);
            event.getRegistry().register(r.serializer);
        }
    }
    
}
