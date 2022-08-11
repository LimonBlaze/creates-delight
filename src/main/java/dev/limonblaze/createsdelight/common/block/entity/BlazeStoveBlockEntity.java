package dev.limonblaze.createsdelight.common.block.entity;

import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerTileEntity;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CampfireCookingRecipe;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import org.apache.commons.lang3.tuple.Triple;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.StoveBlock;
import vectorwing.farmersdelight.common.block.entity.CookingPotBlockEntity;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.tag.ModTags;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import java.util.Optional;

public class BlazeStoveBlockEntity extends BlazeBurnerTileEntity {
    public static final Vec2[] ITEM_OFFSET_NS = new Vec2[9];
    public static final Vec2[] ITEM_OFFSET_WE = new Vec2[9];
    private static final VoxelShape GRILLING_AREA = Block.box(3.0D, 0.0D, 3.0D, 13.0D, 1.0D, 13.0D);
    private static final int INVENTORY_SLOT_COUNT = 9;
    private final ItemStackHandler inventory = this.createItemHandler();
    private final int[] cookingTimes = new int[INVENTORY_SLOT_COUNT];
    private final int[] cookingTimesTotal = new int[INVENTORY_SLOT_COUNT];
    private final ResourceLocation[] lastRecipeIDs = new ResourceLocation[INVENTORY_SLOT_COUNT];
    
