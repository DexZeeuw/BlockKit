package com.blockkit.api.item;

import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;
import java.util.function.Consumer;

/**
 * Central registry for onUse handlers per item-ID.
 */
public interface ItemUseService {
    /**
     * Register a right-click handler for the given itemId.
     */
    void registerUse(UUID itemId, Consumer<PlayerInteractEvent> handler);
}
