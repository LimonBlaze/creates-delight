package dev.limonblaze.createsdelight.common.advancement;

import com.google.gson.JsonObject;
import com.tterrag.registrate.util.entry.ItemProviderEntry;
import dev.limonblaze.createsdelight.common.registry.CDTriggers;
import dev.limonblaze.createsdelight.util.LangUtils;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.CriterionTriggerInstance;
import net.minecraft.advancements.critereon.*;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;

import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class AdvancementHolder {
    
    public static final String SECRET_SUFFIX = "\u00A77\n(Hidden Advancement)";
    
    private final Advancement.Builder builder;
    private BuiltinTrigger trigger;
    private AdvancementHolder parent;
    private Advancement result;
    
    private final ResourceLocation id;
    private String title;
    private String description;
    
    public AdvancementHolder(ResourceLocation id, UnaryOperator<Builder> transform) {
        this.builder = Advancement.Builder.advancement();
        this.id = id;
        
        Builder builder = new Builder();
        transform.apply(builder);
        
        if(!builder.externalTrigger) {
            this.trigger = CDTriggers.addSimple(new ResourceLocation(id.getNamespace(), id.getPath() + "_builtin"));
            this.builder.addCriterion("0", trigger.instance());
        }
        
        this.builder.display(
            builder.icon,
            new TranslatableComponent(this.titleKey()),
            new TranslatableComponent(this.descriptionKey()).withStyle(s -> s.withColor(0xDBA213)),
            id.getPath().equals("root") ? new ResourceLocation(id.getNamespace(), "textures/gui/advancements.png") : null,
            builder.type.frame,
            builder.type.toast,
            builder.type.announce,
            builder.type.hide
        );
        
        if(builder.type == TaskType.SECRET) this.description += SECRET_SUFFIX;
    }
    
    public boolean isAlreadyAwardedTo(Player player) {
        if(!(player instanceof ServerPlayer sp)) return true;
        Advancement advancement = sp.getServer().getAdvancements().getAdvancement(this.id);
        if(advancement == null) return true;
        return sp.getAdvancements().getOrStartProgress(advancement).isDone();
    }
    
    public void awardTo(Player player) {
        if(!(player instanceof ServerPlayer sp)) return;
        if(this.trigger == null) {
            throw new UnsupportedOperationException("Advancement " + this.id + " uses external Triggers, it cannot be awarded directly");
        }
        this.trigger.trigger(sp);
    }
    
    public void save(Consumer<Advancement> consumer) {
        if(this.parent != null) this.builder.parent(this.parent.result);
        this.result = this.builder.save(consumer, id.toString());
    }
    
    public void appendToLang(JsonObject json) {
        json.addProperty(this.titleKey(), this.title);
        json.addProperty(this.descriptionKey(), this.description);
    }
    
    private String titleKey() {
        return LangUtils.translateId("advancement", this.id).toKey();
    }
    
    private String descriptionKey() {
        return LangUtils.translateId("advancement", this.id).suffix("desc").toKey();
    }
    
    public class Builder {
        
        private TaskType type = TaskType.NORMAL;
        private boolean externalTrigger;
        private int keyIndex;
        private ItemStack icon;
        
        public Builder typed(TaskType type) {
            this.type = type;
            return this;
        }
        
        public Builder parent(AdvancementHolder other) {
            AdvancementHolder.this.parent = other;
            return this;
        }
        
        public Builder icon(ItemProviderEntry<?> item) {
            return icon(item.asStack());
        }
        
        public Builder icon(ItemLike item) {
            return icon(new ItemStack(item));
        }
        
        public Builder icon(ItemStack stack) {
            this.icon = stack;
            return this;
        }
        
        public Builder title(String title) {
            AdvancementHolder.this.title = title;
            return this;
        }
    
        public Builder description(String description) {
            AdvancementHolder.this.description = description;
            return this;
        }
    
        public Builder whenBlockPlaced(Block block) {
            return this.externalTrigger(PlacedBlockTrigger.TriggerInstance.placedBlock(block));
        }
    
        public Builder whenIconCollected() {
            return this.externalTrigger(InventoryChangeTrigger.TriggerInstance.hasItems(icon.getItem()));
        }
        
        public Builder whenItemCollected(ItemProviderEntry<?>... items) {
            return this.whenItemCollected(Arrays.stream(items).map(entry -> entry.asStack().getItem()).toArray(Item[]::new));
        }
        
        public Builder whenItemCollected(ItemLike... itemProvider) {
            return this.externalTrigger(InventoryChangeTrigger.TriggerInstance.hasItems(itemProvider));
        }
        
        public Builder whenItemCollected(TagKey<Item> tag) {
            return this.externalTrigger(InventoryChangeTrigger.TriggerInstance.hasItems(
                new ItemPredicate(
                    tag,
                    null,
                    MinMaxBounds.Ints.ANY,
                    MinMaxBounds.Ints.ANY,
                    EnchantmentPredicate.NONE,
                    EnchantmentPredicate.NONE,
                    null,
                    NbtPredicate.ANY
                )
            ));
        }
        
        public Builder awardedForFree() {
            return this.externalTrigger(InventoryChangeTrigger.TriggerInstance.hasItems(new ItemLike[0]));
        }
        
        public Builder externalTrigger(CriterionTriggerInstance trigger) {
            builder.addCriterion(String.valueOf(keyIndex), trigger);
            externalTrigger = true;
            keyIndex++;
            return this;
        }
        
    }
    
}
