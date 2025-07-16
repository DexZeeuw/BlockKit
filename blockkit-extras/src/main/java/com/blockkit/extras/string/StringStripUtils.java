package com.blockkit.extras.string;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * Verwijdert accenten uit een string (ë → e, ó → o, etc.).
 */
public final class StringStripUtils {
    private static final Pattern DIACRITICS = 
        Pattern.compile("\\p{InCombiningDiacriticalMarks}+");

    private StringStripUtils() {}

    public static String stripAccents(String input) {
        String normalized = Normalizer.normalize(input, Normalizer.Form.NFD);
        return DIACRITICS.matcher(normalized).replaceAll("");
    }
}
