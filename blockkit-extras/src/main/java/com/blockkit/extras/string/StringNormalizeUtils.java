package com.blockkit.extras.string;

/**
 * Normaliseert witte ruimtes: meerdere spaties → 1 en trim edges.
 */
public final class StringNormalizeUtils {
    private StringNormalizeUtils() {}

    public static String normalizeSpace(String input) {
        // collapse whitespace (incl. tabs/newlines) naar één spatie
        String collapsed = input.replaceAll("\\s+", " ");
        return collapsed.trim();
    }
}
