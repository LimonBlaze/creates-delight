package dev.limonblaze.createsdelight.core.asm;

import dev.limonblaze.createsdelight.common.registry.CreatesDelightEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.tree.*;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public final class IForgeBlockStateASM {
    private static final String CLASS_NAME = "dev/limonblaze/createsdelight/core/asm/IForgeBlockStateASM";
    //Forge method, no remap
    private static final String GET_FRICTION_NAME = "getFriction";
    //Official mapping's classes searge and name are the same
    private static final String GET_FRICTION_DESC = "(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/Entity;)F";
    private static final String MODIFY_FRICTION_NAME = "modifyFriction";
    private static final String MODIFY_FRICTION_DESC = "(FLnet/minecraft/world/entity/Entity;)F";
    
    public static void transform(ClassNode cls) {
        MethodNode method = cls.methods.stream()
            .filter(m -> GET_FRICTION_NAME.equals(m.name) && GET_FRICTION_DESC.equals(m.desc))
            .findAny()
            .orElseThrow(() -> new IllegalStateException("Unable to find method " + GET_FRICTION_NAME + GET_FRICTION_DESC));
        InsnList list = new InsnList();
        list.add(new VarInsnNode(Opcodes.ALOAD, 3));
        list.add(new MethodInsnNode(Opcodes.INVOKESTATIC, CLASS_NAME, MODIFY_FRICTION_NAME, MODIFY_FRICTION_DESC));
        List<AbstractInsnNode> returns = new ArrayList<>();
        for(var node : method.instructions) {
            if(node.getOpcode() == Opcodes.FRETURN) {
                returns.add(node);
            }
        }
        for(var node : returns) {
            method.instructions.insertBefore(node, list);
        }
    }
    
    @SuppressWarnings("unused")
    public static float modifyFriction(float friction, @Nullable Entity entity) {
        MobEffectInstance effect;
        if(entity instanceof LivingEntity living && (effect = living.getEffect(CreatesDelightEffects.GREASY.get())) != null) {
            int lvl = effect.getAmplifier() + 1;
            return  1 - (1 - friction) / (8 << lvl);
        }
        return friction;
    }

}
