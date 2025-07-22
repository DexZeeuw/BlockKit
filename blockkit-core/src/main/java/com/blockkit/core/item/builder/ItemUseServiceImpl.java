package com.blockkit.core.item.builder;

import com.blockkit.BlockKit;
import com.blockkit.api.item.ItemUseService;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.persistence.PersistentDataType;

import java.util.*;
import java.util.function.Consumer;

public class ItemUseServiceImpl implements ItemUseService, Listener {
    private static final NamespacedKey KEY =
            new NamespacedKey(BlockKit.getPlugin(), "blockkit-item-id");

    private final Map<UUID, List<Consumer<PlayerInteractEvent>>> registry = new HashMap<>();

    public ItemUseServiceImpl() {
        // registreer listener bij ListenerService
        BlockKit.getListenerService().register(this);
    }

    @Override
    public void registerUse(UUID itemId, Consumer<PlayerInteractEvent> handler) {
        registry.computeIfAbsent(itemId, k -> new ArrayList<>()).add(handler);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                if (event.getItem() == null) return;
                String id = event.getItem().getItemMeta()
                        .getPersistentDataContainer()
                        .get(KEY, PersistentDataType.STRING);
                if (id == null) return;
                List<Consumer<PlayerInteractEvent>> handlers =
                        registry.get(UUID.fromString(id));
                if (handlers != null) handlers.forEach(h -> h.accept(event));
            }
            default -> {}
        }
    }
}
