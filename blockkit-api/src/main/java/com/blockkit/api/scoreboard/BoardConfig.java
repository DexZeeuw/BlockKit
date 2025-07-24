package com.blockkit.api.scoreboard;

import java.util.List;

/**
 * Immutable config voor een scoreboard.
 */
public class BoardConfig {
    private final String id;
    private final String displayName;
    private final List<String> lines;
    private final long interval;

    public BoardConfig(String id, String displayName, List<String> lines, long interval) {
        this.id = id;
        this.displayName = displayName;
        this.lines = lines;
        this.interval = interval;
    }

    public String getId() {
        return id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public List<String> getLines() {
        return lines;
    }

    public long getInterval() {
        return interval;
    }
}
