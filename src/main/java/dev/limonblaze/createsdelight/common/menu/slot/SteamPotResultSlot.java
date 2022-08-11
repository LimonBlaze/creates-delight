package dev.limonblaze.createsdelight.common.menu.slot;

import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class SteamPotResultSlot extends SlotItemHandler {
    public final SteamPotBlockEntity steamPot;
    private final Player player;
    private int removeCount;
    
    public SteamPotResultSlot(Player player, SteamPotBlockEntity steamPot, IItemHandler inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
        this.steamPot = steamPot;
        this.player = player;
    }
    
    public boolean mayPlace(ItemStack stack) {
        return false;
    }
    
    @Nonnull
    public ItemStack remove(int amount) {
        if (this.hasItem()) {
            this.removeCount += Math.min(amount, this.getItem().getCount());
        }
        return super.remove(amount);
    }
    
    public void onTake(Player thePlayer, ItemStack stack) {
        this.checkTakeAchievements(stack);
        super.onTake(thePlayer, stack);
    }
    
    protected void onQuickCraft(ItemStack stack, int amount) {
        this.removeCount += amount;
        this.checkTakeAchievements(stack);
    }
    
    protected void checkTakeAchievements(ItemStack stack) {
        stack.onCraftedBy(this.player.level, this.player, this.removeCount);
        if (!this.player.level.isClientSide) {
            this.steamPot.clearUsedRecipes(this.player);
        }
        this.removeCount = 0;
    }
    
}
