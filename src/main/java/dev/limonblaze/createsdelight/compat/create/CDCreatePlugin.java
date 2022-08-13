package dev.limonblaze.createsdelight.compat.create;

import com.simibubi.create.content.contraptions.components.deployer.DeployerRecipeSearchEvent;
import dev.limonblaze.createsdelight.common.recipe.CuttingBoardDeployingRecipe;
import dev.limonblaze.createsdelight.compat.create.arm.CDArmInteractionPointTypes;
import dev.limonblaze.createsdelight.compat.create.ponder.CDPonders;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.level.Level;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.registry.ModRecipeTypes;

public class CDCreatePlugin {
    
    public static void commonBootstrap(IEventBus mod, IEventBus forge) {
        CDArmInteractionPointTypes.register();
        forge.addListener(CDCreatePlugin::addDeployerRecipes);
    }
    
    public static void clientBootstrap(IEventBus mod, IEventBus forge) {
        mod.addListener(CDPonders::registerTags);
    }
    
    public static void addDeployerRecipes(DeployerRecipeSearchEvent event) {
        Level level = event.getTileEntity().getLevel();
        assert level != null;
        RecipeManager recipes = level.getRecipeManager();
        RecipeWrapper inventory = event.getInventory();
        event.addRecipe(() -> recipes
            .getAllRecipesFor(ModRecipeTypes.CUTTING.get())
            .stream()
            .filter(recipe -> recipe.matches(inventory, level) && recipe.getTool().test(inventory.getItem(1)))
            .findFirst()
            .map(CuttingBoardDeployingRecipe::fromCuttingBoard), 50
        );
    }
    
}
