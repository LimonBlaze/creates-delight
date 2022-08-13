package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import dev.limonblaze.createsdelight.common.advancement.AdvancementBehaviourHelper;
import dev.limonblaze.createsdelight.data.server.Advancements;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(value = DeployerTileEntity.class, remap = false)
public class DeployerTileEntityMixin extends KineticTileEntity {
    
    public DeployerTileEntityMixin(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
        super(typeIn, pos, state);
    }
    
    @Inject(method = "addBehaviours", at = @At("TAIL"))
    private void createsdelight$addBehaviors(List<TileEntityBehaviour> behaviours, CallbackInfo ci) {
        for(TileEntityBehaviour behaviour : behaviours) {
            if (behaviour instanceof AdvancementBehaviour ab) {
                ((AdvancementBehaviourHelper)ab).add(Advancements.KNIVES_IN_HANDS);
                return;
            }
        }
        AdvancementBehaviour ab = new AdvancementBehaviour(this);
        ((AdvancementBehaviourHelper)ab).add(Advancements.KNIVES_IN_HANDS);
        behaviours.add(ab);
    }
    
}
