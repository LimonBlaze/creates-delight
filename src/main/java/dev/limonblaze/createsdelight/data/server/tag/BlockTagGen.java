package dev.limonblaze.createsdelight.data.server.tag;

import dev.limonblaze.createsdelight.common.tag.TagHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BlockTagGen extends BlockTagsProvider {
    
    public BlockTagGen(DataGenerator dataGenerator, String modId, @Nullable ExistingFileHelper existingFileHelper) {
        super(dataGenerator, modId, existingFileHelper);
    }
    
    protected void addTags() {
        addForgeTags();
    }
    
    @SuppressWarnings("unchecked")
    private void addForgeTags() {
        tag(Tags.Blocks.STORAGE_BLOCKS).addTags(
            TagHelper.Blocks.STORAGE_BLOCKS$SUGAR,
            TagHelper.Blocks.STORAGE_BLOCKS$SALT,
            TagHelper.Blocks.STORAGE_BLOCKS$CHEESE
        );
    }
    
    @Override
    public String getName() {
        return "Create's Delight: Block Tags";
    }
}
