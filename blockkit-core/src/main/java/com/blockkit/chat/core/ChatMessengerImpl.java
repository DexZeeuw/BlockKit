package com.blockkit.chat.core;

import com.blockkit.api.chat.ChatFormatter;
import com.blockkit.api.chat.ChatMessenger;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

/**
 * Core‐implementatie van ChatMessenger uit blockkit-api.
 */
public class ChatMessengerImpl implements ChatMessenger {
    private final ChatFormatter formatter;

    public ChatMessengerImpl(ChatFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public void send(CommandSender to, String rawMessage) {
        to.sendMessage(formatter.format(rawMessage));
    }

    @Override
    public void send(UUID playerUuid, String rawMessage) {
        Player player = Bukkit.getPlayer(playerUuid);
        if (player != null) {
            send(player, rawMessage);
        }
    }

    @Override
    public void broadcast(String rawMessage) {
        String msg = formatter.format(rawMessage);
        Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage(msg));

        Bukkit.getConsoleSender().sendMessage(msg);
    }
}
