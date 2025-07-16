package com.blockkit.menu.listener;

import com.blockkit.menu.core.MenuManagerImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Map;
import java.util.function.Consumer;

public class MenuListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        var inv = e.getInventory();
        var handlers = MenuManagerImpl.getInstance().getHandlers(inv);
        if (handlers != null) {
            e.setCancelled(true); // voorkom standaard behavior
            int slot = e.getRawSlot();
            Consumer<InventoryClickEvent> handler = handlers.get(slot);
            if (handler != null) handler.accept(e);
        }
    }
}
