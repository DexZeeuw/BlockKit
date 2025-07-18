package com.blockkit.api.chat;

/**
 * Configuratie voor ChatKit:
 * - prefix voor alle berichten
 * - multi-line support (gebruik een custom delimiter)
 */
public interface ChatConfig {

    /**
     * Haalt de prefix op, bijvoorbeeld "[MyPlugin]"
     */
    String getPrefix();

    /**
     * Stelt de prefix in. Minecraft color codes (&a, &b, etc.) zijn toegestaan.
     */
    void setPrefix(String prefix);

    /**
     * Haalt de divider op, bijvoorbeeld "⋙"
     */
    String getDivider();

    /**
     * Stelt de divider in. Speciale tekens zijn toegestaan.
     */
    void setDivider(String divider);

    /**
     * Of multi-line berichten ondersteund worden.
     */
    boolean isMultiLine();

    /**
     * Schakelt multi-line ondersteuning in of uit.
     */
    void setMultiLine(boolean multiLine);

    /**
     * Haalt de primary hex op, bijvoorbeeld "#FFFF55"
     */
    String getPrimaryHex();

    /**
     * Stelt de primary hex in. Alleen hex codes toegestaan
     */
    void setPrimaryHex(String hex);

    /**
     * Haalt de secondary hex op, bijvoorbeeld "#FFAA00"
     */
    String getSecondaryHex();

    /**
     * Stelt de secondary hex in. Alleen hex codes toegestaan
     */
    void setSecondaryHex(String hex);
}