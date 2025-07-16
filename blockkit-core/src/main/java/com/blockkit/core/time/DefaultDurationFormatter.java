package com.blockkit.core.time;

import com.blockkit.api.time.DurationFormatter;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Basic DurationFormatter: outputs non‐zero days, hours, minutes and seconds.
 */
public class DefaultDurationFormatter implements DurationFormatter {

    @Override
    public String format(Duration duration) {
        long secs = Math.abs(duration.getSeconds());
        long days  = secs / 86_400;
        long hours = (secs % 86_400) / 3_600;
        long mins  = (secs % 3_600) / 60;
        long s      = secs % 60;

        List<String> parts = new ArrayList<>();
        if (days  > 0) parts.add(days  + " day"   + (days  == 1 ? "" : "s"));
        if (hours > 0) parts.add(hours + " hour"  + (hours == 1 ? "" : "s"));
        if (mins  > 0) parts.add(mins  + " minute"+ (mins  == 1 ? "" : "s"));
        if (s     > 0 || parts.isEmpty())
            parts.add(s     + " second"+ (s     == 1 ? "" : "s"));

        return String.join(" ", parts);
    }
}
