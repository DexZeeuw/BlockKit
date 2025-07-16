package com.blockkit.api.menu;

import org.bukkit.inventory.Inventory;

/**
 * Registreert menu’s zodat click-events automatisch worden afgehandeld.
 */
public interface MenuManager {

    /** Zet een menu open voor alle gekoppelde click-handlers. */
    void register(Inventory menu);

    /** Haal menu uit registratie als je ‘m niet meer gebruikt. */
    void unregister(Inventory menu);
}
