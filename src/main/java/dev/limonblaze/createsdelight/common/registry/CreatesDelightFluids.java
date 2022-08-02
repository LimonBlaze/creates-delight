package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.FluidEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.block.GreasyFluidBlock;
import dev.limonblaze.createsdelight.common.block.MoltenFluidBlock;
import dev.limonblaze.createsdelight.common.block.ThickFluidBlock;
import dev.limonblaze.createsdelight.common.item.DrinkableBucketItem;
import dev.limonblaze.createsdelight.common.item.EffectCuringBucketItem;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

@SuppressWarnings("unused")
public class CreatesDelightFluids {
    
    private static final CreatesDelightRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final CreatesDelightSections INGREDIENT_FLUID = REGISTRATE.enterSection(CreatesDelightSections.INGREDIENT_CONTAINER);
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> YOGURT = REGISTRATE.standardFluid("yogurt", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.yogurt", "Yogurt")
        .tag(TagHelper.Fluids.YOGURT)
        .attributes(b -> b
            .viscosity(1250)
            .density(1000))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .bucket((f, p) -> new EffectCuringBucketItem(f, p, true))
        .properties(p -> p.food(CreatesDelightFoods.YOGURT_BUCKET))
        .tag(TagHelper.Items.BUCKETS$YOGURT)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM = REGISTRATE.standardFluid("cream", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.cream", "Cream")
        .tag(TagHelper.Fluids.CREAM)
        .attributes(b -> b
            .viscosity(1250)
            .density(600))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(10F))
        .source(ForgeFlowingFluid.Source::new)
        .block(GreasyFluidBlock::new)
        .build()
        .bucket((f, p) -> new EffectCuringBucketItem(f, p, true))
        .properties(p -> p.food(CreatesDelightFoods.CREAM_BUCKET))
        .tag(TagHelper.Items.BUCKETS$CREAM)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SOUR_CREAM = REGISTRATE.standardFluid("sour_cream", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.sour_cream", "Sour Cream")
        .tag(TagHelper.Fluids.SOUR_CREAM)
        .attributes(b -> b
            .viscosity(1250)
            .density(600))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(10F))
        .source(ForgeFlowingFluid.Source::new)
        .bucket((f, p) -> new EffectCuringBucketItem(f, p, true))
        .properties(p -> p.food(CreatesDelightFoods.SOUR_CREAM_BUCKET))
        .tag(TagHelper.Items.BUCKETS$SOUR_CREAM)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHEESE = REGISTRATE.standardFluid("cheese", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.cheese", "Cheese")
        .tag(TagHelper.Fluids.CHEESE)
        .attributes(b -> b
            .viscosity(2000)
            .density(1200))
        .properties(p -> p
            .levelDecreasePerBlock(3)
            .tickRate(40)
            .slopeFindDistance(2)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MoltenFluidBlock(f, CreatesDelightBlocks.CHEESE_WHEEL::getDefaultState, p))
        .build()
        .bucket()
        .tag(TagHelper.Items.BUCKETS$CHEESE)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BUTTER = REGISTRATE.standardFluid("butter", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.butter", "Butter")
        .tag(TagHelper.Fluids.BUTTER)
        .attributes(b -> b
            .viscosity(800)
            .density(800))
        .properties(p -> p
            .levelDecreasePerBlock(1)
            .tickRate(10)
            .slopeFindDistance(7)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block(GreasyFluidBlock::new)
        .build()
        .bucket()
        .tag(TagHelper.Items.BUCKETS$BUTTER)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> TOMATO_SAUCE = REGISTRATE.standardFluid("tomato_sauce", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.tomato_sauce", "Tomato Sauce")
        .tag(TagHelper.Fluids.TOMATO_SAUCE)
        .attributes(b -> b
            .viscosity(2000)
            .density(1200))
        .properties(p -> p
            .levelDecreasePerBlock(3)
            .tickRate(40)
            .slopeFindDistance(2)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block(ThickFluidBlock::new)
        .build()
        .bucket((f, p) -> new DrinkableBucketItem(f, p, true))
        .properties(p -> p.food(CreatesDelightFoods.TOMATO_SAUCE_BUCKET))
        .tag(TagHelper.Items.BUCKETS$TOMATO_SAUCE)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> EGG = REGISTRATE.standardFluid("egg", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.egg", "Egg")
        .tag(TagHelper.Fluids.EGG)
        .attributes(b -> b
            .viscosity(1250)
            .density(1000))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .bucket((f, p) -> new DrinkableBucketItem(f, p, true))
        .properties(p -> p.food(CreatesDelightFoods.EGG_BUCKET))
        .tag(TagHelper.Items.BUCKETS$EGG)
        .build()
        .register();
    
    public static void register() {}
    
    private static class NoColorFluidAttributes extends FluidAttributes {
        
        private NoColorFluidAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }
        
        @Override
        public int getColor(BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }
        
    }
    
}
