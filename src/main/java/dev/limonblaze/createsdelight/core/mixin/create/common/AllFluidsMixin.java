package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.AllFluids;
import com.simibubi.create.content.palettes.AllPaletteStoneTypes;
import dev.limonblaze.createsdelight.common.registry.CDFluids;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = AllFluids.class, remap = false)
public class AllFluidsMixin {
    
    @Inject(method = "getLavaInteraction", at = @At("TAIL"), cancellable = true)
    private static void createsdelight$additionalLavaInteraction(FluidState fluidState, CallbackInfoReturnable<BlockState> cir) {
        if(cir.getReturnValue() == null) {
            Fluid fluid = fluidState.getType();
            if(fluid.isSame(CDFluids.CHEESE.get())
             ||fluid.isSame(CDFluids.TOMATO_SAUCE.get())) {
                cir.setReturnValue(AllPaletteStoneTypes.SCORIA.getBaseBlock().get().defaultBlockState());
            } else if(fluid.isSame(CDFluids.YOGURT.get())
                   || fluid.isSame(CDFluids.CREAM.get())
                   || fluid.isSame(CDFluids.SOUR_CREAM.get())
            ) {
                cir.setReturnValue(Blocks.TUFF.defaultBlockState());
            } else if(fluid.isSame(CDFluids.EGG.get())
                   || fluid.isSame(CDFluids.BUTTER.get())) {
                cir.setReturnValue(AllPaletteStoneTypes.LIMESTONE.getBaseBlock().get().defaultBlockState());
            }
        }
    }
    
}
