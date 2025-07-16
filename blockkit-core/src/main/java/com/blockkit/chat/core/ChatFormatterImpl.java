package com.blockkit.chat.core;

import com.blockkit.api.chat.ChatFormatter;
import com.blockkit.text.ColorUtils;
import com.blockkit.text.GradientBuilder;

/**
 * Core‐implementatie van ChatFormatter uit blockkit-api.
 */
public class ChatFormatterImpl implements ChatFormatter {
    private final ChatConfig config;

    public ChatFormatterImpl(ChatConfig config) {
        this.config = config;
    }

    @Override
    public String format(String rawMessage) {
        String trimmed = rawMessage.trim();
        String prefix = GradientBuilder
            .of(config.prefixStart(), config.prefixEnd())
            .bold()
            .apply(config.prefix());
        String line = String.format("%s %s %s", prefix, config.divider(), trimmed);
        return ColorUtils.color(line);
    }
}
