package dev.limonblaze.createsdelight.data.server;

import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.simibubi.create.AllBlocks;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.common.advancement.AdvancementHolder;
import dev.limonblaze.createsdelight.common.advancement.TaskType;
import dev.limonblaze.createsdelight.common.registry.CDBlocks;
import dev.limonblaze.createsdelight.common.registry.CDItems;
import dev.limonblaze.createsdelight.common.registry.CDTags;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.advancements.Advancement;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import vectorwing.farmersdelight.common.registry.ModItems;

import javax.annotation.ParametersAreNonnullByDefault;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class Advancements implements DataProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    public static final List<AdvancementHolder> ADVANCEMENTS = new ArrayList<>();
    public static final AdvancementHolder
        START = null,
        ROOT = create("root", b -> b
            .icon(CDBlocks.STEAM_POT)
            .title("Welcome to Create's Delight")
            .description("Steampowered delicacies awaits you to discover!")
            .awardedForFree()
            .typed(TaskType.SILENT)),
        //dairy
        DAIRY_INDUSTRY = create("dairy_industry", b -> b
            .icon(Items.MILK_BUCKET)
            .title("Dairy Industry")
            .description("Pour Milk into the basin")
            .parent(ROOT)),
        CREAMY = create("creamy", b -> b
            .icon(CDItems.CREAM_BOTTLE)
            .title("Creamy!")
            .description("Obtain Cream from Milk")
            .whenIconCollected()
            .parent(DAIRY_INDUSTRY)),
        CHEESE_OF_TRUTH = create("cheese_of_truth", b -> b
            .icon(CDBlocks.CHEESE_WHEEL)
            .title("Cheese of Truth")
            .description("Obtain Cheese from Milk, and shape it into a Cheese Wheel")
            .whenIconCollected()
            .parent(CREAMY)),
        BUTTER_FLIES = create("butter_flies", b -> b
            .icon(CDItems.BUTTER)
            .title("Butter Flies!")
            .description("Stir the Cream until it turns into Butter")
            .whenIconCollected()
            .parent(CHEESE_OF_TRUTH)),
        NOT_ONLY_FOR_POTIONS = create("not_only_for_potions", b -> b
            .icon(Items.NETHER_WART)
            .title("Not Only for Potions")
            .description("Of course Nether Wart can ferment Milk and Cream")
            .whenItemCollected(CDItems.YOGURT_BOTTLE, CDItems.SOUR_CREAM_BOTTLE)
            .parent(BUTTER_FLIES)),
        //blaze stove
        CHEF_BLAZE = create("chef_blaze", b -> b
            .icon(CDBlocks.BLAZE_STOVE)
            .title("Chef Blaze")
            .description("Convert a Blaze Burner to make your Blaze a Chef")
            .parent(ROOT)
            .typed(TaskType.NOISY)),
        BLAZERINO = create("blazerino", b -> b
            .icon(Items.TORCH)
            .title("Blazerino")
            .description("Activate your Blaze Stove and enjoy the boost!")
            .parent(CHEF_BLAZE)),
        SUPER_BLAZERINO = create("super_blazerino", b -> b
            .icon(Items.SOUL_TORCH)
            .title("Super Blazerino")
            .description("The more it burns, the faster it cooks")
            .parent(BLAZERINO)),
        BLAZING_BARBECUE = create("blazing_barbecue", b -> b
            .icon(ModItems.BARBECUE_STICK.get())
            .title("Blazing Barbecue")
            .description("Let's go barbecue with our Blaze friends!")
            .parent(CHEF_BLAZE)),
        OVERCOOKED = create("overcooked", b -> b
            .icon(Items.CHARCOAL)
            .title("Overcooked")
            .description("Apparently, it's not a good idea to barbecue when Super-Heated")
            .parent(BLAZING_BARBECUE)
            .typed(TaskType.SECRET)),
        //steam pot
        COOKING_WITH_STEAM = create("cooking_with_steam", b -> b
            .icon(CDBlocks.STEAM_POT)
            .title("Cooking with Steam")
            .description("Attach a Steam Pot to a active Steam Tank")
            .parent(ROOT)
            .typed(TaskType.NOISY)),
        ANOTHER_KIND_OF_BREAD = create("another_kind_of_bread", b -> b
            .icon(CDItems.STEAMED_BUN)
            .title("Another Kind of Bread")
            .description("This is how Chinese make their own \"Bread\"!")
            .whenIconCollected()
            .parent(COOKING_WITH_STEAM)),
        ANOTHER_KIND_OF_PIE = create("another_kind_of_pie", b -> b
            .icon(CDItems.STEAMED_PORK_BUN)
            .title("Another Kind of Pie")
            .description("And this is how Chinese make their own \"Pie\"!")
            .whenItemCollected(CDTags.ItemTag.STEAMED_STUFFED_BUNS)
            .parent(ANOTHER_KIND_OF_BREAD)
        ),
        FULL_STEAM_COOKING = create("full_steam_cooking", b -> b
            .icon(CDBlocks.STEAM_POT)
            .title("Full Steam, but Cooking")
            .description("Time to see the limits of a Steam Pot")
            .parent(ANOTHER_KIND_OF_PIE)
            .typed(TaskType.EXPERT)),
        //automation
        HANDS_OF_A_CHEF = create("hands_of_a_chef", b -> b
            .icon(AllBlocks.DEPLOYER.get())
            .title("Hands of a Chef")
            .description("Use a Deployer to perform Cutting Board Recipes on a Belt")
            .parent(ROOT)
            .typed(TaskType.NOISY)),
        ARMS_OF_A_CHEF = create("arms_of_a_chef", b -> b
            .icon(AllBlocks.MECHANICAL_ARM)
            .title("Arms of a Chef")
            .description("Use Mechanical Arms to operate a Cooking Pot")
            .parent(HANDS_OF_A_CHEF)),
        //sequenced assembly
        PIE_FACTORY = create("pie_factory", b -> b
            .icon(ModItems.APPLE_PIE.get())
            .title("Pie Factory")
            .description("Make an Assembly Line for Pies and check the incomplete product")
            .whenItemCollected(CDTags.ItemTag.INCOMPLETE_FOOD$PIES)
            .parent(ROOT)),
        SANDWICH_FACTORY = create("sandwich_factory", b -> b
            .icon(ModItems.BACON_SANDWICH.get())
            .title("Sandwich Factory")
            .description("Make an Assembly Line for Sandwiches and check the incomplete product")
            .whenItemCollected(CDTags.ItemTag.INCOMPLETE_FOOD$SANDWICHES)
            .parent(PIE_FACTORY)),
        HAMBURGER_FACTORY = create("hamburger_factory", b -> b
            .icon(ModItems.HAMBURGER.get())
            .title("Hamburger Factory")
            .description("Make an Assembly Line for Hamburgers and check the incomplete product")
            .whenItemCollected(CDTags.ItemTag.INCOMPLETE_FOOD$HAMBURGERS)
            .parent(SANDWICH_FACTORY)),
        WRAP_FACTORY = create("wrap_factory", b -> b
            .icon(ModItems.MUTTON_WRAP.get())
            .title("Wrap Factory")
            .description("Make an Assembly Line for Wraps and check the incomplete product")
            .whenItemCollected(CDTags.ItemTag.INCOMPLETE_FOOD$WRAPS)
            .parent(HAMBURGER_FACTORY)),
        END = null;
    
    private final DataGenerator generator;
    
    public Advancements(DataGenerator generator) {
        this.generator = generator;
    }
    
    public static void register() {}
    
    public static AdvancementHolder create(String name, UnaryOperator<AdvancementHolder.Builder> transform) {
        AdvancementHolder holder = new AdvancementHolder(CreatesDelight.asResource(name), transform);
        ADVANCEMENTS.add(holder);
        return holder;
    }
    
    public static JsonObject populateLangEntries() {
        JsonObject json = new JsonObject();
        for(AdvancementHolder advancement : ADVANCEMENTS) {
            advancement.appendToLang(json);
        }
        return json;
    }
    
    @Override
    public void run(HashCache cache) {
        Path outputFolder = this.generator.getOutputFolder();
        Set<ResourceLocation> set = Sets.newHashSet();
        Consumer<Advancement> consumer = advancement -> {
            if(!set.add(advancement.getId())) throw new IllegalStateException("Duplicate advancement " + advancement.getId());
    
            Path outputPath = outputFolder.resolve("data/" +
                advancement.getId().getNamespace() + "/advancements/" +
                advancement.getId().getPath() + ".json"
            );
            
            try {
                DataProvider.save(GSON, cache, advancement.deconstruct().serializeToJson(), outputPath);
            } catch (IOException ioexception) {
                LOGGER.error("Couldn't save advancement {}", outputPath, ioexception);
            }
            
        };
        
        for(AdvancementHolder advancement : ADVANCEMENTS) {
            advancement.save(consumer);
        }
        
    }
    
    @Override
    public String getName() {
        return "Advancements";
    }
    
}
