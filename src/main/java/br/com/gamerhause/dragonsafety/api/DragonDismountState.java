package br.com.gamerhause.dragonsafety.api;

/** Counts distinct Shift presses while a dragon is airborne. */
public final class DragonDismountState {
    private static final long WINDOW_NANOS = 1_500_000_000L;
    private boolean previousShift;
    private boolean allowDismount;
    private int taps;
    private long lastTap;

    public int update(boolean shift, long now) {
        if (!shift) {
            previousShift = false;
            allowDismount = false;
            return 0;
        }
        if (!previousShift) {
            taps = taps > 0 && now >= lastTap && now - lastTap <= WINDOW_NANOS ? taps + 1 : 1;
            lastTap = now;
            if (taps == 3) {
                allowDismount = true;
                taps = 0;
            }
        }
        previousShift = true;
        return allowDismount ? 0 : 3 - taps;
    }

    public boolean allowDismount() {
        return allowDismount;
    }

    public void reset() {
        previousShift = false;
        allowDismount = false;
        taps = 0;
        lastTap = 0;
    }
}
