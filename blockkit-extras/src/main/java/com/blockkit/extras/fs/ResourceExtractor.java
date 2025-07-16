package com.blockkit.fs.extras;

import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * Halen van bestanden uit de plugin-jar naar plugin-folder.
 */
public final class ResourceExtractor {
    private ResourceExtractor() {}

    /**
     * Extract een resource uit de jar naar disk.
     *
     * @param plugin       je JavaPlugin
     * @param resourcePath pad in de jar (bijv. "default/config.yml")
     * @param outFile      bestemming in plugin.getDataFolder()
     * @param replace      overschrijven als al aanwezig
     * @throws IOException bij fouten
     */
    public static void extract(Plugin plugin,
                               String resourcePath,
                               File outFile,
                               boolean replace) throws IOException {
        if (outFile.exists() && !replace) return;

        try (InputStream in = plugin.getResource(resourcePath)) {
            if (in == null) {
                throw new FileNotFoundException("Resource not found: " + resourcePath);
            }
            Files.createDirectories(outFile.getParentFile().toPath());
            Files.copy(in, outFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        }
    }
}
