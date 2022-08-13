package dev.limonblaze.createsdelight.client.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.utility.Lang;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.menu.SteamPotContainerMenu;
import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.ParametersAreNonnullByDefault;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

@ParametersAreNonnullByDefault
public class SteamPotScreen extends AbstractContainerScreen<SteamPotContainerMenu> {
    private static final ResourceLocation BACKGROUND_TEXTURE = CreatesDelight.asResource("textures/gui/steam_pot.png");
    private static final Rectangle HEAT_ICON = new Rectangle(47, 55, 17, 15);
    private static final Rectangle PROGRESS_ARROW = new Rectangle(89, 25, 0, 17);
    
    public SteamPotScreen(SteamPotContainerMenu menu, Inventory playerInventory, Component title) {
        super(menu, playerInventory, title);
        this.leftPos = 0;
        this.topPos = 0;
        this.imageWidth = 176;
        this.imageHeight = 166;
        this.titleLabelX = 28;
    }
    
    public void render(PoseStack ms, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(ms);
        super.render(ms, mouseX, mouseY, partialTicks);
        this.renderMealDisplayTooltip(ms, mouseX, mouseY);
        this.renderHeatIndicatorTooltip(ms, mouseX, mouseY);
    }
    
    private void renderHeatIndicatorTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (this.isHovering(HEAT_ICON.x, HEAT_ICON.y, HEAT_ICON.width, HEAT_ICON.height, mouseX, mouseY)) {
            List<Component> tooltip = new ArrayList<>();
            int steamPower = this.menu.getSteamPower();
            LangUtils.Builder builder = LangUtils.translate("container").suffix("steam_pot");
            tooltip.add(steamPower > 0
                ? builder.suffix("steam_powered").toComponent().append(Lang.translateDirect("boiler.lvl", String.valueOf(steamPower)).withStyle(ChatFormatting.GREEN))
                : builder.suffix("not_powered").toComponent()
            );
            this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
        }
    }
    
    protected void renderMealDisplayTooltip(PoseStack ms, int mouseX, int mouseY) {
        if (this.minecraft != null && this.minecraft.player != null && this.menu.getCarried().isEmpty() && this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
            if (this.hoveredSlot.index == 6) {
                List<Component> tooltip = new ArrayList<>();
                ItemStack mealStack = this.hoveredSlot.getItem();
                tooltip.add(((MutableComponent)mealStack.getItem().getDescription()).withStyle(mealStack.getRarity().getStyleModifier()));
                ItemStack containerStack = this.menu.steamPot.getContainer();
                String container = !containerStack.isEmpty() ? containerStack.getItem().getDescription().getString() : "";
                tooltip.add(TextUtils.getTranslation("container.cooking_pot.served_on", container).withStyle(ChatFormatting.GRAY));
                this.renderComponentTooltip(ms, tooltip, mouseX, mouseY);
            } else {
                this.renderTooltip(ms, this.hoveredSlot.getItem(), mouseX, mouseY);
            }
        }
        
    }
    
    protected void renderLabels(PoseStack ms, int mouseX, int mouseY) {
        super.renderLabels(ms, mouseX, mouseY);
        this.font.draw(ms, this.playerInventoryTitle, 8.0F, (float)(this.imageHeight - 96 + 2), 4210752);
    }
    
    protected void renderBg(PoseStack ms, float partialTicks, int mouseX, int mouseY) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (this.minecraft != null) {
            RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
            this.blit(ms, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
            if (this.menu.getSteamPower() > 0) {
                this.blit(ms, this.leftPos + HEAT_ICON.x, this.topPos + HEAT_ICON.y, 176, 0, HEAT_ICON.width, HEAT_ICON.height);
            }
            
            int l = this.menu.getCookProgressionScaled();
            this.blit(ms, this.leftPos + PROGRESS_ARROW.x, this.topPos + PROGRESS_ARROW.y, 176, 15, l + 1, PROGRESS_ARROW.height);
        }
    }
    
}
