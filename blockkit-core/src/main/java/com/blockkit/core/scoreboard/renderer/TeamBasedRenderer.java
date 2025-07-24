package com.blockkit.core.scoreboard.renderer;

import com.blockkit.BlockKit;
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

        // 1) Cleanup
        board.getTeams().forEach(Team::unregister);
        board.getEntries().forEach(board::resetScores);

        // 2) Render elke regel
        int size = lines.size();
        for (int i = 0; i < size; i++) {
            // originele tekst
            String text = lines.get(i);

            BlockKit.getChat().broadcast("Papi enabled: " + ((Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) ? "ja" : "nee"));

            // run PAPI als beschikbaar
            if (Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI")) {
                text = PlaceholderAPI.setPlaceholders(player, text);
            }

            // unieke, onzichtbare entry (§0…§f)
            ChatColor code = ChatColor.values()[i];
            String entryId  = code.toString();
            String teamId   = "line_" + i;

            // registreer team
            Team team = board.registerNewTeam(teamId);
            team.addEntry(entryId);

            // prefix = jouw (RGB-)tekst
            team.setPrefix(text);
            team.setSuffix("");

            // score voor volgorde
            obj.getScore(entryId).setScore(size - i);
        }
    }
}
