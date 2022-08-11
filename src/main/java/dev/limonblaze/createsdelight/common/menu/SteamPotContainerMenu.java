package dev.limonblaze.createsdelight.common.menu;

import com.mojang.datafixers.util.Pair;
import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;
import dev.limonblaze.createsdelight.common.menu.slot.SteamPotResultSlot;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.SlotItemHandler;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotContainer;
import vectorwing.farmersdelight.common.block.entity.container.CookingPotMealSlot;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Objects;

import static dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity.*;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SteamPotContainerMenu extends AbstractContainerMenu {
    public final SteamPotBlockEntity steamPot;
    public final ItemStackHandler inventory;
    private final ContainerData cookingPotData;
    private final ContainerLevelAccess containerLevelAccess;
    
    public SteamPotContainerMenu(MenuType<?> type, int windowId, Inventory playerInventory, SteamPotBlockEntity steamPot, ContainerData data) {
        super(type, windowId);
        this.steamPot = steamPot;
        this.inventory = steamPot.getInventory();
        this.cookingPotData = data;
        this.containerLevelAccess = ContainerLevelAccess.create(steamPot.getLevel(), steamPot.getBlockPos());
        int inputStartX = 30;
        int inputStartY = 17;
        int borderSlotSize = 18;
        int row, column;
        
        for(row = 0; row < 2; ++row) {
            for(column = 0; column < 3; ++column) {
                this.addSlot(new SlotItemHandler(this.inventory, row * 3 + column, inputStartX + column * borderSlotSize, inputStartY + row * borderSlotSize));
            }
        }
        
        this.addSlot(new CookingPotMealSlot(this.inventory, MEAL_DISPLAY_SLOT, 124, 26));
        this.addSlot(new SlotItemHandler(this.inventory, CONTAINER_SLOT, 92, 55) {
            @OnlyIn(Dist.CLIENT)
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(InventoryMenu.BLOCK_ATLAS, CookingPotContainer.EMPTY_CONTAINER_SLOT_BOWL);
            }
        });
        this.addSlot(new SteamPotResultSlot(playerInventory.player, steamPot, this.inventory, OUTPUT_SLOT, 124, 55));
    
        int inventoryStartX = 8;
        int inventoryStartY = 84;
        
        for(row = 0; row < 3; ++row) {
            for(column = 0; column < 9; ++column) {
                this.addSlot(new Slot(playerInventory, INVENTORY_SIZE + row * 9 + column, inventoryStartX + column * borderSlotSize, inventoryStartY + row * borderSlotSize));
            }
        }
        
        for(column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, inventoryStartX + column * borderSlotSize, 142));
        }
        
        this.addDataSlots(data);
    }
    
    public SteamPotContainerMenu(MenuType<?> type, int windowId, Inventory playerInventory, FriendlyByteBuf data) {
        this(type, windowId, playerInventory, getSteamPot(playerInventory, data), new SimpleContainerData(2));
    }
    
    private static SteamPotBlockEntity getSteamPot(Inventory playerInventory, FriendlyByteBuf data) {
        Objects.requireNonNull(playerInventory, "Inventory cannot be null");
        Objects.requireNonNull(data, "FriendlyByteBuf cannot be null");
        BlockPos pos = data.readBlockPos();
        BlockEntity be = playerInventory.player.level.getBlockEntity(pos);
        if(be instanceof SteamPotBlockEntity steamPot) {
            return steamPot;
        } else {
            throw new IllegalStateException("Expected SteamPotBlockEntity at " + pos + ", but found " + be + " instead");
        }
    }
    
    @Override
    public boolean stillValid(Player player) {
        return this.containerLevelAccess.evaluate(((level, pos) ->
            level.getBlockEntity(pos) instanceof SteamPotBlockEntity &&
            player.distanceToSqr(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D),
            true);
    }
    
    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        int endPlayerInv = INVENTORY_SIZE + 36;
        ItemStack stack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if(slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            stack = slotStack.copy();
            if(index == OUTPUT_SLOT) {
                if(!this.moveItemStackTo(slotStack, INVENTORY_SIZE, endPlayerInv, true)) {
                    return ItemStack.EMPTY;
                }
            } else if(index > OUTPUT_SLOT) {
                if(slotStack.getItem() == Items.BOWL && !this.moveItemStackTo(slotStack, CONTAINER_SLOT, OUTPUT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
                
                if(!this.moveItemStackTo(slotStack, 0, MEAL_DISPLAY_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
                
                if(!this.moveItemStackTo(slotStack, CONTAINER_SLOT, OUTPUT_SLOT, false)) {
                    return ItemStack.EMPTY;
                }
            } else if(!this.moveItemStackTo(slotStack, INVENTORY_SIZE, endPlayerInv, false)) {
                return ItemStack.EMPTY;
            }
            
            if(slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
            
            if(slotStack.getCount() == stack.getCount()) {
                return ItemStack.EMPTY;
            }
            
            slot.onTake(player, slotStack);
        }
        return stack;
    }
    
    @OnlyIn(Dist.CLIENT)
    public int getCookProgressionScaled() {
        int i = this.cookingPotData.get(0);
        int j = this.cookingPotData.get(1);
        return j != 0 && i != 0 ? i * 24 / j : 0;
    }
    
    @OnlyIn(Dist.CLIENT)
    public int getSteamPower() {
        return this.steamPot.getSteamPower();
    }
    
}
