package com.blockkit.core.time;

import com.blockkit.api.time.DurationFormatter;

import java.time.Duration;
import java.time.LocalDateTime;

/**
 * Provides methods for formatting countdowns or elapsed‐times.
 */
public class TimeService {

    private final DurationFormatter formatter;

    public TimeService(DurationFormatter formatter) {
        this.formatter = formatter;
    }

    /**
     * Returns a human‐readable countdown from now until the target.
     */
    public String until(LocalDateTime target) {
        LocalDateTime now = LocalDateTime.now();
        Duration dur = Duration.between(now, target);
        return formatter.format(dur);
    }

    /**
     * Returns a human‐readable elapsed time since the past moment.
     */
    public String since(LocalDateTime past) {
        LocalDateTime now = LocalDateTime.now();
        Duration dur = Duration.between(past, now);
        return formatter.format(dur);
    }
}
