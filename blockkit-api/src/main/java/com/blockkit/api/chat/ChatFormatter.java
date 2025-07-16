package com.blockkit.api.chat;

/**
 * Formats a raw message into a styled chat line (prefix, colors, etc).
 */
public interface ChatFormatter {

    /**
     * Transform a plain text message into a formatted chat string,
     * applying prefix, divider and color codes.
     *
     * @param rawMessage the original unformatted message
     * @return the formatted message ready to send
     */
    String format(String rawMessage);
}
