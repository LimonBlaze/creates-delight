package dev.limonblaze.createsdelight.data.client.blockstate;

import com.simibubi.create.content.contraptions.components.steam.whistle.WhistleBlock;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import dev.limonblaze.createsdelight.common.block.SteamPotBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class SteamPotBlockStateGen extends SpecialBlockStateGen {
    
    @Override
    protected int getXRotation(BlockState state) {
        return 0;
    }
    
    @Override
    protected int getYRotation(BlockState state) {
        return horizontalAngle(state.getValue(SteamPotBlock.FACING));
    }
    
    @Override
    public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov, BlockState state) {
        String wall = state.getValue(SteamPotBlock.WALL) ? "wall" : "floor";
        return AssetLookup.partialBaseModel(ctx, prov, wall);
    }
    
}
