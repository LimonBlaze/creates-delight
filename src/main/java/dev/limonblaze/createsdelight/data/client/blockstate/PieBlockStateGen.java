package dev.limonblaze.createsdelight.data.client.blockstate;

import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.limonblaze.createsdelight.compat.ModHelper;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.ModelFile;
import vectorwing.farmersdelight.common.block.PieBlock;

public class PieBlockStateGen extends SpecialBlockStateGen {
    public static final ResourceLocation DEFAULT_PIE_BOTTOM = ModHelper.FD.asResource("block/pie_bottom");
    public static final ResourceLocation DEFAULT_PIE_SIDE = ModHelper.FD.asResource("block/pie_side");
    private final ResourceLocation top;
    private final ResourceLocation bottom;
    private final ResourceLocation side;
    private final ResourceLocation inner;
    
    public PieBlockStateGen(ResourceLocation top, ResourceLocation bottom, ResourceLocation side, ResourceLocation inner) {
        this.top = top;
        this.side = side;
        this.bottom = bottom;
        this.inner = inner;
    }
    
    public static PieBlockStateGen pie(ResourceLocation id) {
        String namespace = id.getNamespace();
        String path = "block/" + id.getPath();
        return new PieBlockStateGen(
            new ResourceLocation(namespace, path + "_top"),
            DEFAULT_PIE_BOTTOM,
            DEFAULT_PIE_SIDE,
            new ResourceLocation(namespace, path + "_inner")
        );
    }
    
    public static PieBlockStateGen custom(ResourceLocation id) {
        String namespace = id.getNamespace();
        String path = "block/" + id.getPath();
        return new PieBlockStateGen(
            new ResourceLocation(namespace, path + "_top"),
            new ResourceLocation(namespace, path + "_bottom"),
            new ResourceLocation(namespace, path + "_side"),
            new ResourceLocation(namespace, path + "_inner")
        );
    }
    
    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }
    
    @Override
    protected int getYRotation(BlockState state) {
        return horizontalAngle(state.getValue(PieBlock.FACING).getOpposite());
    }
    
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        int bites = state.getValue(PieBlock.BITES);
        String name = ctx.getName();
        String suffix = bites > 0 ? "_slice" + bites : "";
        BlockModelBuilder builder = prov.models()
            .withExistingParent(name + suffix, ModHelper.FD.asResource("block/pie" + suffix))
            .texture("particle", this.top)
            .texture("top", this.top)
            .texture("bottom", this.bottom)
            .texture("side", this.side);
        if(bites > 0) builder.texture("inner", this.inner);
        return builder;
    }
    
}
