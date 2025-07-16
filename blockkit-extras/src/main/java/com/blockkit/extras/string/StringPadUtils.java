package com.blockkit.extras.string;

/**
 * Padding util: centrerent een string in een veld met pad-karakter.
 */
public final class StringPadUtils {
    private StringPadUtils() {}

    public static String center(String input, int totalWidth, char padChar) {
        if (input == null) input = "";
        int len = input.length();
        if (len >= totalWidth) return input;

        int padding = totalWidth - len;
        int padLeft = padding / 2;
        int padRight = padding - padLeft;

        StringBuilder sb = new StringBuilder(totalWidth);
        for (int i = 0; i < padLeft; i++) sb.append(padChar);
        sb.append(input);
        for (int i = 0; i < padRight; i++) sb.append(padChar);
        return sb.toString();
    }
}
