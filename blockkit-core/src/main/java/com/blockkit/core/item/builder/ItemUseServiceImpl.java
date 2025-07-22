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
        event.getPlayer().sendMessage("interact event fase 1");
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                event.getPlayer().sendMessage("interact event fase 2.1");
                if (event.getItem() == null) return;
                event.getPlayer().sendMessage("interact event fase 3");

                String id = event.getItem().getItemMeta()
                        .getPersistentDataContainer()
                        .get(KEY, PersistentDataType.STRING);
                event.getItem().getItemMeta().getPersistentDataContainer().getKeys().forEach(k -> {
                    event.getPlayer().sendMessage("Key: " + k.getKey());
                });
                if (id == null) return;
                event.getPlayer().sendMessage("interact event fase 4");

                List<Consumer<PlayerInteractEvent>> handlers =
                        registry.get(UUID.fromString(id));
                if (handlers != null) handlers.forEach(h -> h.accept(event));
                event.getPlayer().sendMessage("interact event fase 5");
            }
            default -> {
                event.getPlayer().sendMessage("interact event fase 2.2");
            }
        }
    }
}
