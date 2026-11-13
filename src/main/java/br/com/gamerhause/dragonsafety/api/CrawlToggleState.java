package br.com.gamerhause.dragonsafety.api;

/** Tracks presses of Shift+Z independently from how often a key is polled. */
public final class CrawlToggleState {
    private boolean lastCombo;
    private boolean crawling;

    public boolean update(boolean shift, boolean z, boolean focused) {
        if (!focused) {
            lastCombo = false;
            crawling = false;
            return false;
        }
        boolean combo = shift && z;
        if (combo && !lastCombo) {
            crawling = !crawling;
        }
        lastCombo = combo;
        if (!shift) {
            crawling = false;
        }
        return crawling;
    }
}
