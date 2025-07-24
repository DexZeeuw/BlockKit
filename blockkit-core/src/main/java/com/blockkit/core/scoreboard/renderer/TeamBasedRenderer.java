package com.blockkit.core.scoreboard.renderer;

import com.blockkit.core.text.util.ColorUtils;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.List;

public class TeamBasedRenderer implements ScoreboardRenderer {

    @Override
    public void render(Objective obj, Player player, List<String> lines) {
        Scoreboard board = obj.getScoreboard();

        int size = lines.size();
        for (int i = 0; i < size; i++) {
            // unieke onzichtbare entry (§0 … §f)
            ChatColor code = ChatColor.values()[i];
            String entryId = code.toString();
            String teamId  = "line_" + i;

            // haal of maak team
            Team team = board.getTeam(teamId);
            if (team == null) {
                team = board.registerNewTeam(teamId);
                team.addEntry(entryId);
            }

            // haal tekst & pas PAPI toe indien nodig
            String text = lines.get(i);
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                text = ColorUtils.color(PlaceholderAPI.setPlaceholders(player, text));
            }

            // update prefix alleen als gewijzigd
            if (!text.equals(team.getPrefix())) {
                team.setPrefix(text);
            }

            // update score als die niet klopt
            int expectedScore = size - i;
            Score current = obj.getScore(entryId);
            if (current.getScore() != expectedScore) {
                current.setScore(expectedScore);
            }
        }
    }
}
