package com.blockkit.menu.core;

import com.blockkit.api.menu.MenuManager;
import com.blockkit.menu.listener.MenuListener;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import org.bukkit.event.inventory.InventoryClickEvent;

public class MenuManagerImpl implements MenuManager {
    private static MenuManagerImpl instance;
    private final Map<Inventory, Map<Integer, Consumer<InventoryClickEvent>>> registry = new ConcurrentHashMap<>();

    private MenuManagerImpl() {
        Bukkit.getPluginManager().registerEvents(new MenuListener(), BlockKit.getPlugin());
    }

    public static synchronized MenuManagerImpl getInstance() {
        if (instance == null) instance = new MenuManagerImpl();
        return instance;
    }

    void register(Inventory inv, Map<Integer, Consumer<InventoryClickEvent>> handlers) {
        registry.put(inv, handlers);
    }

    @Override
    public void register(Inventory menu) {
        // noop: builder al deed register(inv,handlers)
    }

    @Override
    public void unregister(Inventory menu) {
        registry.remove(menu);
    }

    Map<Integer, Consumer<InventoryClickEvent>> getHandlers(Inventory inv) {
        return registry.get(inv);
    }
}
