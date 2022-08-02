package dev.limonblaze.createsdelight.common.registry;

import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerBlock;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerInteractionBehaviour;
import com.simibubi.create.content.contraptions.processing.burner.BlazeBurnerMovementBehaviour;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.block.BlazeStoveBlock;
import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import vectorwing.farmersdelight.common.registry.ModBlocks;

import java.util.StringJoiner;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;

@SuppressWarnings("unused")
public class CreatesDelightBlocks {
    
    private static final CreatesDelightRegistrate REGISTRATE =
        CreatesDelight.registrate().creativeModeTab(() -> CreatesDelight.CREATIVE_MODE_TAB);
    
    public static final CreatesDelightSections INGREDIENT_BLOCK = REGISTRATE.enterSection(CreatesDelightSections.INGREDIENT_STORAGE);
    
    public static final BlockEntry<BlazeStoveBlock> BLAZE_STOVE = REGISTRATE
        .block("blaze_stove", BlazeStoveBlock::new)
        .initialProperties(SharedProperties::softMetal)
        .properties(p -> p.color(MaterialColor.COLOR_GRAY))
        .properties(p -> p.lightLevel(BlazeBurnerBlock::getLight))
        .transform(b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE))
        .addLayer(() -> RenderType::cutoutMipped)
        .tag(TagHelper.Blocks.FAN_TRANSPARENT, TagHelper.Blocks.FAN_HEATERS, TagHelper.Blocks.HEAT_SOURCES)
        .blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
        .onRegister(movementBehaviour(new BlazeBurnerMovementBehaviour()))
        .onRegister(interactionBehaviour(new BlazeBurnerInteractionBehaviour()))
        .item()
        .model(AssetLookup.customBlockItemModel("blaze_stove", "item"))
        .build()
        .register();
    
    public static final BlockEntry<Block> SUGAR_BAG = REGISTRATE
        .block("sugar_bag", Block::new)
        .initialProperties(() -> Blocks.WHITE_WOOL)
        .transform(b -> b.tag(TagHelper.Blocks.STORAGE_BLOCKS$SUGAR))
        .blockstate((ctx, prov) -> {
            Block block = ctx.getEntry();
            prov.simpleBlock(block, prov.models()
                .withExistingParent("sugar_bag", blockLocation(ModBlocks.RICE_BAG.get()))
                .texture("particle", blockLocation(block, "top"))
                .texture("up", blockLocation(block, "top"))
            );
        })
        .item()
        .transform(b -> b.tag(TagHelper.Items.STORAGE_BLOCKS$SUGAR))
        .build()
        .register();
    
    public static final BlockEntry<Block> SALT_BAG = REGISTRATE
        .block("salt_bag", Block::new)
        .initialProperties(() -> Blocks.WHITE_WOOL)
        .transform(b -> b.tag(TagHelper.Blocks.STORAGE_BLOCKS$SALT))
        .blockstate((ctx, prov) -> {
            Block block = ctx.getEntry();
            prov.simpleBlock(block, prov.models()
                .withExistingParent("salt_bag", blockLocation(ModBlocks.RICE_BAG.get()))
                .texture("particle", blockLocation(block, "top"))
                .texture("up", blockLocation(block, "top"))
            );
        })
        .item()
        .transform(b -> b.tag(TagHelper.Items.STORAGE_BLOCKS$SALT))
        .build()
        .register();
    
    public static final CreatesDelightSections INGREDIENT = REGISTRATE.enterSection(CreatesDelightSections.INGREDIENT);
    
    public static final BlockEntry<SlabBlock> CHEESE_WHEEL = REGISTRATE
        .block("cheese_wheel", SlabBlock::new)
        .initialProperties(Material.CAKE, MaterialColor.SAND)
        .properties(p -> p.strength(0.5F).sound(SoundType.WOOD))
        .transform(b -> b.tag(TagHelper.Blocks.STORAGE_BLOCKS$CHEESE))
        .transform(b -> b.tag(BlockTags.MINEABLE_WITH_PICKAXE))
        .transform(b -> b.tag(BlockTags.MINEABLE_WITH_AXE))
        .loot((lt, block) -> lt.add(block, RegistrateBlockLootTables.createSlabItemTable(block)))
        .blockstate((ctx, prov) -> {
            SlabBlock block = ctx.getEntry();
            ResourceLocation side = blockLocation(block, "side");
            ResourceLocation top = blockLocation(block, "top");
            prov.slabBlock(block,
                prov.models().slab("cheese_wheel", side, top, top),
                prov.models().slabTop("cheese_wheel_top", side, top, top),
                prov.models().cubeColumn("cheese_wheel_double", side, top)
            );
        })
        .item()
        .properties(p -> p.food(CreatesDelightFoods.CHEESE_WHEEL))
        .transform(b -> b.tag(TagHelper.Items.STORAGE_BLOCKS$CHEESE))
        .defaultModel()
        .build()
        .register();
    
    public static void register() {}
    
    public static ResourceLocation blockLocation(Block block, String... suffixs) {
        ResourceLocation loc = block.getRegistryName();
        StringJoiner joiner = new StringJoiner("_");
        joiner.add("block/" + loc.getPath());
        for(String suffix : suffixs) joiner.add(suffix);
        return new ResourceLocation(loc.getNamespace(), joiner.toString());
    }
    
}
