package dev.limonblaze.createsdelight.compat.jei.category;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.compat.jei.category.CreateRecipeCategory;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.recipe.SteamPotRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.drawable.IDrawableAnimated;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.NonNullList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SteamPotCategory extends CreateRecipeCategory<SteamPotRecipe> {
    public static final ResourceLocation BACKGROUND_TEXTURE = CreatesDelight.asResource("textures/gui/steam_pot.png");
    protected final IDrawable background;
    protected final IDrawable heatIndicator;
    protected final IDrawableAnimated arrow;
    
    public SteamPotCategory(Info<SteamPotRecipe> info, IGuiHelper helper) {
        super(info);
        this.background = helper.createDrawable(BACKGROUND_TEXTURE, 29, 16, 117, 57);
        this.heatIndicator = helper.createDrawable(BACKGROUND_TEXTURE, 176, 0, 17, 15);
        this.arrow = helper.drawableBuilder(BACKGROUND_TEXTURE, 176, 15, 24, 17).buildAnimated(200, IDrawableAnimated.StartDirection.LEFT, false);
    }
    
    @Override
    public IDrawable getBackground() {
        return background;
    }
    
    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, SteamPotRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        
        for(int row = 0; row < 2; ++row) {
            for(int column = 0; column < 3; ++column) {
                int inputIndex = row * 3 + column;
                if(inputIndex < ingredients.size()) {
                    builder.addSlot(RecipeIngredientRole.INPUT, column * 18 + 1, row * 18 + 1)
                        .addIngredients(ingredients.get(inputIndex));
                }
            }
        }
    
        ItemStack result = recipe.getResultItem();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 10)
                .addItemStack(result);
        builder.addSlot(RecipeIngredientRole.OUTPUT, 95, 39)
            .addItemStack(result);
    
        ItemStack container = recipe.getOutputContainer();
        if(!container.isEmpty()) {
            builder.addSlot(RecipeIngredientRole.INPUT, 63, 39)
                .addItemStack(container);
        }
    }
    
    @Override
    public void draw(SteamPotRecipe recipe, IRecipeSlotsView recipeSlotsView, PoseStack stack, double mouseX, double mouseY) {
        this.arrow.draw(stack, 60, 9);
        this.heatIndicator.draw(stack, 18, 39);
    }
    
}
