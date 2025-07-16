package com.blockkit.core.text.util;

import net.md_5.bungee.api.ChatColor;

/**
 * Simple helper to translate '&' codes naar Minecraft kleurcodes.
 */
public final class ColorUtils {

    private ColorUtils() { }

    /**
     * Vervang alle '&' colorcodes door de bijbehorende '§' codes.
     */
    public static String color(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }
}