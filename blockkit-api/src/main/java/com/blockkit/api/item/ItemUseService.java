package com.blockkit.api.item;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;    // ← import

import java.util.UUID;
import java.util.function.Consumer;

public interface ItemUseService {

    /**
     * Register a right-click handler for the given itemId.
     */
    void registerUse(UUID itemId, Consumer<PlayerInteractEvent> handler);

    /**
     * NEW: register an inventory-click handler for the given itemId.
     */
    void registerClick(UUID itemId, Consumer<InventoryClickEvent> handler);
}
