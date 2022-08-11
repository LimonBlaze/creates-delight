package dev.limonblaze.createsdelight.core.mixin.minecraft.common;

import dev.limonblaze.createsdelight.common.registry.CDMobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;

@Mixin(FoodData.class)
public class FoodDataMixin {
    
    @Nullable
    private Player createsdelight$player;
    
    @Inject(method = "tick", at = @At("HEAD"))
    private void createsdelight$cachePlayer(Player player, CallbackInfo ci) {
        if(createsdelight$player == null) createsdelight$player = player;
    }
    
    @Inject(method = "needsFood", at = @At("RETURN"), cancellable = true)
    private void createsdelight$applyAppetizing(CallbackInfoReturnable<Boolean> cir) {
        if(createsdelight$player != null && createsdelight$player.hasEffect(CDMobEffects.APPETIZING.get())) {
            cir.setReturnValue(true);
        }
    }
    
}
