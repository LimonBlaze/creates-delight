package dev.limonblaze.createsdelight.common.block;

import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.ITE;
import dev.limonblaze.createsdelight.common.block.entity.SteamPotBlockEntity;
import dev.limonblaze.createsdelight.common.registry.CDBlockEntities;
import net.minecraft.ChatFormatting;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.network.NetworkHooks;
import vectorwing.farmersdelight.common.utility.MathUtils;
import vectorwing.farmersdelight.common.utility.TextUtils;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SteamPotBlock extends Block implements ITE<SteamPotBlockEntity>, IWrenchable {
    
    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final BooleanProperty WALL = BooleanProperty.create("wall");
    
    public SteamPotBlock(Properties pProperties) {
        super(pProperties);
        registerDefaultState(defaultBlockState().setValue(WALL, false));
    }
    
    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        super.setPlacedBy(level, pos, state, placer, stack);
        AdvancementBehaviour.setPlacedBy(level, pos, placer);
        if(stack.hasCustomHoverName()) {
            if(level.getBlockEntity(pos) instanceof SteamPotBlockEntity steamPot) {
                steamPot.setCustomName(stack.getHoverName());
            }
        }
    }
    
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        Direction direction = getAttachedDirection(state);
        BlockState attachedState = level.getBlockState(pos.relative(direction));
        return direction == Direction.DOWN
            ? attachedState.isFaceSturdy(level, pos.below(), Direction.UP)
            : FluidTankBlock.isTank(attachedState);
    }
    
    @Override
    public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
        return originalState.getValue(WALL) ? originalState : IWrenchable.super.getRotatedBlockState(originalState, targetedFace);
    }
    
    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        super.createBlockStateDefinition(pBuilder.add(FACING, WALL));
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        Level level = context.getLevel();
        BlockPos clickedPos = context.getClickedPos();
        Direction face = context.getClickedFace();
        boolean wall = true;
        if(face.getAxis() == Direction.Axis.Y) {
            face = context.getHorizontalDirection().getOpposite();
            wall = false;
        }
        
        BlockState state = super.getStateForPlacement(context)
            .setValue(FACING, face.getOpposite())
            .setValue(WALL, wall);
        if(!canSurvive(state, level, clickedPos))
            return null;
        return state;
    }
    
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(hand);
        if(!level.isClientSide) {
            if(level.getBlockEntity(pos) instanceof SteamPotBlockEntity steamPot) {
                ItemStack servingStack = steamPot.useHeldItemOnMeal(heldStack);
                if(servingStack != ItemStack.EMPTY) {
                    if(!player.getInventory().add(servingStack)) {
                        player.drop(servingStack, false);
                    }
                    level.playSound(null, pos, SoundEvents.ARMOR_EQUIP_GENERIC, SoundSource.BLOCKS, 1.0F, 1.0F);
                } else {
                    NetworkHooks.openGui((ServerPlayer)player, steamPot, pos);
                }
            }
            return InteractionResult.SUCCESS;
        }
        return InteractionResult.CONSUME;
    }
    
    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        ItemStack stack = super.getCloneItemStack(level, pos, state);
        if(level.getBlockEntity(pos) instanceof SteamPotBlockEntity steamPot) {
            CompoundTag nbt = steamPot.writeToItem(new CompoundTag());
            if(!nbt.isEmpty()) {
                stack.addTagElement("BlockEntityTag", nbt);
            }
            if(steamPot.hasCustomName()) {
                stack.setHoverName(steamPot.getCustomName());
            }
        }
        return stack;
    }
    
    @Override
    public List<ItemStack> getDrops(BlockState state, LootContext.Builder builder) {
        List<ItemStack> items = super.getDrops(state, builder);
        if(builder.getOptionalParameter(LootContextParams.BLOCK_ENTITY) instanceof SteamPotBlockEntity steamPot) {
            for(ItemStack stack : items) {
                if(stack.is(this.asItem())) {
                    CompoundTag nbt = steamPot.writeToItem(new CompoundTag());
                    if(!nbt.isEmpty()) {
                        stack.addTagElement("BlockEntityTag", nbt);
                    }
                    if(steamPot.hasCustomName()) {
                        stack.setHoverName(steamPot.getCustomName());
                    }
                }
            }
        }
        return items;
    }
    
    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        FluidTankBlock.updateBoilerState(state, level, pos.relative(getAttachedDirection(state)));
    }
    
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if(state.getBlock() != newState.getBlock()) {
            if(level.getBlockEntity(pos) instanceof SteamPotBlockEntity steamPot) {
                Containers.dropContents(level, pos, steamPot.getDroppableInventory());
                steamPot.grantStoredRecipeExperience(level, Vec3.atCenterOf(pos));
                level.updateNeighbourForOutputSignal(pos, this);
            }
            super.onRemove(state, level, pos, newState, isMoving);
        }
        FluidTankBlock.updateBoilerState(state, level, pos.relative(getAttachedDirection(state)));
    }
    
    public BlockState updateShape(BlockState state, Direction pFacing, BlockState pFacingState, LevelAccessor level,
                                  BlockPos pCurrentPos, BlockPos pFacingPos) {
        return getAttachedDirection(state) == pFacing && !state.canSurvive(level, pCurrentPos)
            ? Blocks.AIR.defaultBlockState()
            : state;
    }
    
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext pContext) {
        if(!state.getValue(WALL))
            return AllShapes.WHISTLE_LARGE_FLOOR;
        Direction direction = state.getValue(FACING);
        return AllShapes.WHISTLE_LARGE_WALL.get(direction);
    }
    
    @Override
    public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
        return false;
    }
    
    @Override
    public boolean hasAnalogOutputSignal(BlockState state) {
        return true;
    }
    
    @Override
    public int getAnalogOutputSignal(BlockState blockState, Level level, BlockPos pos) {
        if(level.getBlockEntity(pos) instanceof SteamPotBlockEntity steamPot) {
            return MathUtils.calcRedstoneFromItemHandler(steamPot.getInventory());
        }
        return 0;
    }
    
    @Override
    public BlockState rotate(BlockState state, Rotation rotation) {
        return state.setValue(FACING, rotation.rotate(state.getValue(FACING)));
    }
    
    @Override
    public BlockState mirror(BlockState state, Mirror mirror) {
        return mirror == Mirror.NONE ? state : state.rotate(mirror.getRotation(state.getValue(FACING)));
    }
    
    @Override
    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack stack, @Nullable BlockGetter level, List<Component> tooltip, TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        CompoundTag nbt = stack.getTagElement("BlockEntityTag");
        if(nbt != null) {
            CompoundTag inventoryTag = nbt.getCompound("Inventory");
            if (inventoryTag.contains("Items", 9)) {
                ItemStackHandler handler = new ItemStackHandler();
                handler.deserializeNBT(inventoryTag);
                ItemStack mealStack = handler.getStackInSlot(6);
                if (!mealStack.isEmpty()) {
                    MutableComponent textServingsOf = mealStack.getCount() == 1 ? TextUtils.getTranslation("tooltip.cooking_pot.single_serving") : TextUtils.getTranslation("tooltip.cooking_pot.many_servings", mealStack.getCount());
                    tooltip.add(textServingsOf.withStyle(ChatFormatting.GRAY));
                    MutableComponent textMealName = mealStack.getHoverName().copy();
                    tooltip.add(textMealName.withStyle(mealStack.getRarity().color));
                }
            }
        } else {
            MutableComponent textEmpty = TextUtils.getTranslation("tooltip.cooking_pot.empty");
            tooltip.add(textEmpty.withStyle(ChatFormatting.GRAY));
        }
    }
    
    @Override
    public Class<SteamPotBlockEntity> getTileEntityClass() {
        return SteamPotBlockEntity.class;
    }
    
    @Override
    public BlockEntityType<? extends SteamPotBlockEntity> getTileEntityType() {
        return CDBlockEntities.STEAM_POT.get();
    }
    
    public static Direction getAttachedDirection(BlockState state) {
        return state.getValue(WALL) ? state.getValue(FACING) : Direction.DOWN;
    }
    
}