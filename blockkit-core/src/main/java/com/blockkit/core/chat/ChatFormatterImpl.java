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

        String prefix = "";
        if (!config.getPrefix().isEmpty()) {
            prefix = GradientBuilder
                    .of(config.getPrimaryHex(), config.getSecondaryHex())
                    .bold()
                    .apply(config.getPrefix());
        }
        String divider = "";
        if (!config.getDivider().isEmpty()) {
            divider = config.getDivider();
        }

        String line = String.format("%s%s%s", (!prefix.isEmpty()) ? String.format("%s ", prefix) : "", (!divider.isEmpty()) ? String.format("%s ", divider) : "", trimmed);
        return ColorUtils.color(line);
    }
}
