package com.blockkit.core.scoreboard.renderer;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class TeamBasedRenderer implements ScoreboardRenderer {

    @Override
    public void render(Objective obj, Player player, List<String> lines) {
        Scoreboard board = obj.getScoreboard();

        // 1) Cleanup: verwijder oude teams & reset scores
        board.getTeams().forEach(Team::unregister);
        board.getEntries().forEach(board::resetScores);

        // 2) Voor elke regel
        int size = lines.size();
        for (int i = 0; i < size; i++) {
            String text   = lines.get(i);              // kan RGB of legacy bevatten
            String teamId = "line_" + i;

            // unieke, onzichtbare entry: §0, §1, … §f
            ChatColor code = ChatColor.values()[i];
            String entryId = code.toString();

            // a) Maak team en koppel de entry
            Team team = board.registerNewTeam(teamId);
            team.addEntry(entryId);

            // b) Prefix toont de regel (RGB wél ondersteund)
            team.setPrefix(text);

            // c) Geen suffix nodig, of lege suffix
            team.setSuffix("");

            // d) Score bepaalt volgorde: hoger = bovenaan
            obj.getScore(entryId).setScore(size - i);
        }
    }
}
