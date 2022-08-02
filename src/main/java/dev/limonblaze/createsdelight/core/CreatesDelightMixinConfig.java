package dev.limonblaze.createsdelight.core;

import dev.limonblaze.createsdelight.core.asm.IForgeBlockStateASM;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class CreatesDelightMixinConfig implements IMixinConfigPlugin {
    
    @Override
    public void onLoad(String mixinPackage) {
    
    }
    
    @Override
    public String getRefMapperConfig() {
        return null;
    }
    
    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        return true;
    }
    
    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {
    
    }
    
    @Override
    public List<String> getMixins() {
        return null;
    }
    
    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
        if("net.minecraftforge.common.extensions.IForgeBlockState".equals(targetClassName)) {
            IForgeBlockStateASM.transform(targetClass);
        }
    }
    
    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    
    }
    
}
