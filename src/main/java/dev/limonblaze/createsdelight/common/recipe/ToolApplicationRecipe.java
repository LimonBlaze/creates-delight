package dev.limonblaze.createsdelight.common.recipe;

import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipeBuilder;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.crafting.CuttingBoardRecipe;

import javax.annotation.ParametersAreNonnullByDefault;

/**
 * A simple wrapper for {@link CuttingBoardRecipe}, used to make {@link CuttingBoardRecipe} automatically processed by deployers. <br/>
 * {@link CuttingBoardRecipe}'s {@link SoundEvent} property is not handled. <br/>
 */
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
@Mod.EventBusSubscriber
public class ToolApplicationRecipe extends ProcessingRecipe<RecipeWrapper> {
    
    public ToolApplicationRecipe(ProcessingRecipeBuilder.ProcessingRecipeParams params) {
        super(CrDlRecipeTypeInfo.TOOL_APPLICATION, params);
    }
    
    @SubscribeEvent
    public static void onDeployerRecipeSearch(DeployerRecipeSearchEvent event) {
        Level level = event.getTileEntity().getLevel();
        if(level != null) {
            event.addRecipe(() -> level
                .getRecipeManager()
                .getRecipeFor(
                    CrDlRecipeTypeInfo.TOOL_APPLICATION.getType(),
                    event.getInventory(), level
                ), 30
            );
        }
    }
    
    @Override
    protected int getMaxInputCount() {
        return 2;
    }
    
    @Override
    protected int getMaxOutputCount() {
        return 4;
    }
    
    public Ingredient getRequiredHeldItem() {
        if(ingredients.size() < 2)
            throw new IllegalStateException("Tool Application Recipe: " + id.toString() + " has no tool!");
        return ingredients.get(1);
    }
    
    public Ingredient getProcessedItem() {
        if(ingredients.isEmpty())
            throw new IllegalStateException("Tool Application Recipe: " + id.toString() + " has no ingredient!");
        return ingredients.get(0);
    }
    
    @Override
    public boolean matches(RecipeWrapper inv, Level level) {
        return ingredients.get(0).test(inv.getItem(0)) && ingredients.get(1).test(inv.getItem(1));
    }
    
}
