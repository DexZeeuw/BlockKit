package com.blockkit.menu.builder;

import com.blockkit.api.menu.MenuBuilder;
import com.blockkit.menu.core.MenuManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class MenuBuilderImpl implements MenuBuilder {
    private final Inventory inv;
    private final Map<Integer, Consumer<InventoryClickEvent>> handlers = new HashMap<>();

    public MenuBuilderImpl(String title, int rows) {
        this.inv = Bukkit.createInventory(null, rows * 9, title);
    }

    @Override
    public MenuBuilder background(ItemStack filler) {
        for (int i = 0; i < inv.getSize(); i++) {
            if (inv.getItem(i) == null) inv.setItem(i, filler);
        }
        return this;
    }

    @Override
    public MenuBuilder item(int row, int col, ItemStack item) {
        int slot = row * 9 + col;
        inv.setItem(slot, item);
        return this;
    }

    @Override
    public MenuBuilder onClick(int row, int col, Consumer<InventoryClickEvent> handler) {
        int slot = row * 9 + col;
        handlers.put(slot, handler);
        return this;
    }

    @Override
    public Inventory build() {
        // registreer bij MenuManager zodat listener weet welke handlers horen
        MenuManagerImpl.getInstance().register(inv, handlers);
        return inv;
    }
}
