package dev.limonblaze.createsdelight.common.block.entity;

import com.simibubi.create.AllSoundEvents;
import com.simibubi.create.content.contraptions.components.steam.SteamJetParticleData;
import com.simibubi.create.content.contraptions.fluids.tank.FluidTankTileEntity;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.VecHelper;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import dev.limonblaze.createsdelight.common.advancement.AdvancementBehaviourHelper;
import dev.limonblaze.createsdelight.common.advancement.AdvancementHolder;
import dev.limonblaze.createsdelight.common.block.SteamPotBlock;
import dev.limonblaze.createsdelight.common.menu.SteamPotContainerMenu;
import dev.limonblaze.createsdelight.common.recipe.SteamPotRecipe;
import dev.limonblaze.createsdelight.common.registry.CDMenus;
import dev.limonblaze.createsdelight.common.registry.CDRecipeTypes;
import dev.limonblaze.createsdelight.compat.create.steam.BoilerDataHelper;
import dev.limonblaze.createsdelight.data.server.Advancements;
import dev.limonblaze.createsdelight.util.LangUtils;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RecipeWrapper;
import vectorwing.farmersdelight.common.block.CookingPotBlock;
import vectorwing.farmersdelight.common.block.entity.inventory.CookingPotItemHandler;
import vectorwing.farmersdelight.common.mixin.accessor.RecipeManagerAccessor;
import vectorwing.farmersdelight.common.utility.ItemUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Optional;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class SteamPotBlockEntity extends SmartTileEntity implements MenuProvider, Nameable {
    public static final int MEAL_DISPLAY_SLOT = 6;
    public static final int CONTAINER_SLOT = 7;
    public static final int OUTPUT_SLOT = 8;
    public static final int INVENTORY_SIZE = 9;
    public WeakReference<FluidTankTileEntity> source;
    public LerpedFloat animation;
    protected final ItemStackHandler inventory = this.createItemHandler();
    protected final LazyOptional<IItemHandler> inputHandler = LazyOptional.of(() ->
        new CookingPotItemHandler(this.inventory, Direction.UP));
    protected final LazyOptional<IItemHandler> outputHandler = LazyOptional.of(() ->
        new CookingPotItemHandler(this.inventory, Direction.DOWN));
    protected final ContainerData cookingPotData;
    protected final Object2IntOpenHashMap<ResourceLocation> experienceTracker;
    @Nullable
    protected ResourceLocation lastRecipeID;
    protected boolean checkNewRecipe;
    protected int cookTime;
    protected int cookTimeTotal;
    protected ItemStack mealContainerStack;
    protected int steamPower;
    @Nullable
    private Component customName;
    
    public SteamPotBlockEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
        this.mealContainerStack = ItemStack.EMPTY;
        this.cookingPotData = this.createContainerData();
        this.experienceTracker = new Object2IntOpenHashMap<>();
        this.checkNewRecipe = true;
        this.source = new WeakReference<>(null);
        this.animation = LerpedFloat.linear();
    }
    
    private ItemStackHandler createItemHandler() {
        return new ItemStackHandler(INVENTORY_SIZE) {
            protected void onContentsChanged(int slot) {
                if(slot >= 0 && slot < MEAL_DISPLAY_SLOT) {
                    SteamPotBlockEntity.this.checkNewRecipe = true;
                }
                SteamPotBlockEntity.this.notifyUpdate();
            }
        };
    }
    
    private ContainerData createContainerData() {
        return new ContainerData() {
            public int get(int index) {
                return switch(index) {
                    case 0 -> SteamPotBlockEntity.this.cookTime;
                    case 1 -> SteamPotBlockEntity.this.cookTimeTotal;
                    default -> 0;
                };
            }
            
            public void set(int index, int value) {
                switch(index) {
                    case 0 -> SteamPotBlockEntity.this.cookTime = value;
                    case 1 -> SteamPotBlockEntity.this.cookTimeTotal = value;
                }
            }
            
            public int getCount() {
                return 2;
            }
        };
    }
    
    @Override
    protected void read(CompoundTag tag, boolean clientPacket) {
        super.read(tag, clientPacket);
        if(!clientPacket) {
            this.cookTime = tag.getInt("CookTime");
            this.cookTimeTotal = tag.getInt("CookTimeTotal");
            CompoundTag compoundRecipes = tag.getCompound("RecipesUsed");
            for(String key : compoundRecipes.getAllKeys()) {
                this.experienceTracker.put(new ResourceLocation(key), compoundRecipes.getInt(key));
            }
            if (tag.contains("CustomName", 8)) {
                this.customName = Component.Serializer.fromJson(tag.getString("CustomName"));
            }
        }
        this.mealContainerStack = ItemStack.of(tag.getCompound("Container"));
        this.inventory.deserializeNBT(tag.getCompound("Inventory"));
    }
    
    @Override
    protected void write(CompoundTag tag, boolean clientPacket) {
        if(!clientPacket) {
            tag.putInt("CookTime", this.cookTime);
            tag.putInt("CookTimeTotal", this.cookTimeTotal);
            CompoundTag compoundRecipes = new CompoundTag();
            this.experienceTracker.forEach((recipeId, craftedAmount) ->
                compoundRecipes.putInt(recipeId.toString(), craftedAmount)
            );
            tag.put("RecipesUsed", compoundRecipes);
            if(this.customName != null) {
                tag.putString("CustomName", Component.Serializer.toJson(this.customName));
            }
        }
        tag.put("Container", this.mealContainerStack.serializeNBT());
        tag.put("Inventory", this.inventory.serializeNBT());
        super.write(tag, clientPacket);
    }
    
    public CompoundTag writeToItem(CompoundTag tag) {
        if(this.getMeal().isEmpty()) return tag;
        
        if (this.customName != null) {
            tag.putString("CustomName", Component.Serializer.toJson(this.customName));
        }
    
        ItemStackHandler drops = new ItemStackHandler(INVENTORY_SIZE);
        for(int i = 0; i < INVENTORY_SIZE; ++i) {
            drops.setStackInSlot(i, i == MEAL_DISPLAY_SLOT ? this.inventory.getStackInSlot(i) : ItemStack.EMPTY);
        }
        
        tag.put("Container", this.mealContainerStack.serializeNBT());
        tag.put("Inventory", drops.serializeNBT());
        return tag;
    }
    
    @Override
    public void tick() {
        super.tick();
        steamPower = this.getNewSteamPower();
        boolean canCook = steamPower > 0;
        if(this.level.isClientSide) {
            animation.chase(
                canCook ? 1 : 0,
                canCook ? .5f : .4f,
                canCook ? LerpedFloat.Chaser.EXP : LerpedFloat.Chaser.LINEAR
            );
            animation.tickChaser();
            if(canCook) this.updateSoundAndParticles(this.level, steamPower);
        } else {
            this.updateCooking(this.level, this.worldPosition, this.getBlockState(), steamPower);
        }
    }
    
    @Override
    public void addBehaviours(List<TileEntityBehaviour> behaviours) {
        AdvancementBehaviour ab = new AdvancementBehaviour(this);
        ((AdvancementBehaviourHelper)ab).add(
            Advancements.COOKING_WITH_STEAM,
            Advancements.FULL_STEAM_COOKING
        );
        behaviours.add(ab);
    }
    
    public void award(AdvancementHolder advancement) {
        ((AdvancementBehaviourHelper)(this.getBehaviour(AdvancementBehaviour.TYPE))).add(advancement);
    }
    
    private int getNewSteamPower() {
        if(this.isVirtual()) return 1;
        FluidTankTileEntity tank = this.updateTank();
        if(tank == null) return 0;
        return Mth.floor(Mth.sqrt(((BoilerDataHelper)tank.boiler).getBoilerLevelForSteamPot(tank.getTotalTankSize())));
    }
    
    public int getSteamPower() {
        return this.steamPower;
    }
    
    public FluidTankTileEntity updateTank() {
        FluidTankTileEntity tank = source.get();
        if(tank == null || tank.isRemoved()) {
            if(tank != null)
                source = new WeakReference<>(null);
            Direction facing = SteamPotBlock.getAttachedDirection(getBlockState());
            BlockEntity be = level.getBlockEntity(worldPosition.relative(facing));
            if(be instanceof FluidTankTileEntity tankTe)
                source = new WeakReference<>(tank = tankTe);
        }
        if(tank == null)
            return null;
        return tank.getControllerTE();
    }
    
    public void updateCooking(Level level, BlockPos pos, BlockState state, int steamPower) {
        boolean didInventoryChange = false;
        if(steamPower > 0 && this.hasInput()) {
            this.award(Advancements.COOKING_WITH_STEAM);
            if(steamPower >= 4) this.award(Advancements.FULL_STEAM_COOKING);
            Optional<SteamPotRecipe> recipe = this.getMatchingRecipe(new RecipeWrapper(this.inventory));
            if(recipe.isPresent() && this.canCook(recipe.get())) {
                didInventoryChange = this.processCooking(recipe.get(), steamPower);
            } else {
                this.cookTime = 0;
            }
        } else if(this.cookTime > 0) {
            this.cookTime = Mth.clamp(this.cookTime - 2, 0, this.cookTimeTotal);
        }
        
        ItemStack mealStack = this.getMeal();
        if(!mealStack.isEmpty()) {
            if(!this.doesMealHaveContainer(mealStack)) {
                this.moveMealToOutput();
                didInventoryChange = true;
            } else if(!this.inventory.getStackInSlot(CONTAINER_SLOT).isEmpty()) {
                this.useStoredContainersOnMeal();
                didInventoryChange = true;
            }
        }
        
        if(didInventoryChange) {
            this.notifyUpdate();
        }
    }
    
    public void updateSoundAndParticles(Level level, int steamPower) {
        if(level.getGameTime() * steamPower % 96 == 0) {
            Direction facing = getBlockState().getOptionalValue(SteamPotBlock.FACING).orElse(Direction.SOUTH);
            float angle = 180 + AngleHelper.horizontalAngle(facing);
            Vec3 sizeOffset = VecHelper.rotate(new Vec3(0, -0.3F, 0.125F), angle, Direction.Axis.Y);
            Vec3 offset = VecHelper.rotate(new Vec3(0, 1, 0.75F), angle, Direction.Axis.Y);
            Vec3 particlePos = offset
                .scale(.45F)
                .add(sizeOffset)
                .add(Vec3.atCenterOf(worldPosition));
            Vec3 particleSpeed = offset.subtract(Vec3.atLowerCornerOf(facing.getNormal())
                .scale(.75F));
            level.addParticle(new SteamJetParticleData(1),
                particlePos.x, particlePos.y, particlePos.z,
                particleSpeed.x, particleSpeed.y, particleSpeed.z
            );
            FluidTankTileEntity source = this.source.get();
            if (source != null) {
                FluidTankTileEntity controller = source.getControllerTE();
                if(controller != null && controller.boiler != null) {
                    float volume = steamPower / (float) Math.max(2, ((BoilerDataHelper)controller.boiler).getAttachedSteamPots() / 6);
                    float pitch = 1.18F - level.random.nextFloat() * .25F;
                    level.playLocalSound(worldPosition.getX(), worldPosition.getY(), worldPosition.getZ(),
                        SoundEvents.CANDLE_EXTINGUISH, SoundSource.BLOCKS, volume, pitch, false);
                    AllSoundEvents.STEAM.playAt(level, worldPosition, volume * 0.05F, .8F, false);
                }
            }
        }
    }
    
    private Optional<SteamPotRecipe> getMatchingRecipe(RecipeWrapper inventoryWrapper) {
        if(this.level == null) return Optional.empty();
        if(this.lastRecipeID == null) {
            if(this.checkNewRecipe) {
                Optional<SteamPotRecipe> recipe = this.level.getRecipeManager().getRecipeFor(CDRecipeTypes.STEAM_POT.getType(), inventoryWrapper, this.level);
                if(recipe.isPresent()) {
                    this.lastRecipeID = recipe.get().getId();
                    return recipe;
                }
            }
        }
        Recipe<RecipeWrapper> recipe = ((RecipeManagerAccessor)this.level.getRecipeManager()).getRecipeMap(CDRecipeTypes.STEAM_POT.<RecipeType<Recipe<RecipeWrapper>>>getType()).get(this.lastRecipeID);
        if(recipe instanceof SteamPotRecipe) {
            if(recipe.matches(inventoryWrapper, this.level)) {
                return Optional.of((SteamPotRecipe) recipe);
            }
            if(recipe.getResultItem().sameItem(this.getMeal())) {
                return Optional.empty();
            }
        }
        this.checkNewRecipe = false;
        return Optional.empty();
    }
    
    public ItemStack getContainer() {
        return !this.mealContainerStack.isEmpty() ? this.mealContainerStack : this.getMeal().getContainerItem();
    }
    
    private boolean hasInput() {
        for(int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
            if(!this.inventory.getStackInSlot(i).isEmpty()) {
                return true;
            }
        }
        
        return false;
    }
    
    protected boolean canCook(SteamPotRecipe recipe) {
        if(this.hasInput()) {
            ItemStack resultStack = recipe.getResultItem();
            if(resultStack.isEmpty()) {
                return false;
            } else {
                ItemStack storedMealStack = this.inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
                if(storedMealStack.isEmpty()) {
                    return true;
                } else if(!storedMealStack.sameItem(resultStack)) {
                    return false;
                } else if(storedMealStack.getCount() + resultStack.getCount() <= this.inventory.getSlotLimit(MEAL_DISPLAY_SLOT)) {
                    return true;
                } else {
                    return storedMealStack.getCount() + resultStack.getCount() <= resultStack.getMaxStackSize();
                }
            }
        } else {
            return false;
        }
    }
    
    private boolean processCooking(SteamPotRecipe recipe, int steamPower) {
        if(this.level == null) {
            return false;
        }
        this.cookTime += steamPower;
        this.cookTimeTotal = recipe.getCookTime();
        if(this.cookTime < this.cookTimeTotal) {
            return false;
        } else {
            this.cookTime = 0;
            this.mealContainerStack = recipe.getOutputContainer();
            ItemStack resultStack = recipe.getResultItem();
            ItemStack storedMealStack = this.inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
            if(storedMealStack.isEmpty()) {
                this.inventory.setStackInSlot(MEAL_DISPLAY_SLOT, resultStack.copy());
            } else if(storedMealStack.sameItem(resultStack)) {
                storedMealStack.grow(resultStack.getCount());
            }
        
            this.trackRecipeExperience(recipe);
        
            for(int i = 0; i < MEAL_DISPLAY_SLOT; ++i) {
                ItemStack slotStack = this.inventory.getStackInSlot(i);
                if(slotStack.hasContainerItem()) {
                    Direction direction = this.getBlockState().getValue(CookingPotBlock.FACING).getCounterClockWise();
                    double x = this.worldPosition.getX() + 0.5D + direction.getStepX() * 0.5D;
                    double y = this.worldPosition.getY() + 0.5D;
                    double z = this.worldPosition.getZ() + 0.5D + direction.getStepZ() * 0.5D;
                    ItemUtils.spawnItemEntity(this.level, this.inventory.getStackInSlot(i).getContainerItem(), x, y, z, direction.getStepX() * 0.08, 0.08, direction.getStepZ() * 0.08);
                }
            
                if(!slotStack.isEmpty()) {
                    slotStack.shrink(1);
                }
            }
        
            return true;
        }
    }
    
    public void trackRecipeExperience(@Nullable Recipe<?> recipe) {
        if(recipe != null) {
            ResourceLocation recipeID = recipe.getId();
            this.experienceTracker.addTo(recipeID, 1);
        }
    }
    
    public void clearUsedRecipes(Player player) {
        this.grantStoredRecipeExperience(player.level, player.position());
        this.experienceTracker.clear();
    }
    
    public void grantStoredRecipeExperience(Level world, Vec3 pos) {
        for(var entry : this.experienceTracker.object2IntEntrySet()) {
            var recipe = world.getRecipeManager().byKey(entry.getKey());
            if(recipe.isPresent() && recipe.get() instanceof SteamPotRecipe steamPotRecipe) {
                splitAndSpawnExperience(world, pos, entry.getIntValue(), steamPotRecipe.getExperience());
            }
        }
    }
    
    private static void splitAndSpawnExperience(Level world, Vec3 pos, int craftedAmount, float experience) {
        int expTotal = Mth.floor((float)craftedAmount * experience);
        float expFraction = Mth.frac((float)craftedAmount * experience);
        if(expFraction != 0.0F && Math.random() < (double)expFraction) {
            ++expTotal;
        }
        
        while(expTotal > 0) {
            int expValue = ExperienceOrb.getExperienceValue(expTotal);
            expTotal -= expValue;
            world.addFreshEntity(new ExperienceOrb(world, pos.x, pos.y, pos.z, expValue));
        }
    }
    
    public ItemStackHandler getInventory() {
        return this.inventory;
    }
    
    public ItemStack getMeal() {
        return this.inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
    }
    
    public NonNullList<ItemStack> getDroppableInventory() {
        NonNullList<ItemStack> drops = NonNullList.create();
        
        for(int i = 0; i < INVENTORY_SIZE; ++i) {
            if(i != MEAL_DISPLAY_SLOT) {
                drops.add(this.inventory.getStackInSlot(i));
            }
        }
        
        return drops;
    }
    
    private void moveMealToOutput() {
        ItemStack mealStack = this.inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
        ItemStack outputStack = this.inventory.getStackInSlot(OUTPUT_SLOT);
        int mealCount = Math.min(mealStack.getCount(), mealStack.getMaxStackSize() - outputStack.getCount());
        if(outputStack.isEmpty()) {
            this.inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
        } else if(outputStack.getItem() == mealStack.getItem()) {
            mealStack.shrink(mealCount);
            outputStack.grow(mealCount);
        }
        
    }
    
    private void useStoredContainersOnMeal() {
        ItemStack mealStack = this.inventory.getStackInSlot(MEAL_DISPLAY_SLOT);
        ItemStack containerInputStack = this.inventory.getStackInSlot(CONTAINER_SLOT);
        ItemStack outputStack = this.inventory.getStackInSlot(OUTPUT_SLOT);
        if(this.isContainerValid(containerInputStack) && outputStack.getCount() < outputStack.getMaxStackSize()) {
            int smallerStackCount = Math.min(mealStack.getCount(), containerInputStack.getCount());
            int mealCount = Math.min(smallerStackCount, mealStack.getMaxStackSize() - outputStack.getCount());
            if(outputStack.isEmpty()) {
                containerInputStack.shrink(mealCount);
                this.inventory.setStackInSlot(OUTPUT_SLOT, mealStack.split(mealCount));
            } else if(outputStack.getItem() == mealStack.getItem()) {
                mealStack.shrink(mealCount);
                containerInputStack.shrink(mealCount);
                outputStack.grow(mealCount);
            }
        }
    }
    
    public ItemStack useHeldItemOnMeal(ItemStack container) {
        if(this.isContainerValid(container) && !this.getMeal().isEmpty()) {
            container.shrink(1);
            return this.getMeal().split(1);
        } else {
            return ItemStack.EMPTY;
        }
    }
    
    private boolean doesMealHaveContainer(ItemStack meal) {
        return !this.mealContainerStack.isEmpty() || meal.hasContainerItem();
    }
    
    public boolean isContainerValid(ItemStack containerItem) {
        if(containerItem.isEmpty()) {
            return false;
        } else {
            return !this.mealContainerStack.isEmpty() ? this.mealContainerStack.sameItem(containerItem) : this.getMeal().getContainerItem().sameItem(containerItem);
        }
    }
    
    public Component getName() {
        return this.customName != null ? this.customName : LangUtils.translate("container").suffix("steam_pot").toComponent();
    }
    
    public Component getDisplayName() {
        return this.getName();
    }
    
    @Nullable
    public Component getCustomName() {
        return this.customName;
    }
    
    public void setCustomName(Component name) {
        this.customName = name;
    }
    
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new SteamPotContainerMenu(CDMenus.STEAM_POT.get(), id, inventory, this, this.cookingPotData);
    }
    
    @Nonnull
    public <T> LazyOptional<T> getCapability(Capability<T> cap, @Nullable Direction side) {
        if(cap.equals(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)) {
            return side != null && !side.equals(Direction.UP) ? this.outputHandler.cast() : this.inputHandler.cast();
        } else {
            return super.getCapability(cap, side);
        }
    }
    
}
