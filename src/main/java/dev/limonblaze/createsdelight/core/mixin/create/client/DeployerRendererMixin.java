package dev.limonblaze.createsdelight.core.mixin.create.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.components.deployer.DeployerRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import dev.limonblaze.createsdelight.core.mixin.create.access.DeployerTileEntityAccessor;
import net.minecraft.client.renderer.MultiBufferSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(DeployerRenderer.class)
public class DeployerRendererMixin {
    
    @ModifyVariable(
        method = "renderItem",
        at = @At(value = "INVOKE", target = "Lcom/simibubi/create/foundation/utility/AngleHelper;horizontalAngle(Lnet/minecraft/core/Direction;)F"),
        name = "punch",
        ordinal = 0,
        remap = false
    )
    private boolean createsdelight$uprightOnDeployer(boolean original, DeployerTileEntity te, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        return original || ((DeployerTileEntityAccessor) te).getHeldItem().is(CDTags.ItemTag.UPRIGHT_ON_DEPLOYER);
    }
    
}
