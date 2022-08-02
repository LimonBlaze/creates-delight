package dev.limonblaze.createsdelight.common.block;

import dev.limonblaze.createsdelight.common.registry.CreatesDelightEffects;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FlowingFluid;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class GreasyFluidBlock extends LiquidBlock {
    
    public GreasyFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties) {
        super(fluid, properties);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(entity instanceof LivingEntity living && (!living.hasEffect(CreatesDelightEffects.GREASY.get()) || living.tickCount % 100 == 0)) {
            living.addEffect(new MobEffectInstance(CreatesDelightEffects.GREASY.get(), 600));
        }
    }
    
}
