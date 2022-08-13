package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.content.contraptions.components.deployer.BeltDeployerCallbacks;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour;
import dev.limonblaze.createsdelight.common.advancement.AdvancementBehaviourHelper;
import dev.limonblaze.createsdelight.common.recipe.CuttingBoardDeployingRecipe;
import dev.limonblaze.createsdelight.data.server.Advancements;
import net.minecraft.world.item.crafting.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = BeltDeployerCallbacks.class, remap = false)
public class BeltDeployerCallbacksMixin {
    
    @Inject(method = "activate", at = @At(value = "INVOKE", target = "Lcom/simibubi/create/content/contraptions/components/deployer/BeltDeployerCallbacks;awardAdvancements(Lcom/simibubi/create/content/contraptions/components/deployer/DeployerTileEntity;Lnet/minecraft/world/item/ItemStack;)V"))
    private static void createsdelight$grantAdvancement(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler, DeployerTileEntity deployerTileEntity, Recipe<?> recipe, CallbackInfo ci) {
        if(recipe instanceof CuttingBoardDeployingRecipe) {
            ((AdvancementBehaviourHelper)deployerTileEntity.getBehaviour(AdvancementBehaviour.TYPE)).awardPlayer(Advancements.KNIVES_IN_HANDS);
        }
    }
    
}
