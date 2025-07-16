package com.blockkit.api.chat;

/**
 * Configuratie voor ChatKit:
 * - prefix voor alle berichten
 * - multi-line support (gebruik een custom delimiter)
 */
public interface ChatConfig {

    /**
     * Haalt de prefix op, bijvoorbeeld "[MyPlugin] "
     */
    String getPrefix();

    /**
     * Stelt de prefix in. Minecraft color codes (&a, &b, etc.) zijn toegestaan.
     */
    void setPrefix(String prefix);

    /**
     * Of multi-line berichten ondersteund worden.
     */
    boolean isMultiLine();

    /**
     * Schakelt multi-line ondersteuning in of uit.
     */
    void setMultiLine(boolean multiLine);

    /**
     * Delimiter die in de tekst "%nl%" kan vervangen voor een line break.
     */
    String getNewLineDelimiter();

    /**
     * Stelt de delimiter in voor nieuwe regels.
     */
    void setNewLineDelimiter(String delimiter);
}