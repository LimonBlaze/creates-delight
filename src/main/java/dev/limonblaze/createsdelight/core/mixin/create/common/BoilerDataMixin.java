package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.content.contraptions.fluids.tank.BoilerData;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankTileEntity;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.compat.create.steam.BoilerDataHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = BoilerData.class, remap = false)
public abstract class BoilerDataMixin implements BoilerDataHelper {
    
    private int attachedSteamPots;
    
    @Shadow public abstract boolean isPassive(int boilerSize);
    
    @Shadow public int activeHeat;
    
    @Shadow protected abstract int getActualHeat(int boilerSize);
    
    @Override
    public int getAttachedSteamPots() {
        return this.attachedSteamPots;
    }
    
    @Override
    public int getBoilerLevelForSteamPot(int boilerSize) {
        if(isPassive(boilerSize) || this.activeHeat == 0) return 1;
        return Math.max(1, this.getActualHeat(boilerSize));
    }
    
    @Inject(method = "evaluate", at = @At("HEAD"))
    private void createsdelight$clearAttachedSteamPots(FluidTankTileEntity controller, CallbackInfoReturnable<Boolean> cir) {
        this.attachedSteamPots = 0;
    }
    
    @ModifyVariable(
        method = "evaluate",
        at = @At(value = "INVOKE", target = "Lcom/tterrag/registrate/util/entry/BlockEntry;has(Lnet/minecraft/world/level/block/state/BlockState;)Z", ordinal = 0),
        name = "attachedState"
    )//@ModifyVariable trick which prevents using @Inject#locals field
    private BlockState createsdelight$calculateAttachedSteamPots(BlockState state) {
        if(CDBlocks.STEAM_POT.has(state)) ++attachedSteamPots;
        return state;
    }
    
    @Inject(method = "write", at = @At("RETURN"))
    private void createsdelight$writeAttachedSteamPots(CallbackInfoReturnable<CompoundTag> cir) {
        cir.getReturnValue().putInt("SteamPots", attachedSteamPots);
    }
    
    @Inject(method = "read", at = @At("HEAD"))
    private void createsdelight$readAttachedSteamPots(CompoundTag nbt, int boilerSize, CallbackInfo ci) {
        attachedSteamPots = nbt.getInt("SteamPots");
    }
    
    @Inject(method = "isActive", at = @At("RETURN"), cancellable = true)
    private void createsdelight$addActiveCondition(CallbackInfoReturnable<Boolean> cir) {
        if(!cir.getReturnValue()) cir.setReturnValue(attachedSteamPots > 0);
    }
    
}
