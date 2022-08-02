package dev.limonblaze.createsdelight.util;

import dev.limonblaze.createsdelight.CreatesDelight;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.StringJoiner;

public class LangUtils {
    
    public static TranslatableComponent translate(String category, ResourceLocation loc, String[] suffixs, Object[] args) {
        StringJoiner joiner = new StringJoiner(".");
        joiner.add(loc.getNamespace()).add(category).add(loc.getPath());
        for(String suffix : suffixs) joiner.add(suffix);
        return new TranslatableComponent(joiner.toString(), args);
    }
    
    public static TranslatableComponent translate(String category, ResourceLocation loc, String... suffixs) {
        return translate(category, loc, suffixs, new Object[0]);
    }
    
    public static TranslatableComponent translate(String category, ResourceLocation loc, Object... args) {
        return translate(category, loc, new String[0], args);
    }
    
    public static TranslatableComponent translate(String category, String[] suffixs, Object[] args) {
        StringJoiner joiner = new StringJoiner(".");
        joiner.add(CreatesDelight.ID).add(category);
        for(String suffix : suffixs) joiner.add(suffix);
        return new TranslatableComponent(joiner.toString(), args);
    }
    
    public static TranslatableComponent translate(String category, String... suffixs) {
        return translate(category, suffixs, new Object[0]);
    }
    
    public static TranslatableComponent translate(String category, Object... args) {
        return translate(category, new String[0], args);
    }
    
}