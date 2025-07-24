package com.blockkit.api.scoreboard;

import java.util.List;

/**
 * Fluent builder voor configuratie van een scoreboard.
 */
public interface ScoreboardBuilder {

    /**
     * Unieke ID voor dit board.
     */
    ScoreboardBuilder id(String boardId);

    /**
     * Titel die bovenaan in de sidebar wordt getoond.
     */
    ScoreboardBuilder displayName(String title);

    /**
     * De regels (lines) van het board (van boven naar beneden).
     */
    ScoreboardBuilder lines(List<String> lines);

    /**
     * Update-interval in ticks (bv. 20 = 1 seconde).
     */
    ScoreboardBuilder updateInterval(long ticks);

    /**
     * Bouw de configuratie en registreer het board.
     */
    ScoreboardBuilder build();
}
