package dev.limonblaze.createsdelight.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import com.simibubi.create.compat.jei.category.DeployingCategory;
import com.simibubi.create.compat.jei.category.animations.AnimatedDeployer;
import com.simibubi.create.compat.jei.category.animations.AnimatedKinetics;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.contraptions.processing.ProcessingRecipe;
import com.simibubi.create.foundation.gui.AllGuiTextures;
import com.simibubi.create.foundation.gui.element.GuiGameElement;
import com.simibubi.create.foundation.ponder.ui.LayoutHelper;
import com.simibubi.create.foundation.utility.Lang;
import dev.limonblaze.createsdelight.common.recipe.ToolApplicationRecipe;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.ChatFormatting;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ParametersAreNonnullByDefault
public class ToolApplicationCategory extends CreateRecipeCategory<ToolApplicationRecipe> {
    
    private final AnimatedDeployer deployer = new AnimatedDeployer();
    
    public ToolApplicationCategory(Info<ToolApplicationRecipe> info) {
        super(info);
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, ToolApplicationRecipe recipe, IFocusGroup focuses) {
        builder.addSlot(RecipeIngredientRole.INPUT, 27, 51)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(recipe.getProcessedItem());
        
        builder.addSlot(RecipeIngredientRole.INPUT, 51, 5)
            .setBackground(getRenderedSlot(), -1, -1)
            .addIngredients(recipe.getRequiredHeldItem())
            .addTooltipCallback((view, tooltip) ->
                tooltip.add(1, Lang
                    .translate("recipe.deploying.not_consumed")
                    .component()
                    .withStyle(ChatFormatting.GOLD)
                )
            );
    
        layoutOutput(recipe).forEach(layoutEntry -> builder
            .addSlot(RecipeIngredientRole.OUTPUT, 139 + layoutEntry.posX() + 1, 54 + layoutEntry.posY() + 1)
            .setBackground(getRenderedSlot(layoutEntry.output()), -1, -1)
            .addItemStack(layoutEntry.output().getStack())
            .addTooltipCallback(addStochasticTooltip(layoutEntry.output()))
        );
    }
    
    @Override
    public void draw(ToolApplicationRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack matrixStack, double mouseX, double mouseY) {
        AllGuiTextures.JEI_SHADOW.render(matrixStack, 62, 57);
        AllGuiTextures.JEI_DOWN_ARROW.render(matrixStack, 126, 29);
        deployer.draw(matrixStack, getBackground().getWidth() / 2 - 13, 22);
    }
    
    private List<LayoutEntry> layoutOutput(ProcessingRecipe<?> recipe) {
        int size = recipe.getRollableResults().size();
        List<LayoutEntry> positions = new ArrayList<>(size);
        
        LayoutHelper layout = LayoutHelper.centeredHorizontal(size, 1, 18, 18, 1);
        for (ProcessingOutput result : recipe.getRollableResults()) {
            positions.add(new LayoutEntry(result, layout.getX(), layout.getY()));
            layout.next();
        }
        
        return positions;
    }
    
    record LayoutEntry(ProcessingOutput output, int posX, int posY) {}
    
}
