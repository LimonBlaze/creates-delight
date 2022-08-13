package dev.limonblaze.createsdelight.data.client.lang;

import com.google.gson.JsonElement;
import com.simibubi.create.foundation.utility.FilesHelper;
import com.simibubi.create.foundation.utility.Lang;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.data.server.Advancements;

import java.util.function.Supplier;

public enum PartialLangProvider {
    ADVANCEMENTS("Advancements", Advancements::populateLangEntries),
    INTERFACE("UI & Messages"),
    TOOLTIPS("Item Descriptions");
    
    private final String display;
    private final Supplier<JsonElement> provider;
    
    PartialLangProvider(String display) {
        this.display = display;
        this.provider = this::fromResource;
    }
    
    PartialLangProvider(String display, Supplier<JsonElement> customProvider) {
        this.display = display;
        this.provider = customProvider;
    }
    
    public String getDisplay() {
        return display;
    }
    
    public JsonElement provide() {
        return provider.get();
    }
    
    private JsonElement fromResource() {
        String fileName = Lang.asId(name());
        String filepath = "assets/" + CreatesDelight.ID + "/lang/default/" + fileName + ".json";
        JsonElement element = FilesHelper.loadJsonResource(filepath);
        if (element == null)
            throw new IllegalStateException(String.format("Could not find default lang file: %s", filepath));
        return element;
    }
    
}
