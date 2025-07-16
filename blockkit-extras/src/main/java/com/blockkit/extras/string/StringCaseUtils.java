package com.blockkit.extras.string;

/**
 * Case-conversies en capitalisatie helpers.
 */
public final class StringCaseUtils {
    private StringCaseUtils() {}

    public static String toLower(String input) {
        return input.toLowerCase();
    }

    public static String toUpper(String input) {
        return input.toUpperCase();
    }

    public static String capitalizeAllWords(String input) {
        String[] words = input.split("\\s+");
        StringBuilder out = new StringBuilder(input.length());
        for (int i = 0; i < words.length; i++) {
            String w = words[i];
            if (w.isEmpty()) continue;
            out.append(Character.toUpperCase(w.charAt(0)));
            if (w.length() > 1) {
                out.append(w.substring(1).toLowerCase());
            }
            if (i < words.length - 1) out.append(' ');
        }
        return out.toString();
    }
}
