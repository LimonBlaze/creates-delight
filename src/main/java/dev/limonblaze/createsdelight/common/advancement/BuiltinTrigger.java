package dev.limonblaze.createsdelight.common.advancement;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import com.simibubi.create.foundation.advancement.CriterionTriggerBase;
import com.simibubi.create.foundation.advancement.ITriggerable;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.PlayerAdvancements;
import net.minecraft.server.level.ServerPlayer;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.*;
import java.util.function.Supplier;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class BuiltinTrigger implements CriterionTrigger<BuiltinTrigger.Instance>, ITriggerable {
    
    private final ResourceLocation id;
    protected final Map<PlayerAdvancements, Set<Listener<BuiltinTrigger.Instance>>> listeners = Maps.newHashMap();
    
    public BuiltinTrigger(ResourceLocation id) {
        this.id = id;
    }
    
    @Override
    public ResourceLocation getId() {
        return this.id;
    }
    
    @Override
    public void addPlayerListener(PlayerAdvancements playerAdvancements, Listener<Instance> listener) {
        Set<Listener<BuiltinTrigger.Instance>> playerListeners = this.listeners.computeIfAbsent(playerAdvancements, k -> new HashSet<>());
        playerListeners.add(listener);
    }
    
    @Override
    public void removePlayerListener(PlayerAdvancements playerAdvancements, Listener<Instance> listener) {
        Set<Listener<BuiltinTrigger.Instance>> playerListeners = this.listeners.get(playerAdvancements);
        if (playerListeners != null) {
            playerListeners.remove(listener);
            if (playerListeners.isEmpty()) {
                this.listeners.remove(playerAdvancements);
            }
        }
    }
    
    @Override
    public void removePlayerListeners(PlayerAdvancements playerAdvancements) {
        this.listeners.remove(playerAdvancements);
    }
    
    @Override
    public Instance createInstance(JsonObject json, DeserializationContext context) {
        return new Instance(getId());
    }
    
    public void trigger(ServerPlayer player) {
        PlayerAdvancements playerAdvancements = player.getAdvancements();
        Set<Listener<BuiltinTrigger.Instance>> playerListeners = this.listeners.get(playerAdvancements);
        if(playerListeners != null) {
            playerListeners.forEach(listener -> listener.run(playerAdvancements));
        }
    }
    
    public Instance instance() {
        return new Instance(getId());
    }
    
    public static class Instance extends AbstractCriterionTriggerInstance {
        
        public Instance(ResourceLocation id) {
            super(id, EntityPredicate.Composite.ANY);
        }
        
    }
    
}
