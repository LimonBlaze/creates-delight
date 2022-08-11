package dev.limonblaze.createsdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.FluidState;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@ParametersAreNonnullByDefault
public class MoltenFluidBlock extends ThickFluidBlock {
    
    private final Supplier<BlockState> solid;
    
    public MoltenFluidBlock(Supplier<? extends FlowingFluid> fluid, Supplier<BlockState> solid, Properties properties) {
        super(fluid, properties);
        this.solid = solid;
    }
    
    @Override
    public boolean shouldSpreadLiquid(Level level, BlockPos pos, BlockState state) {
        if(level.getFluidState(pos).isSource()) {
            for(Direction direction : POSSIBLE_FLOW_DIRECTIONS) {
                BlockPos blockpos = pos.relative(direction.getOpposite());
                FluidState touched = level.getFluidState(blockpos);
                if(touched.is(FluidTags.WATER) && !touched.is(this.getFluid())) {
                    level.setBlockAndUpdate(pos, solid.get());
                    level.levelEvent(1501, pos, 0);
                    return false;
                }
            }
        }
        return true;
    }
    
}
