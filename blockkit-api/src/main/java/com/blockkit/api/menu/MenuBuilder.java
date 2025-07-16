// MenuBuilder.java
package com.blockkit.api.menu;

import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

public interface MenuBuilder {

    /** Start een nieuwe menu builder met titel en aantal rijen. */
    static MenuBuilder of(String title, int rows) {
        throw new UnsupportedOperationException("Use blockkit-core implementation");
    }

    /** Vul alle lege slots met een standaard filler-item. */
    MenuBuilder background(ItemStack filler);

    /** Plaats een item op (row, column), 0-indexed: row∈[0,rows), col∈[0,9). */
    MenuBuilder item(int row, int col, ItemStack item);

    /** Stel een click-handler in voor een specifieke slot. */
    MenuBuilder onClick(int row, int col, Consumer<InventoryClickEvent> handler);

    /** Bouw de Inventory en return ’m; je moet ‘m nog registreren. */
    Inventory build();
}
