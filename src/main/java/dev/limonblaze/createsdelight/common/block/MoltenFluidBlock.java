package dev.limonblaze.createsdelight.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

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
                if(level.getFluidState(blockpos).is(FluidTags.WATER)) {
                    level.setBlockAndUpdate(pos, solid.get());
                    level.levelEvent(1501, pos, 0);
                    return false;
                }
            }
        }
        return true;
    }
    
}
