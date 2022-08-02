package dev.limonblaze.createsdelight.core.mixin.create.access;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

import javax.annotation.Nullable;

@Mixin(BlazeBurnerRenderer.class)
public interface BlazeBurnerRendererAccessor {
    
    @Invoker(value = "renderShared", remap = false)
    static void callRenderShared(Level level, MultiBufferSource buffer, @Nullable PoseStack modelTransform, PoseStack ms,
                             BlockState blockState, float horizontalAngle, float animation,
                             boolean drawGoggles, boolean drawHat, int hashCode) {
        throw new AssertionError();
    }
    
}
