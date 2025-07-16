package com.blockkit.core.item.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ItemStackUtils {
    private ItemStackUtils() {}

    /**
     * Maak een ItemStack met materiaal, hoeveelheid, displayName en lore.
     */
    public static ItemStack create(
            Material material,
            int amount,
            String displayName,
            List<String> lore
    ) {
        ItemStack stack = new ItemStack(material, amount);
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(displayName);
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
        return stack;
    }

    /**
     * Haal de displayName op indien aanwezig.
     */
    public static Optional<String> getDisplayName(ItemStack stack) {
        return Optional.ofNullable(stack.getItemMeta())
                       .map(ItemMeta::getDisplayName);
    }

    /**
     * Haal de lore op indien aanwezig.
     */
    public static Optional<List<String>> getLore(ItemStack stack) {
        return Optional.ofNullable(stack.getItemMeta())
                       .map(ItemMeta::getLore)
                       .map(l -> l != null ? l : Collections.emptyList());
    }

    /**
     * Zet een nieuwe displayName.
     */
    public static void setDisplayName(ItemStack stack, String name) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setDisplayName(name);
            stack.setItemMeta(meta);
        }
    }

    /**
     * Zet een nieuwe lore.
     */
    public static void setLore(ItemStack stack, List<String> lore) {
        ItemMeta meta = stack.getItemMeta();
        if (meta != null) {
            meta.setLore(lore);
            stack.setItemMeta(meta);
        }
    }
}
