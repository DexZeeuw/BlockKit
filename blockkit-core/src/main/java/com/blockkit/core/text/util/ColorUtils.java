package com.blockkit.core.text.util;

import com.blockkit.core.text.builder.GradientBuilder;
import net.md_5.bungee.api.ChatColor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Simple helper to translate '&' codes naar Minecraft kleurcodes.
 */
public final class ColorUtils {

    private static final Pattern HEX_PATTERN =
            Pattern.compile("(?i)#([A-F0-9]{6})");

    public static String color(String input) {
        if (input == null) return null;

        String text = input;

        // 1) Hex‐RGB → legacy §x codes via GradientBuilder
        Matcher matcher = HEX_PATTERN.matcher(text);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String hex = "#" + matcher.group(1);
            String replacement = GradientBuilder.toLegacyHex(hex);
            matcher.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        matcher.appendTail(sb);

        text = sb.toString();

        // 2) Legacy &‐codes
        text = ChatColor.translateAlternateColorCodes('&', text);

        return text;
    }
}