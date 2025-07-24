package com.blockkit.core.text.util;

import com.blockkit.core.text.builder.GradientBuilder;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Helper om '&' codes én '#RRGGBB' (of '&#RRGGBB') om te zetten
 * naar de Minecraft §x‐§R§R§G§G§B§B legacy‐hex reeks.
 */
public final class ColorUtils {

    private ColorUtils() { }

    // match '#FFAABB' of '&#FFAABB' (case‐insensitive)
    private static final Pattern HEX_PATTERN =
        Pattern.compile("(?i)&?#([A-F0-9]{6})");

    /**
     * Vervang alle '&' color‐codes en hex‐RGB codes krachtig.
     */
    public static String color(String input) {
        if (input == null) {
            return null;
        }

        // 1) Legacy '&' codes
        String text = ChatColor.translateAlternateColorCodes('&', input);

        // 2) Hex‐RGB (met of zonder leading '&') → legacy §x‐prefix via GradientBuilder
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = "#" + matcher.group(1);
            String replacement = GradientBuilder.toLegacyHex(hex);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);

        return sb.toString();
    }
}
