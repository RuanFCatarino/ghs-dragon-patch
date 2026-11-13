package br.com.gamerhause.dragonsafety.api;

public final class DragonDismountStateTest {
    public static void main(String[] args) {
        DragonDismountState state = new DragonDismountState();
        long t = 1_000_000_000L;
        check(state.update(true, t) == 2, "first press");
        check(state.update(true, t + 100_000_000L) == 2, "held Shift is one press");
        check(!state.allowDismount(), "first press blocked");
        state.update(false, t + 150_000_000L);
        check(state.update(true, t + 300_000_000L) == 1, "second press");
        check(!state.allowDismount(), "second press blocked");
        state.update(false, t + 350_000_000L);
        check(state.update(true, t + 500_000_000L) == 0, "third press");
        check(state.allowDismount(), "third press allowed");
        state.update(false, t + 550_000_000L);
        check(!state.allowDismount(), "release disarms");
        check(state.update(true, t + 700_000_000L) == 2, "next sequence begins at one");
        state.update(false, t + 800_000_000L);
        check(state.update(true, t + 2_400_000_001L) == 2, "expired sequence resets");
        state.reset();
        check(state.update(true, t + 2_500_000_000L) == 2, "new mount resets");
        state.update(false, t + 2_600_000_000L);
        check(state.update(true, t + 2_700_000_000L) == 1, "after reset second press");
        state.update(false, t + 2_800_000_000L);
        check(state.update(true, t + 2_500_000_000L) == 2, "clock rollback resets");
        System.out.println("DRAGON_DISMOUNT_OK");
    }

    private static void check(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }
}
