package com.blockkit.extras.chat;

import org.bukkit.inventory.ItemStack;

/**
 * Simpele JSON‐serializer voor ItemStack, gebruikt in SHOW_ITEM hover events.
 */
public final class ItemSerializer {
    private ItemSerializer() {}

    /**
     * Zet een ItemStack om naar de minimale JSON‐vorm die Minecraft verwacht:
     * {"id":"minecraft:stone","count":1}
     * 
     * Je kunt dit uitbreiden met tags, naam, lore, etc.
     */
    public static String toJson(ItemStack item) {
        String namespace = "minecraft";
        String material = item.getType().name().toLowerCase();
        int count = item.getAmount();
        return String.format(
            "{\"id\":\"%s:%s\",\"count\":%d}",
            namespace, material, count
        );
    }
}
