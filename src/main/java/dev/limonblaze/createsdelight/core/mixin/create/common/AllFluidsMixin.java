package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AllFluids.class)
public class AllFluidsMixin {
    
    @Inject(method = "getLavaInteraction", at = @At("TAIL"), cancellable = true, remap = false)
    private static void createsdelight$additionalLavaInteraction(FluidState fluidState, CallbackInfoReturnable<BlockState> cir) {
        if(cir.getReturnValue() == null) {
            Fluid fluid = fluidState.getType();
            if(fluid.isSame(CreatesDelightFluids.CHEESE.get())) {
                cir.setReturnValue(AllPaletteStoneTypes.SCORIA.getBaseBlock().get().defaultBlockState());
            } else if(fluid.isSame(CreatesDelightFluids.CREAM.get())
                    ||fluid.isSame(CreatesDelightFluids.SOUR_CREAM.get())
                    ||fluid.isSame(CreatesDelightFluids.TOMATO_SAUCE.get())
            ) {
                cir.setReturnValue(Blocks.TUFF.defaultBlockState());
            } else if(fluid.isSame(CreatesDelightFluids.BUTTER.get())) {
                cir.setReturnValue(AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get().defaultBlockState());
            }
        }
    }
    
}
