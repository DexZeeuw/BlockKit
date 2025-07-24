package com.blockkit.core.scoreboard;

import com.blockkit.api.scoreboard.ScoreboardBuilder;
import com.blockkit.api.scoreboard.ScoreboardService;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreboardServiceImpl implements ScoreboardService {

    private final Map<String, ScoreboardManager> boards = new HashMap<>();

    @Override
    public void shutdown() {
        boards.values().forEach(ScoreboardManager::stop);
        boards.clear();
    }

    @Override
    public void register(String boardId, ScoreboardBuilder builder) {
        // cast naar onze implementatie
        ScoreboardBuilderImpl b = (ScoreboardBuilderImpl) builder;
        ScoreboardManager mgr = new ScoreboardManager(b);
        boards.put(boardId, mgr);
        mgr.start();  // start repeating update-task
    }

    @Override
    public void show(World world, String boardId) {
        ScoreboardManager mgr = boards.get(boardId);
        if (mgr != null) mgr.showInWorld(world);
    }

    @Override
    public void hide(World world) {
        boards.values().forEach(mgr -> mgr.hideInWorld(world));
    }

    @Override
    public void showForPlayer(Player player, String boardId) {
        ScoreboardManager mgr = boards.get(boardId);
        if (mgr != null) mgr.showFor(player);
    }

    @Override
    public void hideForPlayer(Player player) {
        boards.values().forEach(mgr -> mgr.hideFor(player));
    }
}
