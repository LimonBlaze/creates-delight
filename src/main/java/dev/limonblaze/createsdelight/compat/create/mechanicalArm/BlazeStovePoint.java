package dev.limonblaze.createsdelight.compat.create.mechanicalArm;

import com.simibubi.create.content.logistics.block.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import dev.limonblaze.createsdelight.common.block.BlazeStoveBlock;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BlazeStovePoint extends AllArmInteractionPointTypes.DepositOnlyArmInteractionPoint {
    
    public BlazeStovePoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
        super(type, level, pos, state);
    }
    
    @Override
    public ItemStack insert(ItemStack stack, boolean simulate) {
        ItemStack input = stack.copy();
        InteractionResultHolder<ItemStack> holder = BlazeStoveBlock.tryInsert(level, pos, input, false, false, simulate);
        ItemStack remainder = holder.getObject();
        if(input.isEmpty()) {
            return remainder;
        } else {
            if(!simulate) Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), remainder);
            return input;
        }
    }
    
    public static class Type extends ArmInteractionPointType {
    
        public Type(ResourceLocation id) {
            super(id);
        }
    
        @Override
        public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
            return CDBlocks.BLAZE_STOVE.has(state);
        }
    
        @Nullable
        @Override
        public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
            return new BlazeStovePoint(this, level, pos, state);
        }
        
    }
    
}
