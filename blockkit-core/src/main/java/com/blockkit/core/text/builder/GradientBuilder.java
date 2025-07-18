package com.blockkit.core.text.builder;

import org.bukkit.Bukkit;

import java.awt.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Bouwt een karakter-voor-karakter kleurgradient tussen twee hex-kleuren.
 */
public class GradientBuilder {
    private final String startRGB;
    private final String endRGB;
    private boolean bold = false;

    private GradientBuilder(String startRGB, String endRGB) {
        this.startRGB = startRGB;
        this.endRGB = endRGB;
    }

    /**
     * Maak een nieuwe builder met hex-kleuren "#RRGGBB" of "RRGGBB".
     */
    public static GradientBuilder of(String hexStart, String hexEnd) {
        return new GradientBuilder(hexStart, hexEnd);
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
        StringBuilder gradientText = new StringBuilder(); // StringBuilder to hold the resulting gradient text
        Color startColor = Color.decode(this.startRGB); // Decode the starting color from hex
        Color endColor = Color.decode(this.endRGB); // Decode the ending color from hex

        // Loop through each character in the text
        for (int i = 0; i < text.length(); i++) {
            float ratio = (float) i / (text.length() - 1); // Calculate the ratio for color interpolation
            Color currentColor = interpolate(startColor, endColor, ratio); // Get the interpolated color
            // Format the color code for the current character
            String colorCode = String.format("&#%02x%02x%02x", currentColor.getRed(), currentColor.getGreen(), currentColor.getBlue());
            gradientText.append(colorCode).append(text.charAt(i)); // Append the color code and character to the result
        }

        return gradientText.toString(); // Return the final gradient text
    }

    /**
     * Interpolates between two colors based on a given ratio.
     *
     * @param start the starting color
     * @param end   the ending color
     * @param ratio the ratio for interpolation (0.0 to 1.0)
     * @return the interpolated Color
     */
    private static Color interpolate(Color start, Color end, float ratio) {
        // Calculate the red, green, and blue components of the interpolated color
        int r = (int) (start.getRed() + ratio * (end.getRed() - start.getRed()));
        int g = (int) (start.getGreen() + ratio * (end.getGreen() - start.getGreen()));
        int b = (int) (start.getBlue() + ratio * (end.getBlue() - start.getBlue()));
        return new Color(r, g, b); // Return the new Color object
    }
}