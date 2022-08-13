package dev.limonblaze.createsdelight.compat.create.arm;

import com.simibubi.create.content.logistics.block.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.core.mixin.farmersdelight.access.SkilletBlockEntityAccessor;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.block.entity.SkilletBlockEntity;

import java.util.Optional;

public class SkilletPoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    
    public SkilletPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        BlockEntity blockEntity = level.getBlockEntity(pos);
        if (!(blockEntity instanceof SkilletBlockEntity skillet))
            return stack;
        ItemStack cookingStack = skillet.getStoredStack();
        if(cookingStack.isEmpty()) {
            Optional<CampfireCookingRecipe> recipe = ((SkilletBlockEntityAccessor)skillet).callGetMatchingRecipe(new SimpleContainer(stack));
            if(recipe.isEmpty()) return stack;
        }
        ItemStack remainder = stack.copy();
        if(simulate) return skillet.getInventory().insertItem(0, remainder, true);
        return skillet.addItemToCook(remainder, null);
    }
    
    public static class Type extends ArmInteractionPointType {
        
        public Type(ResourceLocation id) {
            super(id);
        }
        
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return level.getBlockEntity(pos) instanceof SkilletBlockEntity;
        }
        
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new SkilletPoint(this, level, pos, state);
        }
        
    }
    
}
