package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import dev.limonblaze.createsdelight.common.advancement.AdvancementBehaviourHelper;
import dev.limonblaze.createsdelight.compat.create.arm.CookingPotPoint;
import dev.limonblaze.createsdelight.data.server.Advancements;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = ArmTileEntity.class, remap = false)
public class ArmTileEntityMixin extends KineticTileEntity {
    
    public ArmTileEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
    
    @Inject(method = "addBehaviours", at = @At("TAIL"))
    private void createsdelight$addBehaviors(List<TileEntityBehaviour> behaviours, CallbackInfo ci) {
        for(TileEntityBehaviour behaviour : behaviours) {
            if (behaviour instanceof AdvancementBehaviour ab) {
                ((AdvancementBehaviourHelper)ab).add(Advancements.MEALS_IN_ARMS);
                return;
            }
        }
        AdvancementBehaviour ab = new AdvancementBehaviour(this);
        ((AdvancementBehaviourHelper)ab).add(Advancements.MEALS_IN_ARMS);
        behaviours.add(ab);
    }
    
    @ModifyVariable(method = "depositItem", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/block/mechanicalArm/ArmInteractionPoint;insert(Lnet/minecraft/world/item/ItemStack;Z)Lnet/minecraft/world/item/ItemStack;"), name = "armInteractionPoint")
    private ArmInteractionPoint createsdelight$triggerAdvancementOnInsert(ArmInteractionPoint point) {
        if(point instanceof CookingPotPoint) {
            ((AdvancementBehaviourHelper)this.getBehaviour(AdvancementBehaviour.TYPE)).awardPlayer(Advancements.MEALS_IN_ARMS);
        }
        return point;
    }
    
    @ModifyVariable(method = "collectItem", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/logistics/block/mechanicalArm/ArmInteractionPoint;extract(IIZ)Lnet/minecraft/world/item/ItemStack;"), name = "armInteractionPoint")
    private ArmInteractionPoint createsdelight$triggerAdvancementOnExtract(ArmInteractionPoint point) {
        if(point instanceof CookingPotPoint) {
            ((AdvancementBehaviourHelper)this.getBehaviour(AdvancementBehaviour.TYPE)).awardPlayer(Advancements.MEALS_IN_ARMS);
        }
        return point;
    }
    
}
