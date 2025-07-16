package com.blockkit.extras.fs;

/**
 * Simpele logger interface voor FS-helpers.
 */
public interface FSLogger {
    void info(String msg);
    void warn(String msg);
    void error(String msg, Throwable t);
}
