package com.blockkit.item.util;

import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.Optional;

public final class NBTUtils {
    private NBTUtils() {}

    public static void setString(ItemStack stack, String key, String value) {
        ItemMeta meta = stack.getItemMeta();
        meta.getPersistentDataContainer()
            .set(new NamespacedKey("blockkit", key),
                 PersistentDataType.STRING, value);
        stack.setItemMeta(meta);
    }

    public static Optional<String> getString(ItemStack stack, String key) {
        return Optional.ofNullable(stack.getItemMeta())
            .map(m -> m.getPersistentDataContainer()
             .get(new NamespacedKey("blockkit", key), PersistentDataType.STRING));
    }
}
