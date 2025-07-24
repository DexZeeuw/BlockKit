package com.blockkit.core.scoreboard;

import com.blockkit.BlockKit;
import com.blockkit.api.scoreboard.BoardConfig;
import com.blockkit.core.scoreboard.renderer.TeamBasedRenderer;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.Objective;

import java.util.*;

public class ScoreboardManager {
    private final BoardConfig cfg;
    private final Map<UUID, Objective> playerBoards = new HashMap<>();
    private final Set<World> worlds = new HashSet<>();
    private final TeamBasedRenderer renderer = new TeamBasedRenderer();
    private int taskId;

    public ScoreboardManager(ScoreboardBuilderImpl builder) {
        this.cfg = new BoardConfig(
            builder.getBoardId(),
            builder.getDisplayName(),
            builder.getLines(),
            builder.getInterval()
        );
    }

    /** Start de repeating scheduler voor updates */
    public void start() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
            BlockKit.getPlugin(),
            this::updateAll,
            0L,
            cfg.getInterval()
        );
    }

    /** Stop de scheduler en clear alle data */
    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
        playerBoards.clear();
        worlds.clear();
    }

    /** Toon dit board voor alle spelers in de wereld */
    public void showInWorld(World world) {
        worlds.add(world);
        world.getPlayers().forEach(this::showFor);
    }

    /** Verberg dit board voor alle spelers in de wereld */
    public void hideInWorld(World world) {
        worlds.remove(world);
        world.getPlayers().forEach(this::hideFor);
    }

    /** Toon board alleen voor één speler */
    public void showFor(Player player) {
        // Nieuwe Objective per speler
        Objective obj = Bukkit.getScoreboardManager()
            .getNewScoreboard()
            .registerNewObjective(cfg.getId(), "dummy", cfg.getDisplayName());
        obj.setDisplaySlot(org.bukkit.scoreboard.DisplaySlot.SIDEBAR);
        player.setScoreboard(obj.getScoreboard());
        playerBoards.put(player.getUniqueId(), obj);
        updateFor(player);
    }

    /** Verberg board voor één speler */
    public void hideFor(Player player) {
        playerBoards.remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    /** Update alle boards: overrides én wereld-boards */
    private void updateAll() {
        // 1) persoonlijke overrides
        new ArrayList<>(playerBoards.keySet()).forEach(uuid -> {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null && playerBoards.containsKey(uuid)) {
                updateFor(p);
            } else {
                playerBoards.remove(uuid);
            }
        });
        // 2) wereld-boards
        worlds.forEach(world ->
            world.getPlayers().forEach(this::updateFor)
        );
    }

    /** Update een enkele speler */
    private void updateFor(Player player) {
        Objective obj = playerBoards.get(player.getUniqueId());
        if (obj != null) {
            renderer.render(obj, player, cfg.getLines());
        }
    }
}
