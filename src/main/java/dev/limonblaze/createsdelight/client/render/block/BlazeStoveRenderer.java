package dev.limonblaze.createsdelight.client.render.block;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;
import dev.limonblaze.createsdelight.common.block.entity.BlazeStoveBlockEntity;
import dev.limonblaze.createsdelight.core.mixin.create.access.BlazeBurnerRendererAccessor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import vectorwing.farmersdelight.common.block.StoveBlock;

public class BlazeStoveRenderer extends SafeTileEntityRenderer<BlazeStoveBlockEntity> {
    
    public BlazeStoveRenderer(BlockEntityRendererProvider.Context context) {}
    
    @Override
    protected void renderSafe(BlazeStoveBlockEntity stove, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int light, int overlay) {
        renderItem(stove, poseStack, buffer, overlay);
        BlazeBurnerBlock.HeatLevel heatLevel = stove.getHeatLevelFromBlock();
        if(heatLevel == BlazeBurnerBlock.HeatLevel.NONE) return;
        renderBlaze(stove, partialTicks, poseStack, buffer);
    }
    
    protected void renderItem(BlazeStoveBlockEntity stove, PoseStack poseStack, MultiBufferSource buffer, int overlay) {
        Direction direction = stove.getBlockState().getValue(StoveBlock.FACING).getOpposite();
        ItemStackHandler inventory = stove.getInventory();
        int seed = (int)stove.getBlockPos().asLong();
        poseStack.pushPose();
        poseStack.translate(0.5D, 1.0125D, 0.5D);
        for(int i = 0; i < inventory.getSlots(); ++i) {
            ItemStack stack = inventory.getStackInSlot(i);
            if (!stack.isEmpty()) {
                poseStack.pushPose();
                float directionRot = -direction.toYRot();
                poseStack.mulPose(Vector3f.YP.rotationDegrees(directionRot));
                poseStack.mulPose(Vector3f.XP.rotationDegrees(90.0F));
                poseStack.translate(((i % 3) - 1) * 5D / 16D, ((i / 3) - 1) * 5D / 16D, 0.0D);
                poseStack.scale(0.25F, 0.25F, 0.25F);
                Minecraft.getInstance().getItemRenderer().renderStatic(
                    stack, ItemTransforms.TransformType.FIXED,
                    LightTexture.FULL_BRIGHT, overlay,
                    poseStack, buffer, seed + i
                );
                poseStack.popPose();
            }
        }
        poseStack.popPose();
    }
    
    protected void renderBlaze(BlazeStoveBlockEntity stove, float partialTicks, PoseStack poseStack, MultiBufferSource buffer) {
        float horizontalAngle = AngleHelper.rad(stove.getHeadAngle().getValue(partialTicks));
        Level level = stove.getLevel();
        int hashCode = stove.hashCode();
        float animation = stove.getHeadAnimation().getValue(partialTicks) * .175f;
        BlockState blockState = stove.getBlockState();
        boolean drawGoggles = stove.getGoggles();
        BlazeBurnerRendererAccessor.callRenderShared(level, buffer, null, poseStack, blockState, horizontalAngle, animation, drawGoggles, true, hashCode);
    }
    
}
