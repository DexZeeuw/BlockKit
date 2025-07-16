package com.blockkit.time.extras;

import com.blockkit.api.time.DurationFormatter;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;

/**
 * Builder voor custom DurationFormatter: kies units, labels, locale en skip‐zeros.
 */
public class DurationFormatterBuilder {

    private Locale locale = Locale.getDefault();
    private boolean skipZero = true;
    private final Map<TimeUnit, String[]> labels = new LinkedHashMap<>();

    private enum TimeUnit { DAYS, HOURS, MINUTES, SECONDS }

    private DurationFormatterBuilder() {
        // standaard labels Engels
        labels.put(TimeUnit.DAYS,    new String[]{"day", "days"});
        labels.put(TimeUnit.HOURS,   new String[]{"hour","hours"});
        labels.put(TimeUnit.MINUTES, new String[]{"minute","minutes"});
        labels.put(TimeUnit.SECONDS, new String[]{"second","seconds"});
    }

    public static DurationFormatterBuilder of() {
        return new DurationFormatterBuilder();
    }

    public DurationFormatterBuilder locale(Locale locale) {
        this.locale = locale;
        return this;
    }

    public DurationFormatterBuilder skipZeroUnits(boolean skipZero) {
        this.skipZero = skipZero;
        return this;
    }

    public DurationFormatterBuilder labelDays(String singular, String plural) {
        labels.put(TimeUnit.DAYS, new String[]{singular, plural});
        return this;
    }

    public DurationFormatterBuilder labelHours(String singular, String plural) {
        labels.put(TimeUnit.HOURS, new String[]{singular, plural});
        return this;
    }

    public DurationFormatterBuilder labelMinutes(String singular, String plural) {
        labels.put(TimeUnit.MINUTES, new String[]{singular, plural});
        return this;
    }

    public DurationFormatterBuilder labelSeconds(String singular, String plural) {
        labels.put(TimeUnit.SECONDS, new String[]{singular, plural});
        return this;
    }

    public DurationFormatter build() {
        // return een DurationFormatter op maat
        return duration -> {
            long secs = Math.abs(duration.getSeconds());
            long days  = secs / 86_400;
            long hours = (secs % 86_400) / 3_600;
            long mins  = (secs % 3_600) / 60;
            long s     = secs % 60;

            Map<TimeUnit, Long> values = Map.of(
                TimeUnit.DAYS,    days,
                TimeUnit.HOURS,   hours,
                TimeUnit.MINUTES, mins,
                TimeUnit.SECONDS, s
            );

            StringBuilder sb = new StringBuilder();
            values.forEach((unit, val) -> {
                if (!skipZero || val > 0) {
                    String[] form = labels.get(unit);
                    String label = val == 1 ? form[0] : form[1];
                    if (sb.length() > 0) sb.append(" ");
                    sb.append(val).append(" ").append(label);
                }
            });

            // bij lege string, toon "0 <seconds>"
            if (sb.length() == 0) {
                String[] form = labels.get(TimeUnit.SECONDS);
                sb.append("0 ").append(form[1]);
            }
            return sb.toString();
        };
    }
}
