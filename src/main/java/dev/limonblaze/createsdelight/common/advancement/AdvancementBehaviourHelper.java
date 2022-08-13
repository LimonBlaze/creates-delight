package dev.limonblaze.createsdelight.common.advancement;

public interface AdvancementBehaviourHelper {
    
    void add(AdvancementHolder... advancements);
    
    void awardPlayer(AdvancementHolder advancement);
    
    void awardPlayerIfNear(AdvancementHolder advancement, int maxDistance);
    
}
