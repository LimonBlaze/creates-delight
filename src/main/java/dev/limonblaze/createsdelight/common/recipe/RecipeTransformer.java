package dev.limonblaze.createsdelight.common.recipe;

import com.google.common.collect.ImmutableMap;
import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.processing.HeatCondition;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import dev.limonblaze.createsdelight.api.event.TransformRecipeEvent;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import vectorwing.farmersdelight.common.crafting.CookingPotRecipe;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;
import vectorwing.farmersdelight.common.crafting.ingredient.ChanceResult;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * A {@linkplain SimplePreparableReloadListener} which runs after {@linkplain RecipeManager},
 * transforms existing recipe to new ones and adds them to the {@linkplain RecipeManager}. <br/>
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber
public class RecipeTransformer extends SimplePreparableReloadListener<Void> {
    private static final Logger LOGGER = LoggerFactory.getLogger("Recipe Transformer");
    private final RecipeManager recipeManager;
    
    public RecipeTransformer(RecipeManager recipeManager) {
        this.recipeManager = recipeManager;
    }
    
    //priority has to be EventPriority.HIGHEST, ensuring other mods getting correct recipe data
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void register(AddReloadListenerEvent event) {
        event.addListener(new RecipeTransformer(event.getServerResources().getRecipeManager()));
    }
    
    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        return null;
    }
    
    @Override
    protected void apply(Void voidIn, ResourceManager resourceManager, ProfilerFiller profiler) {
        //nasty but useful way to modify vanilla's immutable maps
        var byType = new HashMap<>(recipeManager.recipes);
        var byName = new HashMap<>(recipeManager.byName);
        
        transform(byType, byName,
            ModRecipeTypes.CUTTING.get(),
            CrDlRecipeTypeInfo.TOOL_APPLICATION.getType(),
            RecipeTransformer::transformCuttingBoardRecipe
        );
        transform(byType, byName,
            ModRecipeTypes.COOKING.get(),
            AllRecipeTypes.MIXING.getType(),
            RecipeTransformer::transformCookingPotRecipe
        );
        
        recipeManager.recipes = ImmutableMap.copyOf(byType);
        recipeManager.byName = ImmutableMap.copyOf(byName);
    }
    
    @SuppressWarnings("unchecked")
    public static <O extends Recipe<?>, T extends Recipe<?>> void transform(
        Map<RecipeType<?>, Map<ResourceLocation, Recipe<?>>> byType,
        Map<ResourceLocation, Recipe<?>> byName,
        RecipeType<? super O> originalType, RecipeType<? super T> transformedType,
        Function<O, T> transformer
    ) {
        int count = 0;
        var recipes = new HashMap<>(byType.getOrDefault(transformedType, new HashMap<>()));
        for(var entry : byType.get(originalType).entrySet()) {
            O original = (O) entry.getValue();
            TransformRecipeEvent<T> event = new TransformRecipeEvent<>(original, transformer.apply(original));
            if(!MinecraftForge.EVENT_BUS.post(event)) {
                T transformed = event.getTransformed();
                recipes.put(transformed.getId(), transformed);
                byName.put(transformed.getId(), transformed);
                ++count;
            }
        }
        byType.put(transformedType, ImmutableMap.copyOf(recipes));
        LOGGER.info("Transformed {} recipe(s) of type [{}] into type [{}].", count, originalType, transformedType);
    }
    
    public static ToolApplicationRecipe transformCuttingBoardRecipe(CuttingBoardRecipe recipe) {
        var builder = new ProcessingRecipeBuilder<>(ToolApplicationRecipe::new,
            new ResourceLocation(recipe.getId().getNamespace(), recipe.getId().getPath() + "_using_deployer"))
            .require(recipe.getIngredients().get(0))
            .require(recipe.getTool())
            .toolNotConsumed();
        for(ChanceResult cr : recipe.getRollableResults()) {
            builder.output(cr.getChance(), cr.getStack());
        }
        return builder.build();
    }
    
    public static MixingRecipe transformCookingPotRecipe(CookingPotRecipe recipe) {
        ResourceLocation id = new ResourceLocation(recipe.getId().getNamespace(), recipe.getId().getPath() + "_mixing");
        ItemStack container = recipe.getOutputContainer();
        var builder = new ProcessingRecipeBuilder<>(MixingRecipe::new, id);
        for(var ingredient : recipe.getIngredients()) {
            builder.require(ingredient);
        }
        if(!container.isEmpty()) builder.require(container.getItem());
        return builder
            .requiresHeat(HeatCondition.HEATED)
            .output(recipe.getResultItem())
            .build();
    }
    
}
