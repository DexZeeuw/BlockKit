package com.blockkit.core.chat;

import com.blockkit.api.chat.ChatFormatter;
import com.blockkit.core.text.builder.GradientBuilder;
import com.blockkit.core.text.util.ColorUtils;

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
            .of("#fdfefe", "#943126")
            .bold()
            .apply(config.getPrefix());
        String line = String.format("%s %s %s", prefix, config.getNewLineDelimiter(), trimmed);
        return ColorUtils.color(line);
    }
}
