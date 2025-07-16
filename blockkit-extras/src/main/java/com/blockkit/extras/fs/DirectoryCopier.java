package com.blockkit.fs.extras;

import java.io.File;

/**
 * Wrap voor recursieve map-kopie via FileUtils.
 */
public final class DirectoryCopier {
    private DirectoryCopier() {}

    /**
     * Kopieer een map compleet (inhoud en submappen).
     *
     * @param srcDir  bronmap
     * @param destDir doelmap
     * @return true als succesvol
     */
    public static boolean copyDirectory(File srcDir, File destDir) {
        return FileUtils.copy(srcDir, destDir);
    }
}
