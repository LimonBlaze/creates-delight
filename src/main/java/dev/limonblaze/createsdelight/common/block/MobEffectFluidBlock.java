package dev.limonblaze.createsdelight.common.block;

import com.google.common.collect.ImmutableList;
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
import java.util.List;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class MobEffectFluidBlock extends LiquidBlock {
    
    protected final List<Supplier<MobEffectInstance>> effects;
    
    @SafeVarargs
    public MobEffectFluidBlock(Supplier<? extends FlowingFluid> fluid, Properties properties, Supplier<MobEffectInstance>... effects) {
        super(fluid, properties);
        this.effects = ImmutableList.copyOf(effects);
    }
    
    @SuppressWarnings("deprecation")
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if(entity instanceof LivingEntity living && living.tickCount % this.getFluid().getTickDelay(level) == 0) {
            effects.forEach(effect -> living.addEffect(effect.get()));
        }
    }
    
}
