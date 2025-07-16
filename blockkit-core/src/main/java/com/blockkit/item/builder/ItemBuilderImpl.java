package com.blockkit.item.builder;

import com.blockkit.api.item.ItemBuilder;
import com.blockkit.item.util.NBTUtils;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import java.util.Map;

public class ItemBuilderImpl implements ItemBuilder {

    private final ItemStack stack;
    private final ItemMeta meta;

    public ItemBuilderImpl(Material material) {
        this.stack = new ItemStack(material);
        this.meta  = stack.getItemMeta();
    }

    @Override
    public ItemBuilder amount(int amount) {
        stack.setAmount(amount);
        return this;
    }

    @Override
    public ItemBuilder name(String displayName) {
        meta.setDisplayName(displayName);
        return this;
    }

    @Override
    public ItemBuilder lore(String... lore) {
        meta.setLore(java.util.Arrays.asList(lore));
        return this;
    }

    @Override
    public ItemBuilder enchant(Enchantment enchantment, int level, boolean ignore) {
        meta.addEnchant(enchantment, level, ignore);
        return this;
    }

    @Override
    public ItemBuilder enchants(Map<Enchantment, Integer> enchants) {
        enchants.forEach((ench, lvl) -> meta.addEnchant(ench, lvl, true));
        return this;
    }

    @Override
    public ItemBuilder tag(String key, String value) {
        NBTUtils.setString(stack, key, value);
        return this;
    }

    @Override
    public ItemBuilder customModelData(int data) {
        meta.setCustomModelData(data);
        return this;
    }

    @Override
    public ItemStack build() {
        stack.setItemMeta(meta);
        return stack;
    }
}
