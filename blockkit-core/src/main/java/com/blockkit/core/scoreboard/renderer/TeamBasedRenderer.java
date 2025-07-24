package com.blockkit.core.scoreboard.renderer;

import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class TeamBasedRenderer implements ScoreboardRenderer {

    @Override
    public void render(Objective obj, Player player, List<String> lines) {
        Scoreboard board = obj.getScoreboard();

        // 1) Cleanup: verwijder oude teams & entries
        board.getTeams().forEach(Team::unregister);
        board.getEntries().forEach(board::resetScores);

        // 2) Voor elke regel
        for (int i = 0; i < lines.size(); i++) {
            String text     = lines.get(i);                   // RGB/legacy text
            String teamId   = "line_" + i;
            String entryId  = teamId + "_entry";

            // a) maak team aan en voeg entry toe
            Team team = board.registerNewTeam(teamId);
            team.addEntry(entryId);

            // b) prefix = de zichtbare tekst (RGB wél ondersteund)
            team.setPrefix(text);

            // c) score bepaalt volgorde (higher = bovenaan)
            obj.getScore(entryId).setScore(lines.size() - i);
        }
    }
}
