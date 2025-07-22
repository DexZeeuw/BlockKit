package com.blockkit.api.item;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.function.Consumer;

/**
 * Fluent builder voor ItemStacks: materiaal, hoeveelheid, naam, lore, enchants, tags, enz.
 */
public interface ItemBuilder {

    /** Start een nieuwe builder met het opgegeven materiaal. */
    static ItemBuilder of(Material material) {
        throw new UnsupportedOperationException("Use implementation from blockkit-core");
    }

    /** Stel de stack‐hoeveelheid in. */
    ItemBuilder amount(int amount);

    /** Stel de display‐naam in (met kleurcodes). */
    ItemBuilder name(String displayName);

    /** Stel de lore in (meerdere regels). */
    ItemBuilder lore(String... lore);

    /** Voeg een enchantment toe. */
    ItemBuilder enchant(Enchantment enchantment, int level, boolean ignoreLevelRestriction);

    /** Voeg meerdere enchantments toe (key = enchantment, value = level). */
    ItemBuilder enchants(Map<Enchantment, Integer> enchants);

    /** Voeg een custom NBT‐tag toe (key/value). */
    ItemBuilder tag(String key, String value);

    /** Stel custom model data in. */
    ItemBuilder customModelData(int data);

    /**
     * Register a handler that fires on right-click with this item.
     */
    ItemBuilder onUse(Consumer<PlayerInteractEvent> handler);

    /** Bouw en retourneer de ItemStack. */
    ItemStack build();
}
