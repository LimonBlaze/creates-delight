package dev.limonblaze.createsdelight.data.server.tag;

import dev.limonblaze.createsdelight.common.tag.CrDlTags;
import dev.limonblaze.createsdelight.common.tag.ForgeTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class CrDlItemTagsProvider extends ItemTagsProvider {
    
    public CrDlItemTagsProvider(DataGenerator dataGenerator, BlockTagsProvider blockTagsProvider, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, blockTagsProvider, modId, existingFileHelper);
    }
    
    protected void addTags() {
        addForgeTags();
        addCreatesDelightTags();
    }
    
    private void addForgeTags() {
        tag(vectorwing.farmersdelight.common.tag.ForgeTags.TOOLS_KNIVES);
        tag(ForgeTags.ITEM_TOOLS$SWORDS).add(Items.WOODEN_SWORD, Items.STONE_SWORD, Items.IRON_SWORD, Items.GOLDEN_SWORD, Items.DIAMOND_SWORD, Items.NETHERITE_SWORD);
        tag(ForgeTags.ITEM_TOOLS$AXES).add(Items.WOODEN_AXE, Items.STONE_AXE, Items.IRON_AXE, Items.GOLDEN_AXE, Items.DIAMOND_AXE, Items.NETHERITE_AXE);
        tag(ForgeTags.ITEM_TOOLS$PICKAXES).add(Items.WOODEN_PICKAXE, Items.STONE_PICKAXE, Items.IRON_PICKAXE, Items.GOLDEN_PICKAXE, Items.DIAMOND_PICKAXE, Items.NETHERITE_PICKAXE);
        tag(ForgeTags.ITEM_TOOLS$SHOVELS).add(Items.WOODEN_SHOVEL, Items.STONE_SHOVEL, Items.IRON_SHOVEL, Items.GOLDEN_SHOVEL, Items.DIAMOND_SHOVEL, Items.NETHERITE_SHOVEL);
        tag(ForgeTags.ITEM_TOOLS$HOES).add(Items.WOODEN_HOE, Items.STONE_HOE, Items.IRON_HOE, Items.GOLDEN_HOE, Items.DIAMOND_HOE, Items.NETHERITE_HOE);
    }
    
    @SuppressWarnings("unchecked")
    private void addCreatesDelightTags() {
        tag(CrDlTags.ITEM_UPRIGHT_ON_DEPLOYER).addTags(ForgeTags.ITEM_TOOLS$SWORDS, ForgeTags.ITEM_TOOLS$AXES, ForgeTags.ITEM_TOOLS$PICKAXES, ForgeTags.ITEM_TOOLS$SHOVELS, ForgeTags.ITEM_TOOLS$HOES, vectorwing.farmersdelight.common.tag.ForgeTags.TOOLS_KNIVES);
    }
    
}
