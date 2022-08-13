package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.content.contraptions.components.deployer.ManualApplicationRecipe;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.data.server.Advancements;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = ManualApplicationRecipe.class, remap = false)
public class ManualApplicationRecipeMixin {
    
    @Inject(method = "awardAdvancements", at = @At("HEAD"), cancellable = true)
    private static void createsdelight$awardAdvancements(Player player, BlockState placed, CallbackInfo ci) {
        if(CDBlocks.BLAZE_STOVE.has(placed)) {
            Advancements.CHEF_BLAZE.awardTo(player);
            ci.cancel();
        }
    }
    
}
