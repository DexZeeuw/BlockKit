package com.blockkit.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.inventory.InventoryClickEvent;    // ← import
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

public interface ItemBuilder {

    static ItemBuilder of(Material material) {
        throw new UnsupportedOperationException("Use implementation from blockkit-core");
    }

    ItemBuilder amount(int amount);

    ItemBuilder name(String displayName);

    ItemBuilder lore(String... lore);

    ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction);

    ItemBuilder enchants(Map<Enchantment, Integer> enchants);

    ItemBuilder tag(String key, String value);

    ItemBuilder customModelData(int data);

    /** world-right-click handler */
    ItemBuilder onUse(Consumer<PlayerInteractEvent> handler);

    /** NEW: inventory-click handler */
    ItemBuilder onClick(Consumer<InventoryClickEvent> handler);

    ItemStack build();
}
