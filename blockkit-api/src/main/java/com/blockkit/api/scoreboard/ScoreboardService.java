package com.blockkit.api.scoreboard;

import org.bukkit.World;
import org.bukkit.entity.Player;

/**
 * Beheer en toon scoreboards per wereld en per speler.
 */
public interface ScoreboardService {
    /**
     * Verwijder alle geregistreerde boards en stop de updates.
     */
    void shutdown();

    /**
     * Toon board met boardId in een hele wereld.
     */
    void show(World world, String boardId);

    /**
     * Verberg board in een wereld.
     */
    void hide(World world);

    /**
     * Toon board met boardId alleen voor één speler.
     */
    void showForPlayer(Player player, String boardId);

    /**
     * Verberg board voor een enkele speler (val terug op wereld-board).
     */
    void hideForPlayer(Player player);

    /**
     * Registreer een nieuw board-config via een builder.
     */
    void register(String boardId, ScoreboardBuilder builder);
}
