package com.blockkit.extras.string;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * Fluente pipeline voor string-transformaties.
 */
public class StringPipeline {

    private String input;
    private final List<Function<String, String>> steps = new ArrayList<>();

    private StringPipeline(String input) {
        this.input = input;
    }

    /** Start een nieuwe pipeline met de opgegeven tekst. */
    public static StringPipeline of(String input) {
        return new StringPipeline(input);
    }

    /** Trim whitespace aan beide zijden. */
    public StringPipeline trim() {
        steps.add(String::trim);
        return this;
    }

    /** Verwijder accenten (ë → e, ó → o, etc.). */
    public StringPipeline stripAccents() {
        steps.add(StringStripUtils::stripAccents);
        return this;
    }

    /** Normaliseer meerdere spaties naar één en verwijder randen. */
    public StringPipeline normalizeSpaces() {
        steps.add(StringNormalizeUtils::normalizeSpace);
        return this;
    }

    /** Zet om naar lowercase. */
    public StringPipeline toLowerCase() {
        steps.add(StringCaseUtils::toLower);
        return this;
    }

    /** Zet om naar UPPERCASE. */
    public StringPipeline toUpperCase() {
        steps.add(StringCaseUtils::toUpper);
        return this;
    }

    /** Capitaliseer de eerste letter van elk woord. */
    public StringPipeline capitalizeAllWords() {
        steps.add(StringCaseUtils::capitalizeAllWords);
        return this;
    }

    /**
     * Pad links of rechts met het opgegeven teken.
     *
     * @param totalWidth de gewenste totale lengte
     * @param padChar     het teken om mee te vullen
     */
    public StringPipeline center(int totalWidth, char padChar) {
        steps.add(s -> StringPadUtils.center(s, totalWidth, padChar));
        return this;
    }

    /** Bouw en retourneer de getransformeerde string. */
    public String build() {
        String result = input;
        for (Function<String, String> step : steps) {
            result = step.apply(result);
        }
        return result;
    }
}
