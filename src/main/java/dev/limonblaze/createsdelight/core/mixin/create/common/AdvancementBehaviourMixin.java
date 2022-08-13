package dev.limonblaze.createsdelight.core.mixin.create.common;

import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.CreateAdvancement;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import dev.limonblaze.createsdelight.common.advancement.AdvancementBehaviourHelper;
import dev.limonblaze.createsdelight.common.advancement.AdvancementHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Mixin(value = AdvancementBehaviour.class, remap = false)
public abstract class AdvancementBehaviourMixin extends TileEntityBehaviour implements AdvancementBehaviourHelper {
    
    public AdvancementBehaviourMixin(SmartTileEntity te) {
        super(te);
    }
    
    @Shadow protected abstract Player getPlayer();
    
    @Shadow private Set<CreateAdvancement> advancements;
    @Shadow private UUID playerId;
    
    @Shadow protected abstract void removeAwarded();
    
    @Shadow public abstract void awardPlayer(CreateAdvancement advancement);
    
    private final Set<AdvancementHolder> createsdelight$advancements = new HashSet<>();
    
    @Inject(method = "removeAwarded", at = @At(value = "INVOKE", target = "Ljava/util/Set;isEmpty()Z"), cancellable = true)
    private void createsdelight$removeAwarded(CallbackInfo ci) {
        this.createsdelight$advancements.removeIf(a -> a.isAlreadyAwardedTo(getPlayer()));
        if(this.createsdelight$advancements.isEmpty() && this.advancements.isEmpty()) {
            this.playerId = null;
            this.tileEntity.setChanged();
        }
        ci.cancel();
    }
    
    public void add(AdvancementHolder... advancements) {
        Collections.addAll(this.createsdelight$advancements, advancements);
    }
    
    public void awardPlayer(AdvancementHolder advancement) {
        Player player = this.getPlayer();
        if(player == null) return;
        this.award(advancement, player);
    }
    
    public void awardPlayerIfNear(AdvancementHolder advancement, int maxDistance) {
        Player player = getPlayer();
        if(player != null && player.distanceToSqr(Vec3.atCenterOf(getPos())) < maxDistance * maxDistance) {
            this.award(advancement, player);
        }
    }
    
    private void award(AdvancementHolder advancement, Player player) {
        if(createsdelight$advancements.contains(advancement)) {
            advancement.awardTo(player);
        }
        removeAwarded();
    }
    
}
