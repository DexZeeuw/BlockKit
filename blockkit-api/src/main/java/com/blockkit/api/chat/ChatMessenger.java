package com.blockkit.api.chat;

import org.bukkit.command.CommandSender;

import java.util.UUID;

/**
 * Sends formatted chat messages to players, console or broadcasts.
 */
public interface ChatMessenger {

    /**
     * Send a formatted message to any CommandSender (player or console).
     *
     * @param to         the recipient
     * @param rawMessage the unformatted message—formatter will be applied
     */
    void send(CommandSender to, String rawMessage);

    /**
     * Send a formatted message to a player by their UUID.
     *
     * @param playerUuid the UUID of the player
     * @param rawMessage the unformatted message—formatter will be applied
     */
    void send(UUID playerUuid, String rawMessage);

    /**
     * Broadcast a formatted message to all online players.
     *
     * @param rawMessage the unformatted message—formatter will be applied
     */
    void broadcast(String rawMessage);
}
