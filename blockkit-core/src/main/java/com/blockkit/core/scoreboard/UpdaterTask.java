package com.blockkit.core.scoreboard;

/**
 * Runnable voor periodieke scoreboard-updates.
 */
public class UpdaterTask implements Runnable {
    private final ScoreboardManager mgr;

    public UpdaterTask(ScoreboardManager mgr) {
        this.mgr = mgr;
    }

    @Override
    public void run() {
        mgr.updateAll();
    }
}
