package dev.limonblaze.createsdelight.mixin.create.access;

import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(DeployerTileEntity.class)
public interface DeployerTileEntityAccessor {
    
    @Accessor(value = "heldItem", remap = false)
    ItemStack crdl$getHeldItem();
    
}
