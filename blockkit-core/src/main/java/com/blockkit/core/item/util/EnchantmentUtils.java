package com.blockkit.core.item.util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.stream.Collectors;

public final class EnchantmentUtils {
    private EnchantmentUtils() {}

    /**
     * Voeg een enchantment toe.
     */
    public static void addEnchantment(
            ItemStack stack,
            Enchantment enchantment,
            int level,
            boolean ignoreLevelRestriction
    ) {
        stack.getItemMeta().addEnchant(enchantment, level, ignoreLevelRestriction);
        stack.setItemMeta(stack.getItemMeta());
    }

    /**
     * Haal alle enchantments uit de itemstack.
     */
    public static Map<Enchantment, Integer> getEnchantments(ItemStack stack) {
        return stack.getItemMeta().getEnchants();
    }

    /**
     * Controleer of een enchantment aanwezig is.
     */
    public static boolean hasEnchantment(ItemStack stack, Enchantment enchantment) {
        return stack.getItemMeta().hasEnchant(enchantment);
    }

    /**
     * Verwijder een specifiek enchantment.
     */
    public static void removeEnchantment(ItemStack stack, Enchantment enchantment) {
        stack.getItemMeta().removeEnchant(enchantment);
        stack.setItemMeta(stack.getItemMeta());
    }

    /**
     * Verwijder alle enchantments.
     */
    public static void clearEnchantments(ItemStack stack) {
        stack.getItemMeta().getEnchants()
            .keySet()
            .forEach(e -> stack.getItemMeta().removeEnchant(e));
        stack.setItemMeta(stack.getItemMeta());
    }
}
