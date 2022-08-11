package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.fluids.tank.BoilerHeaters;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.block.BlazeStoveBlock;
import dev.limonblaze.createsdelight.common.block.SteamPotBlock;
import dev.limonblaze.createsdelight.data.client.blockstate.PieBlockStateGen;
import dev.limonblaze.createsdelight.data.client.blockstate.SteamPotBlockStateGen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.material.MaterialColor;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.tag.ForgeTags;
import vectorwing.farmersdelight.common.tag.ModTags;

import java.util.StringJoiner;

public class CDBlocks {
    
    private static final CDRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final BlockEntry<SteamPotBlock> STEAM_POT = REGISTRATE
        .block("steam_pot", SteamPotBlock::new)
        .initialProperties(SharedProperties::copperMetal)
        .properties(p -> p.color(MaterialColor.GOLD))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE)
        .blockstate((ctx, prov) -> new SteamPotBlockStateGen().generate(ctx, prov))
        .item()
        .model(AssetLookup.customBlockItemModel("steam_pot", "item"))
        .build()
        .register();
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY).lightLevel(BlazeBurnerBlock::getLight))
        .tag(BlockTags.MINEABLE_WITH_PICKAXE, CDTags.BlockTag.FAN_TRANSPARENT, CDTags.BlockTag.FAN_HEATERS, ModTags.HEAT_SOURCES)
        .addLayer(() -> RenderType::cutoutMipped)
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .onRegister(block -> BoilerHeaters.registerHeater(block.delegate, BlazeStoveBlock::getActiveHeat))
        .item()
        .model(AssetLookup.customBlockItemModel("blaze_stove", "item"))
        .build()
        .register();
    
    public static final BlockEntry<Block> SALT_BAG = REGISTRATE
        .block("salt_bag", Block::new)
        .initialProperties(() -> Blocks.WHITE_WOOL)
        .transform(b -> b.tag(CDTags.BlockTag.STORAGE_BLOCKS$SALT))
        .blockstate((ctx, prov) -> {
            Block block = ctx.getEntry();
            prov.simpleBlock(block, prov.models()
                .withExistingParent("salt_bag", blockLocation(ModBlocks.RICE_BAG.get()))
                .texture("particle", blockLocation(block, "top"))
                .texture("up", blockLocation(block, "top"))
            );
        })
        .item()
        .transform(b -> b.tag(CDTags.ItemTag.STORAGE_BLOCKS$SALT))
        .build()
        .register();
    
    public static final BlockEntry<Block> SUGAR_BAG = REGISTRATE
        .block("sugar_bag", Block::new)
        .initialProperties(() -> Blocks.WHITE_WOOL)
        .transform(b -> b.tag(CDTags.BlockTag.STORAGE_BLOCKS$SUGAR))
        .blockstate((ctx, prov) -> {
            Block block = ctx.getEntry();
            prov.simpleBlock(block, prov.models()
                .withExistingParent("sugar_bag", blockLocation(ModBlocks.RICE_BAG.get()))
                .texture("particle", blockLocation(block, "top"))
                .texture("up", blockLocation(block, "top"))
            );
        })
        .item()
        .transform(b -> b.tag(CDTags.ItemTag.STORAGE_BLOCKS$SUGAR))
        .build()
        .register();
    
    public static final BlockEntry<PieBlock> CHEESE_WHEEL = REGISTRATE
        .block("cheese_wheel", p -> new PieBlock(p, CDItems.CHEESE::get))
        .initialProperties(() -> Blocks.CAKE)
        .transform(b -> b.tag(CDTags.BlockTag.STORAGE_BLOCKS$CHEESE))
        .blockstate((ctx, prov) -> PieBlockStateGen.custom(ctx.getId()).generate(ctx, prov))
        .item()
        .properties(p -> p.food(CDFoods.CHEESE_WHEEL))
        .tag(CDTags.ItemTag.STORAGE_BLOCKS$CHEESE)
        .tag(CDTags.ItemTag.UPRIGHT_ON_BELT)
        .defaultModel()
        .build()
        .register();
    
    public static void register() {
        CDTags.BlockTag.register(REGISTRATE);
    }
    
    public static ResourceLocation blockLocation(Block block, String... suffixs) {
        ResourceLocation loc = block.getRegistryName();
        StringJoiner joiner = new StringJoiner("_");
        joiner.add("block/" + loc.getPath());
        for(String suffix : suffixs) joiner.add(suffix);
        return new ResourceLocation(loc.getNamespace(), joiner.toString());
    }
    
}
