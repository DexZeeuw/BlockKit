package com.blockkit.extras.fs;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Default implementatie met java.util.logging.
 */
public class JavaFSLogger implements FSLogger {
    private static final Logger LOG = Logger.getLogger("BlockKit-FS");

    @Override
    public void info(String msg) {
        LOG.info(msg);
    }

    @Override
    public void warn(String msg) {
        LOG.warning(msg);
    }

    @Override
    public void error(String msg, Throwable t) {
        LOG.log(Level.SEVERE, msg, t);
    }
}
