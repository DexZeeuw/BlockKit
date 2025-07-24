package com.blockkit.core.scoreboard;

import com.blockkit.BlockKit;
import com.blockkit.api.scoreboard.ScoreboardBuilder;
import com.blockkit.api.scoreboard.ScoreboardService;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/**
 * Core-implementatie die boards beheert en tasks start/stop zet.
 */
public class ScoreboardServiceImpl implements ScoreboardService {
    private final Map<String, ScoreboardManager> boards = new HashMap<>();

    @Override
    public void shutdown() {
        boards.values().forEach(ScoreboardManager::stop);
        boards.clear();
    }

    @Override
    public void show(World world, String boardId) {
        ScoreboardManager mgr = boards.get(boardId);
        if (mgr != null) mgr.showInWorld(world);
    }

    @Override
    public void hide(World world) {
        boards.values().forEach(m -> m.hideInWorld(world));
    }

    @Override
    public void showForPlayer(Player player, String boardId) {
        ScoreboardManager mgr = boards.get(boardId);
        if (mgr != null) mgr.showFor(player);
    }

    @Override
    public void hideForPlayer(Player player) {
        boards.values().forEach(m -> m.hideFor(player));
    }

    @Override
    public void register(String boardId, ScoreboardBuilder builder) {
        ScoreboardBuilderImpl b = (ScoreboardBuilderImpl) builder;
        ScoreboardManager mgr = new ScoreboardManager(b);
        boards.put(boardId, mgr);
        mgr.start();
    }
}
