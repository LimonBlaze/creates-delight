package dev.limonblaze.createsdelight.util;

import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class LangUtils {
    
    public static Builder translate(String category) {
        return new Builder().suffix(CreatesDelight.ID).suffix(category);
    }
    
    public static Builder translateId(String category, ResourceLocation loc) {
        return new Builder().suffix(loc.getNamespace()).suffix(category).suffix(loc.getPath());
    }
    
    public static class Builder {
        private final List<String> key = new ArrayList<>();
        private final List<Object> args = new ArrayList<>();
        
        public Builder suffix(String suffix) {
            key.add(suffix);
            return this;
        }
        
        public Builder arg(Object arg) {
            args.add(arg);
            return this;
        }
        
        public TranslatableComponent toComponent() {
            return new TranslatableComponent(this.toKey(), args.toArray());
        }
        
        public String toKey() {
            StringJoiner joiner = new StringJoiner(".");
            this.key.forEach(joiner::add);
            return joiner.toString();
        }
        
    }
    
}