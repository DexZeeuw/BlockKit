package com.blockkit.fs.extras;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Basis bewerkingen: bestaan, kopiëren, verwijderen (recursief), lijst
 */
public final class FileUtils {
    private FileUtils() {}

    public static boolean exists(File file) {
        return file != null && file.exists();
    }

    public static boolean delete(File file) {
        if (file == null || !file.exists()) return false;
        if (file.isDirectory()) {
            for (File child : file.listFiles()) {
                delete(child);
            }
        }
        return file.delete();
    }

    public static boolean copy(File source, File target) {
        try {
            if (source.isDirectory()) {
                Files.createDirectories(target.toPath());
                for (File child : source.listFiles()) {
                    copy(child, new File(target, child.getName()));
                }
            } else {
                Files.createDirectories(target.getParentFile().toPath());
                Files.copy(source.toPath(), target.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            }
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    public static List<File> list(File dir) {
        List<File> result = new ArrayList<>();
        if (dir != null && dir.isDirectory()) {
            for (File f : dir.listFiles()) {
                result.add(f);
            }
        }
        return result;
    }
}
