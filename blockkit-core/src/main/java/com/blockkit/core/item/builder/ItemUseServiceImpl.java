package com.blockkit.core.item.builder;

import com.blockkit.BlockKit;
import com.blockkit.api.item.ItemUseService;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;    // ← import
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.function.Consumer;

public class ItemUseServiceImpl implements ItemUseService, Listener {
    private static final NamespacedKey KEY =
        new NamespacedKey(BlockKit.getPlugin(), "blockkit-item-id");

    private final Map<UUID, List<Consumer<PlayerInteractEvent>>>    useRegistry   = new HashMap<>();
    private final Map<UUID, List<Consumer<InventoryClickEvent>>> clickRegistry = new HashMap<>();  // ← new

    public ItemUseServiceImpl() {
        BlockKit.getListenerService().register(this);
    }

    @Override
    public void registerUse(UUID itemId, Consumer<PlayerInteractEvent> handler) {
        useRegistry.computeIfAbsent(itemId, k -> new ArrayList<>()).add(handler);
    }

    @Override
    public void registerClick(UUID itemId, Consumer<InventoryClickEvent> handler) {  // ← new
        clickRegistry.computeIfAbsent(itemId, k -> new ArrayList<>()).add(handler);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        switch (event.getAction()) {
            case RIGHT_CLICK_AIR, RIGHT_CLICK_BLOCK -> {
                if (event.getItem() == null) return;
                ItemMeta meta = event.getItem().getItemMeta();
                if (meta == null) return;
                String id = meta.getPersistentDataContainer()
                                .get(KEY, PersistentDataType.STRING);
                if (id == null) return;
                List<Consumer<PlayerInteractEvent>> handlers =
                    useRegistry.get(UUID.fromString(id));
                if (handlers != null) handlers.forEach(h -> h.accept(event));
            }
            default -> {}
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {// ← new
        if (event.getCurrentItem() == null) return;
        ItemMeta meta = event.getCurrentItem().getItemMeta();
        if (meta == null) return;
        String id = meta.getPersistentDataContainer()
                .get(KEY, PersistentDataType.STRING);
        BlockKit.getChat().broadcast("ID: " + id);
        if (id == null) return;
        List<Consumer<InventoryClickEvent>> handlers =
                clickRegistry.get(UUID.fromString(id));
        if (handlers != null) handlers.forEach(h -> h.accept(event));
    }
}
