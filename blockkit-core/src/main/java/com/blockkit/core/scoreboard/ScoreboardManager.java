package com.blockkit.core.scoreboard;

import com.blockkit.BlockKit;
import com.blockkit.api.scoreboard.BoardConfig;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Beheert uitvoering en updates van één scoreboard-config.
 */
public class ScoreboardManager {
    private final BoardConfig cfg;
    private final Map<UUID, Objective> playerBoards = new HashMap<>();
    private final Set<World> worlds = new HashSet<>();
    private int taskId;

    public ScoreboardManager(ScoreboardBuilderImpl builder) {
        this.cfg = new BoardConfig(
            builder.getBoardId(),
            builder.getDisplayName(),
            builder.getLines(),
            builder.getInterval()
        );
    }

    public void start() {
        taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(
            BlockKit.getPlugin(),
            new UpdaterTask(this),
            0L,
            cfg.getInterval()
        );
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(taskId);
        playerBoards.values().forEach(obj -> {/* eventueel cleanup */});
    }

    public void showInWorld(World world) {
        worlds.add(world);
        world.getPlayers().forEach(this::showFor);
    }

    public void hideInWorld(World world) {
        worlds.remove(world);
        world.getPlayers().forEach(this::hideFor);
    }

    public void showFor(Player player) {
        Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj = sb.registerNewObjective(cfg.getId(), "dummy", cfg.getDisplayName());
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        player.setScoreboard(sb);
        playerBoards.put(player.getUniqueId(), obj);
        updateFor(player);
    }

    public void hideFor(Player player) {
        playerBoards.remove(player.getUniqueId());
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }

    void updateAll() {
        worlds.forEach(w -> w.getPlayers().forEach(this::updateFor));
    }

    void updateFor(Player player) {
        Objective obj = playerBoards.get(player.getUniqueId());
        if (obj != null) updateLines(obj);
    }

    private void updateLines(Objective obj) {
        // clear old
        obj.getScoreboard().getEntries()
            .forEach(entry -> obj.getScoreboard().resetScores(entry));

        List<String> lines = cfg.getLines();
        int size = lines.size();
        IntStream.range(0, size).forEach(i -> {
            String text = lines.get(i);
            obj.getScore(text).setScore(size - i);
        });
    }
}
