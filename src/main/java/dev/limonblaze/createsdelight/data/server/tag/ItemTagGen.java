package dev.limonblaze.createsdelight.data.server.tag;

import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;
import vectorwing.farmersdelight.common.tag.ForgeTags;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class ItemTagGen extends ItemTagsProvider {
    
    public ItemTagGen(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, modId, existingFileHelper);
    }
    
    protected void addTags() {
        addForgeTags();
        addCreateTags();
    }
    
    private void addForgeTags() {
        tag(ForgeTags.TOOLS_KNIVES);
        tag(TagHelper.Items.TOOLS$SWORDS).add(
            Items.WOODEN_SWORD,
            Items.STONE_SWORD,
            Items.IRON_SWORD,
            Items.GOLDEN_SWORD,
            Items.DIAMOND_SWORD,
            Items.NETHERITE_SWORD);
        tag(TagHelper.Items.TOOLS$AXES).add(
            Items.WOODEN_AXE,
            Items.STONE_AXE,
            Items.IRON_AXE,
            Items.GOLDEN_AXE,
            Items.DIAMOND_AXE,
            Items.NETHERITE_AXE);
        tag(TagHelper.Items.TOOLS$PICKAXES).add(
            Items.WOODEN_PICKAXE,
            Items.STONE_PICKAXE,
            Items.IRON_PICKAXE,
            Items.GOLDEN_PICKAXE,
            Items.DIAMOND_PICKAXE,
            Items.NETHERITE_PICKAXE);
        tag(TagHelper.Items.TOOLS$SHOVELS).add(
            Items.WOODEN_SHOVEL,
            Items.STONE_SHOVEL,
            Items.IRON_SHOVEL,
            Items.GOLDEN_SHOVEL,
            Items.DIAMOND_SHOVEL,
            Items.NETHERITE_SHOVEL);
        tag(TagHelper.Items.TOOLS$HOES).add(
            Items.WOODEN_HOE,
            Items.STONE_HOE,
            Items.IRON_HOE,
            Items.GOLDEN_HOE,
            Items.DIAMOND_HOE,
            Items.NETHERITE_HOE);
        copy(Tags.Blocks.STORAGE_BLOCKS, Tags.Items.STORAGE_BLOCKS);
        tag(Tags.Items.DUSTS).add(Items.SUGAR).addTag(TagHelper.Items.DUSTS$SALT);
    }
    
    @SuppressWarnings("unchecked")
    private void addCreateTags() {
        tag(TagHelper.Items.UPRIGHT_ON_DEPLOYER).addTags(
            TagHelper.Items.TOOLS$SWORDS,
            TagHelper.Items.TOOLS$AXES,
            TagHelper.Items.TOOLS$PICKAXES,
            TagHelper.Items.TOOLS$SHOVELS,
            TagHelper.Items.TOOLS$HOES,
            ForgeTags.TOOLS_KNIVES,
            Tags.Items.SHEARS);
    }
    
    @Override
    public String getName() {
        return "Create's Delight: Item Tags";
    }
}
