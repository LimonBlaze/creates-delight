package dev.limonblaze.createsdelight.client.render.block;

import com.jozufozu.flywheel.core.PartialModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.foundation.render.CachedBufferer;
import com.simibubi.create.foundation.tileEntity.renderer.SafeTileEntityRenderer;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import dev.limonblaze.createsdelight.client.model.CDBlockPartials;
import dev.limonblaze.createsdelight.common.block.SteamPotBlock;
import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;

public class SteamPotRenderer extends SafeTileEntityRenderer<SteamPotBlockEntity> {
    
    public SteamPotRenderer(BlockEntityRendererProvider.Context context) {}
    
    @Override
    protected void renderSafe(SteamPotBlockEntity steamPot, float partialTicks, PoseStack ms, MultiBufferSource buffer, int light, int overlay) {
        BlockState blockState = steamPot.getBlockState();
        if(!(blockState.getBlock() instanceof SteamPotBlock))
            return;
        
        Direction direction = blockState.getValue(SteamPotBlock.FACING);
    
        PartialModel outer = CDBlockPartials.STEAM_POT_OUTER;
        
        float offset = steamPot.animation.getValue(partialTicks);
        if(steamPot.animation.getChaseTarget() > 0 && steamPot.animation.getValue() > 0.5F) {
            float wiggleProgress = (AnimationTickHolder.getTicks(steamPot.getLevel()) + partialTicks) / 8F;
            offset -= Math.sin(wiggleProgress * Mth.PI * steamPot.getSteamPower() / 2) / 8F;
        }
        
        CachedBufferer.partial(outer, blockState)
            .centre()
            .rotateY(AngleHelper.horizontalAngle(direction))
            .unCentre()
            .translate(0, offset / 8F, 0)
            .light(light)
            .renderInto(ms, buffer.getBuffer(RenderType.solid()));
    }
    
}
