package dev.limonblaze.createsdelight.common.block;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.block.ITE;
import dev.limonblaze.createsdelight.common.block.entity.BlazeStoveBlockEntity;
import dev.limonblaze.createsdelight.common.registry.CreatesDelightBlockEntities;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.util.FakePlayer;
import vectorwing.farmersdelight.common.block.StoveBlock;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlazeStoveBlock extends HorizontalDirectionalBlock implements ITE<BlazeStoveBlockEntity>, IWrenchable {
    public static final EnumProperty<BlazeBurnerBlock.HeatLevel> HEAT_LEVEL = BlazeBurnerBlock.HEAT_LEVEL;
    public static final BooleanProperty LIT = BlockStateProperties.LIT;
    private static final VoxelShape SHAPE = Shapes.or(
        Block.box(2, 0, 2, 14, 5, 14),
        Block.box(1, 5, 1, 15, 15, 15),
        Block.box(0, 15, 0, 16, 16, 16)
    );
    
    public BlazeStoveBlock(Properties properties) {
        super(properties);
        registerDefaultState(defaultBlockState()
            .setValue(HEAT_LEVEL, BlazeBurnerBlock.HeatLevel.SMOULDERING)
            .setValue(LIT, false)
        );
        BoilerHeaters.registerHeater(this.delegate, this::getActiveHeat);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(HEAT_LEVEL, LIT, FACING);
    }
    
    @Override
    public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
        if(world.isClientSide) return;
        BlockEntity blockEntity = world.getBlockEntity(pos.above());
        if(blockEntity instanceof BasinTileEntity basin) {
            basin.notifyChangeOfContents();
        }
    }
    
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
                                 BlockHitResult blockRayTraceResult) {
        ItemStack heldItem = player.getItemInHand(hand);
        BlazeBurnerBlock.HeatLevel heat = state.getValue(HEAT_LEVEL);
        
        if(AllItems.GOGGLES.isIn(heldItem) && heat != BlazeBurnerBlock.HeatLevel.NONE)
            return onTileEntityUse(world, pos, stove -> {
                if(stove.getGoggles()) return InteractionResult.PASS;
                stove.setGoggles(true);
                stove.notifyUpdate();
                return InteractionResult.SUCCESS;
            });
        
        if(heldItem.isEmpty() && heat != BlazeBurnerBlock.HeatLevel.NONE)
            return onTileEntityUse(world, pos, stove -> {
                if(!stove.getGoggles()) return InteractionResult.PASS;
                stove.setGoggles(false);
                stove.notifyUpdate();
                return InteractionResult.SUCCESS;
            });
        
        boolean noConsume = player.isCreative();
        boolean forceOverflow = !(player instanceof FakePlayer);
        
        InteractionResultHolder<ItemStack> holder = tryInsert(world, pos, heldItem, noConsume, forceOverflow, false);
        ItemStack leftover = holder.getObject();
        if (!world.isClientSide && !noConsume && !leftover.isEmpty()) {
            if(heldItem.isEmpty()) {
                player.setItemInHand(hand, leftover);
            } else if (!player.getInventory().add(leftover)) {
                player.drop(leftover, false);
            }
        }
        
        return holder.getResult() == InteractionResult.SUCCESS ? InteractionResult.SUCCESS : InteractionResult.PASS;
    }
    
    public static InteractionResultHolder<ItemStack> tryInsert(Level level, BlockPos pos, ItemStack stack,
                                                               boolean noConsume, boolean forceOverflow, boolean simulate) {
        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof BlazeStoveBlockEntity stove))
            return InteractionResultHolder.fail(ItemStack.EMPTY);
        
        if(stove.isCreativeFuel(stack)) {
            if(!simulate) stove.applyCreativeFuel();
            return InteractionResultHolder.success(ItemStack.EMPTY);
        }
        
        if(!stove.addFuelOrIngredient(stack, forceOverflow, simulate))
            return InteractionResultHolder.fail(ItemStack.EMPTY);
        
        if(!noConsume) {
            ItemStack container = stack.hasContainerItem() ? stack.getContainerItem() : ItemStack.EMPTY;
            if (!level.isClientSide) {
                stack.shrink(1);
            }
            return InteractionResultHolder.success(container);
        }
        
        return InteractionResultHolder.success(ItemStack.EMPTY);
    }
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof BlazeStoveBlockEntity stove) {
                stove.dropAll();
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
    }
    
    @Override
    public void stepOn(Level level, BlockPos pos, BlockState state, Entity entity) {
        float damage = Math.max(0, this.getActiveHeat(level, pos, state));
        if(damage > 0
        && !entity.fireImmune()
        && entity instanceof LivingEntity living
        && !EnchantmentHelper.hasFrostWalker(living)) {
            entity.hurt(StoveBlock.STOVE_DAMAGE, 1.0F);
        }
        super.stepOn(level, pos, state, entity);
    }
    
    public float getActiveHeat(Level level, BlockPos pos, BlockState state) {
        BlazeBurnerBlock.HeatLevel value = state.getValue(BlazeBurnerBlock.HEAT_LEVEL);
        if(value == BlazeBurnerBlock.HeatLevel.NONE) {
            return -1;
        }
        if(value == BlazeBurnerBlock.HeatLevel.SEETHING) {
            return 2;
        }
        if(value.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING)) {
            return 1;
        }
        return 0;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter reader, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }
    
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    
    @Override
    public int getAnalogOutputSignal(BlockState state, Level level, BlockPos pos) {
        return Math.max(0, state.getValue(HEAT_LEVEL).ordinal() - 1);
    }
    
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void animateTick(BlockState state, Level world, BlockPos pos, Random random) {
        if (random.nextInt(10) != 0)
            return;
        if (!state.getValue(HEAT_LEVEL).isAtLeast(BlazeBurnerBlock.HeatLevel.SMOULDERING))
            return;
        world.playLocalSound((float)pos.getX() + 0.5F, (float)pos.getY() + 0.5F, (float)pos.getZ() + 0.5F,
            SoundEvents.CAMPFIRE_CRACKLE, SoundSource.BLOCKS,
            0.5F + random.nextFloat(), random.nextFloat() * 0.7F + 0.6F, false);
    }
    
    @Override
    public Class<BlazeStoveBlockEntity> getTileEntityClass() {
        return BlazeStoveBlockEntity.class;
    }
    
    @Override
    public BlockEntityType<? extends BlazeStoveBlockEntity> getTileEntityType() {
        return CreatesDelightBlockEntities.BLAZE_STOVE.get();
    }
    
}
