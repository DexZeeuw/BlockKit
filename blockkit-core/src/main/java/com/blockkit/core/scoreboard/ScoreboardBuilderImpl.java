package com.blockkit.core.scoreboard;

import com.blockkit.BlockKit;
import com.blockkit.api.scoreboard.ScoreboardBuilder;

import java.util.List;

/**
 * Implementatie van de fluent ScoreboardBuilder.
 */
public class ScoreboardBuilderImpl implements ScoreboardBuilder {
    private final String boardId;
    private String displayName;
    private List<String> lines;
    private long interval = 100L;

    public ScoreboardBuilderImpl(String boardId) {
        this.boardId = boardId;
    }

    @Override
    public ScoreboardBuilder id(String boardId) {
        // boardId vast in constructor; ignore setter
        return this;
    }

    @Override
    public ScoreboardBuilder displayName(String title) {
        this.displayName = title;
        return this;
    }

    @Override
    public ScoreboardBuilder lines(List<String> lines) {
        this.lines = lines;
        return this;
    }

    @Override
    public ScoreboardBuilder updateInterval(long ticks) {
        this.interval = ticks;
        return this;
    }

    @Override
    public ScoreboardBuilder build() {
        // registreer bij de service
        ScoreboardServiceImpl svc = (ScoreboardServiceImpl) BlockKit.getScoreboardService();
        svc.register(boardId, this);
        return this;
    }

    // getters voor interne gebruik door ScoreboardManager
    String getBoardId() {
        return boardId;
    }

    String getDisplayName() {
        return displayName;
    }

    List<String> getLines() {
        return lines;
    }

    long getInterval() {
        return interval;
    }
}
