package com.blockkit.api.time;

import java.time.Duration;

/**
 * Formats a java.time.Duration into a human‐readable string.
 */
public interface DurationFormatter {

    /**
     * Format the given Duration, e.g. "2 days 3 hours 15 minutes".
     *
     * @param duration the duration to format
     * @return human‐readable representation
     */
    String format(Duration duration);
}
