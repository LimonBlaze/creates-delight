package dev.limonblaze.createsdelight.compat.create.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemHandlerHelper;
import org.jetbrains.annotations.Nullable;

public class SteamPotPoint extends ArmInteractionPoint {
    public static final int MEAL_DISPLAY_SLOT = 6;
    public static final int CONTAINER_SLOT = 7;
    public static final int OUTPUT_SLOT = 8;
    
    public SteamPotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof SteamPotBlockEntity steamPot)) return stack;
        IItemHandler inventory = getHandler();
        ItemStack currentContainer = inventory.getStackInSlot(CONTAINER_SLOT);
        ItemStack neededContainer = steamPot.getContainer();
        if(ItemHandlerHelper.canItemStacksStack(neededContainer, currentContainer) &&
            ItemHandlerHelper.canItemStacksStack(neededContainer, stack))
            return inventory.insertItem(CONTAINER_SLOT, stack, simulate);
        for(int slot = 0; slot < MEAL_DISPLAY_SLOT; ++slot) {
            ItemStack ingredient = inventory.getStackInSlot(slot);
            if(ItemHandlerHelper.canItemStacksStack(ingredient, stack))
                return inventory.insertItem(slot, stack, simulate);
        }
        return stack;
    }
    
    @Override
    public ItemStack extract(int slot, boolean simulate) {
        if(slot != OUTPUT_SLOT) return ItemStack.EMPTY;
        return super.extract(slot, simulate);
    }
    
    @Nullable
    @Override
    protected IItemHandler getHandler() {
        if (!cachedHandler.isPresent()) {
            BlockEntity be = level.getBlockEntity(pos);
            if(!(be instanceof SteamPotBlockEntity steamPot)) return null;
            cachedHandler = LazyOptional.of(steamPot::getInventory);
        }
        return cachedHandler.orElse(null);
    }
    
    public static class Type extends ArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof SteamPotBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new SteamPotPoint(this, level, pos, state);
        }
        
    }
    
}