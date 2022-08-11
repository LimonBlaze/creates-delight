package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.FluidEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.block.MobEffectFluidBlock;
import dev.limonblaze.createsdelight.common.block.MoltenFluidBlock;
import dev.limonblaze.createsdelight.common.block.ThickFluidBlock;
import dev.limonblaze.createsdelight.common.item.DrinkableBucketItem;
import dev.limonblaze.createsdelight.common.item.CurativeBucketItem;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class CDFluids {
    
    private static final CDRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> YOGURT = REGISTRATE.standardFluid("yogurt", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.yogurt", "Yogurt")
        .tag(CDTags.FluidTag.YOGURT)
        .attributes(b -> b
            .viscosity(1250)
            .density(1000))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MobEffectFluidBlock(f, p,
            () -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 320)))
        .build()
        .bucket((f, p) -> new CurativeBucketItem(f, p, true))
        .properties(p -> p.food(CDFoods.YOGURT_BUCKET))
        .tag(CDTags.ItemTag.BUCKETS$YOGURT)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CREAM = REGISTRATE.standardFluid("cream", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.cream", "Cream")
        .tag(CDTags.FluidTag.CREAM)
        .attributes(b -> b
            .viscosity(1250)
            .density(750))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(10F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MobEffectFluidBlock(f, p,
            () -> new MobEffectInstance(CDMobEffects.GREASY.get(), 160),
            () -> new MobEffectInstance(MobEffects.SLOW_FALLING, 160)))
        .build()
        .bucket((f, p) -> new CurativeBucketItem(f, p, true))
        .properties(p -> p.food(CDFoods.CREAM_BUCKET))
        .tag(CDTags.ItemTag.BUCKETS$CREAM)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> SOUR_CREAM = REGISTRATE.standardFluid("sour_cream", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.sour_cream", "Sour Cream")
        .tag(CDTags.FluidTag.SOUR_CREAM)
        .attributes(b -> b
            .viscosity(1250)
            .density(750))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(10F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MobEffectFluidBlock(f, p,
            () -> new MobEffectInstance(CDMobEffects.GREASY.get(), 160),
            () -> new MobEffectInstance(CDMobEffects.APPETIZING.get(), 160)))
        .build()
        .bucket((f, p) -> new CurativeBucketItem(f, p, true))
        .properties(p -> p.food(CDFoods.SOUR_CREAM_BUCKET))
        .tag(CDTags.ItemTag.BUCKETS$SOUR_CREAM)
        .build()
        .register();
    
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> BUTTER = REGISTRATE.standardFluid("butter", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.butter", "Butter")
        .tag(CDTags.FluidTag.BUTTER)
        .attributes(b -> b
            .viscosity(800)
            .density(800)
            .temperature(360))
        .properties(p -> p
            .levelDecreasePerBlock(1)
            .tickRate(10)
            .slopeFindDistance(7)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MobEffectFluidBlock(f, p,
            () -> new MobEffectInstance(CDMobEffects.GREASY.get(), 320)))
        .build()
        .bucket()
        .tag(CDTags.ItemTag.BUCKETS$BUTTER)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> CHEESE = REGISTRATE.standardFluid("cheese", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.cheese", "Cheese")
        .tag(CDTags.FluidTag.CHEESE)
        .attributes(b -> b
            .viscosity(2000)
            .density(1250)
            .temperature(360))
        .properties(p -> p
            .levelDecreasePerBlock(3)
            .tickRate(40)
            .slopeFindDistance(2)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block((f, p) -> new MoltenFluidBlock(f, CDBlocks.CHEESE_WHEEL::getDefaultState, p))
        .build()
        .bucket()
        .tag(CDTags.ItemTag.BUCKETS$CHEESE)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> TOMATO_SAUCE = REGISTRATE.standardFluid("tomato_sauce", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.tomato_sauce", "Tomato Sauce")
        .tag(CDTags.FluidTag.TOMATO_SAUCE)
        .attributes(b -> b
            .viscosity(1500)
            .density(1250))
        .properties(p -> p
            .levelDecreasePerBlock(3)
            .tickRate(40)
            .slopeFindDistance(2)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .block(ThickFluidBlock::new)
        .build()
        .bucket((f, p) -> new DrinkableBucketItem(f, p, false))
        .properties(p -> p.food(CDFoods.TOMATO_SAUCE_BUCKET))
        .tag(CDTags.ItemTag.BUCKETS$TOMATO_SAUCE)
        .build()
        .register();
    
    public static final FluidEntry<ForgeFlowingFluid.Flowing> EGG = REGISTRATE.standardFluid("egg", NoColorFluidAttributes::new)
        .lang(f -> "fluid.createsdelight.egg", "Egg")
        .tag(CDTags.FluidTag.EGG)
        .attributes(b -> b
            .viscosity(1250)
            .density(1000))
        .properties(p -> p
            .levelDecreasePerBlock(2)
            .tickRate(20)
            .slopeFindDistance(3)
            .explosionResistance(100F))
        .source(ForgeFlowingFluid.Source::new)
        .bucket((f, p) -> new DrinkableBucketItem(f, p, false))
        .properties(p -> p.food(CDFoods.EGG_BUCKET))
        .tag(CDTags.ItemTag.BUCKETS$EGG)
        .build()
        .register();
    
    public static void register() {}
    
    public static class NoColorFluidAttributes extends FluidAttributes {
        
        private NoColorFluidAttributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }
        
        @Override
        public int getColor(BlockAndTintGetter world, BlockPos pos) {
            return 0x00ffffff;
        }
        
    }
    
}
