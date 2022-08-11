package dev.limonblaze.createsdelight.common.registry;

import com.tterrag.registrate.util.entry.MenuEntry;
import dev.limonblaze.createsdelight.CreatesDelight;
import dev.limonblaze.createsdelight.client.screen.SteamPotScreen;
import dev.limonblaze.createsdelight.common.menu.SteamPotContainerMenu;

public class CDMenus {
    
    public static CDRegistrate REGISTRATE = CreatesDelight.registrate();
    
    public static MenuEntry<SteamPotContainerMenu> STEAM_POT = REGISTRATE
        .menu("steam_pot", SteamPotContainerMenu::new, () -> SteamPotScreen::new)
        .register();
    
    public static void register() {}
    
}
