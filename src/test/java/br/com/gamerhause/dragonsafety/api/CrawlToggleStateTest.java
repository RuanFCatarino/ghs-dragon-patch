package br.com.gamerhause.dragonsafety.api;

public final class CrawlToggleStateTest {
    private static void expect(boolean actual, boolean expected, String step) {
        if (actual != expected) throw new AssertionError(step);
    }

    public static void main(String[] args) {
        CrawlToggleState state = new CrawlToggleState();
        expect(state.update(true, false, true), false, "Shift sozinho agacha");
        expect(state.update(true, true, true), true, "primeiro Shift+Z deita");
        expect(state.update(true, true, true), true, "segurar Z nao alterna outra vez");
        expect(state.update(true, false, true), true, "soltar Z preserva deitado");
        expect(state.update(true, true, true), false, "segundo Shift+Z volta a agachar");
        expect(state.update(true, false, true), false, "soltar Z continua agachado");
        expect(state.update(true, true, true), true, "terceiro Shift+Z deita novamente");
        expect(state.update(false, false, true), false, "soltar Shift levanta");
        expect(state.update(false, true, true), false, "Z sozinho preserva mapa");
        expect(state.update(true, true, false), false, "perder foco cancela deitado");
        System.out.println("CRAWL_TOGGLE_OK");
    }
}
