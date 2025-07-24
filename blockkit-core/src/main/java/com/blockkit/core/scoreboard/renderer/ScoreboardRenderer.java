package com.blockkit.core.scoreboard.renderer;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;
import java.util.List;

public interface ScoreboardRenderer {
    /**
     * Render de gegeven lines in de sidebar van een Objective voor een speler.
     *
     * @param obj     het scoreboard-objective (SIDEBAR)
     * @param player  de target speler
     * @param lines   de rijen tekst, inclusief RGB-codes
     */
    void render(Objective obj, Player player, List<String> lines);
}