    public BlazeStoveBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }
    
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(INVENTORY_SLOT_COUNT) {
            public int getSlotLimit(int slot) {
                return 1;
            }
        };
    }
    
    @Override
    public void tick() {
        super.tick();
        BlazeBurnerBlock.HeatLevel heat = getHeatLevelFromBlock();
        if(level.isClientSide) {
            if(!heat.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) {
                for(int i = 0; i < INVENTORY_SLOT_COUNT; ++i) {
                    if(this.level.random.nextFloat() < 0.2F && !this.inventory.getStackInSlot(i).isEmpty()) {
                        this.addSmokeAtItems(i, 3);
                    }
                }
            }
        } else {
            boolean blockedAbove = this.isBlockedAbove();
            switch(heat) {
                case SEETHING -> {
                    this.boostCookingPot(3);
                    this.burnIngredients(blockedAbove);
                }
                case KINDLED, FADING -> {
                    this.boostCookingPot(1);
                    this.processCooking(blockedAbove);
                }
                default -> this.decrementCooking(blockedAbove);
            }
            if(blockedAbove) {
                this.dropAll();
            }
        }
    }
    
    @Override
    protected void read(CompoundTag compound, boolean clientPacket) {
        if(!clientPacket) {
            int[] array;
            array = compound.getIntArray("CookingTimes");
            System.arraycopy(array, 0, this.cookingTimes, 0, Math.min(this.cookingTimesTotal.length, array.length));
            array = compound.getIntArray("CookingTotalTimes");
            System.arraycopy(array, 0, this.cookingTimesTotal, 0, Math.min(this.cookingTimesTotal.length, array.length));
        }
        this.inventory.deserializeNBT(compound.getCompound("Inventory"));
        super.read(compound, clientPacket);
    }
    
    @Override
    public void write(CompoundTag compound, boolean clientPacket) {
        if(!clientPacket) {
            compound.putIntArray("CookingTimes", this.cookingTimes);
            compound.putIntArray("CookingTotalTimes", this.cookingTimesTotal);
        }
        compound.put("Inventory", this.inventory.serializeNBT());
        super.write(compound, clientPacket);
    }
    
    @Override
    public boolean isValidBlockAbove() {
        BlockState blockState = this.level.getBlockState(worldPosition.above());
        return AllBlocks.BASIN.has(blockState)
            || blockState.getBlock() instanceof FluidTankBlock
            || blockState.getBlock() instanceof CookingPotBlock;
    }
    
    public boolean isBlockedAbove() {
        if (this.level != null) {
            BlockState above = this.level.getBlockState(this.worldPosition.above());
            return Shapes.joinIsNotEmpty(GRILLING_AREA, above.getShape(this.level, this.worldPosition.above()), BooleanOp.AND);
        } else {
            return false;
        }
    }
    
    public ItemStackHandler getInventory() {
        return this.inventory;
    }
    
    public int getNextEmptySlot() {
        for(int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack slotStack = this.inventory.getStackInSlot(i);
            if (slotStack.isEmpty()) {
                return i;
            }
        }
        return -1;
    }
    
    public LerpedFloat getHeadAngle() {
        return this.headAngle;
    }
    
    public LerpedFloat getHeadAnimation() {
        return this.headAnimation;
    }
    
    @Override
    protected void setBlockHeat(BlazeBurnerBlock.HeatLevel newHeat) {
        BlazeBurnerBlock.HeatLevel originalHeat = this.getHeatLevelFromBlock();
        if(originalHeat == newHeat)
            return;
        this.level.setBlockAndUpdate(worldPosition, getBlockState()
            .setValue(BlazeBurnerBlock.HEAT_LEVEL, newHeat)
        );
        notifyUpdate();
    }
    
    @Override
    public void applyCreativeFuel() {
        super.applyCreativeFuel();
    }
    
    public boolean addFuelOrIngredient(ItemStack inStack, boolean forceOverflow, boolean simulate) {
        if(tryUpdateFuel(inStack, forceOverflow, simulate)) return true;
        
        int slot = this.getNextEmptySlot();
        if(slot < 0) return false;
        
        BlazeBurnerBlock.HeatLevel heat = this.getHeatLevelFromBlock();
        if(!heat.isAtLeast(BlazeBurnerBlock.HeatLevel.FADING) || heat.isAtLeast(BlazeBurnerBlock.HeatLevel.SEETHING)) return false;
        
        if(this.isBlockedAbove()) return false;
        
        Optional<CampfireCookingRecipe> optional = this.findRecipe(new SimpleContainer(inStack), slot);
        if(optional.isPresent() && slot < this.inventory.getSlots()) {
            if(simulate) return true;
            
            CampfireCookingRecipe recipe = optional.get();
            ItemStack slotStack = this.inventory.getStackInSlot(slot);
            if(slotStack.isEmpty()) {
                ItemStack addStack = inStack.copy();
                addStack.setCount(1);
                this.cookingTimesTotal[slot] = recipe.getCookingTime();
                this.cookingTimes[slot] = 0;
                this.inventory.setStackInSlot(slot, addStack);
                this.lastRecipeIDs[slot] = recipe.getId();
                this.notifyUpdate();
                return true;
            }
        }
        
        return false;
    }
    
    public void dropAll() {
        if(!ItemUtils.isInventoryEmpty(this.inventory)) {
            ItemUtils.dropItems(this.level, getBlockPos(), this.inventory);
            this.notifyUpdate();
        }
    }
    
    protected void processCooking(boolean blockedAbove) {
        if(blockedAbove) return;
        boolean didInventoryChange = false;
        for(int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if(stack.isEmpty()) continue;
            
            ++this.cookingTimes[i];
            if(this.cookingTimes[i] >= this.cookingTimesTotal[i]) {
                Container inventoryWrapper = new SimpleContainer(stack);
                Optional<CampfireCookingRecipe> recipe = this.findRecipe(inventoryWrapper, i);
                if(recipe.isPresent()) {
                    ItemStack resultStack = recipe.get().getResultItem();
                    if (!resultStack.isEmpty()) {
                        ItemUtils.spawnItemEntity(this.level, resultStack.copy(),
                            this.worldPosition.getX() + 0.5D,
                            this.worldPosition.getY() + 1.0D,
                            this.worldPosition.getZ() + 0.5D,
                            this.level.random.nextGaussian() * 0.001D,
                            0.1D,
                            this.level.random.nextGaussian() * 0.001D);
                    }
                }
                
                this.inventory.setStackInSlot(i, ItemStack.EMPTY);
                didInventoryChange = true;
            }
        }
        
        if(didInventoryChange) {
            this.notifyUpdate();
        }
    }
    
    public Optional<CampfireCookingRecipe> findRecipe(Container recipeWrapper, int slot) {
        if(this.level == null) return Optional.empty();
        
        if(this.lastRecipeIDs[slot] != null) {
            Recipe<Container> recipe = ((RecipeManagerAccessor)this.level.getRecipeManager()).getRecipeMap(RecipeType.CAMPFIRE_COOKING).get(this.lastRecipeIDs[slot]);
            if(recipe instanceof CampfireCookingRecipe && recipe.matches(recipeWrapper, this.level)) {
                return Optional.of((CampfireCookingRecipe)recipe);
            }
        }
        
        return this.level.getRecipeManager().getRecipeFor(RecipeType.CAMPFIRE_COOKING, recipeWrapper, this.level);
    }
    
    protected void burnIngredients(boolean blockedAbove) {
        if(blockedAbove) return;
        boolean didInventoryChange = false;
        int totalUncooked = 0;
        for(int i = 0; i < this.inventory.getSlots(); ++i) {
            ItemStack stack = this.inventory.getStackInSlot(i);
            if(stack.isEmpty()) continue;
            totalUncooked += Math.max(0, this.cookingTimesTotal[i] - this.cookingTimes[i]);
            this.cookingTimesTotal[i] = this.cookingTimes[0] = 0;
            this.inventory.setStackInSlot(i, ItemStack.EMPTY);
            this.addSmokeAtItems(i, 5);
            didInventoryChange = true;
        }
        
        if(didInventoryChange) {
            this.remainingBurnTime += totalUncooked / INVENTORY_SLOT_COUNT;
            this.level.levelEvent(1501, getBlockPos(), 0);
            this.notifyUpdate();
        }
    }
    
    protected void decrementCooking(boolean blockedAbove) {
        if(blockedAbove) return;
        for(int i = 0; i < INVENTORY_SLOT_COUNT; ++i) {
            if(this.cookingTimes[i] > 0) {
                this.cookingTimes[i] = Mth.clamp(this.cookingTimes[i] - 2, 0, this.cookingTimesTotal[i]);
            }
        }
    }
    
    protected void boostCookingPot(int times) {
        Triple<BlockPos, BlockState, CookingPotBlockEntity> result = null;
        BlockPos posAbove = getBlockPos().above();
        BlockState stateAbove = this.level.getBlockState(posAbove);
        if(this.level.getBlockEntity(posAbove) instanceof CookingPotBlockEntity cookingPot) {
            result = Triple.of(posAbove, stateAbove, cookingPot);
        } else {
            if(stateAbove.is(ModTags.HEAT_CONDUCTORS)) {
                BlockPos posFurtherAbove = posAbove.above();
                if(this.level.getBlockEntity(posFurtherAbove) instanceof CookingPotBlockEntity cookingPot
                && !cookingPot.requiresDirectHeat()) {
                    result = Triple.of(posFurtherAbove, this.level.getBlockState(posFurtherAbove), cookingPot);
                }
            }
        }
        if(result == null) return;
        for(int i = 0; i < times; ++i) {
            CookingPotBlockEntity.cookingTick(this.level, result.getLeft(), result.getMiddle(), result.getRight());
        }
    }
    
    public void addSmokeAtItems(int slot, int amount) {
        Direction direction = this.getBlockState().getValue(StoveBlock.FACING);
        int directionIndex = direction.get2DDataValue();
        Vec2 offset = directionIndex % 2 == 0 ? ITEM_OFFSET_NS[slot] : ITEM_OFFSET_WE[slot];
        double x = this.worldPosition.getX() + 0.5D +
            (direction.getClockWise().getStepX() - direction.getStepX()) * offset.x;
        double y = this.worldPosition.getY() + 1.0D;
        double z = this.worldPosition.getZ() + 0.5D +
            (direction.getClockWise().getStepZ() - direction.getStepZ()) * offset.y;
        for(int k = 0; k < amount; ++k) {
            this.level.addParticle(ParticleTypes.SMOKE, x, y, z, 0.0D, 5.0E-4D, 0.0D);
        }
    }
    
    static {
        float scale = 5/16F;
        for(int i = 0; i < 9; ++i) {
            float x = (i % 3 - 1) * scale;
            float y = (i / 3 - 1) * scale;
            ITEM_OFFSET_NS[i] = new Vec2(x, y);
            ITEM_OFFSET_WE[i] = new Vec2(y, x);
        }
    }
    
}
