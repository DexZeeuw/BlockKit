package com.blockkit.core.text.builder;

/**
 * Bouwt een karakter-voor-karakter kleurgradient tussen twee hex-kleuren.
 */
public class GradientBuilder {
    private final int startRGB;
    private final int endRGB;
    private boolean bold = false;

    private GradientBuilder(int startRGB, int endRGB) {
        this.startRGB = startRGB;
        this.endRGB = endRGB;
    }

    /**
     * Maak een nieuwe builder met hex-kleuren "#RRGGBB" of "RRGGBB".
     */
    public static GradientBuilder of(String hexStart, String hexEnd) {
        return new GradientBuilder(hexToRgb(hexStart), hexToRgb(hexEnd));
    }

    /**
     * Schakel bold styling in.
     */
    public GradientBuilder bold() {
        this.bold = true;
        return this;
    }

    /**
     * Pas de gradient toe op de tekst.
     */
    public String apply(String text) {
        StringBuilder out = new StringBuilder();
        int len = text.length();
        for (int i = 0; i < len; i++) {
            float ratio = (len == 1 ? 1f : (float) i / (len - 1));
            int r = interpolate((startRGB >> 16) & 0xFF, (endRGB >> 16) & 0xFF, ratio);
            int g = interpolate((startRGB >>  8) & 0xFF, (endRGB >>  8) & 0xFF, ratio);
            int b = interpolate((startRGB      ) & 0xFF, (endRGB      ) & 0xFF, ratio);

            // Bouw de '§x§R§R§G§G§B§B' hex-code
            String hex = String.format("%02x%02x%02x", r, g, b);
            out.append('§').append('x');
            for (char c : hex.toCharArray()) {
                out.append('§').append(c);
            }
            if (bold) out.append("§l");
            out.append(text.charAt(i));
        }
        return out.toString();
    }

    private static int interpolate(int start, int end, float ratio) {
        return Math.round(start + (end - start) * ratio);
    }

    private static int hexToRgb(String hex) {
        String h = hex.startsWith("#") ? hex.substring(1) : hex;
        return Integer.parseInt(h, 16);
    }
}