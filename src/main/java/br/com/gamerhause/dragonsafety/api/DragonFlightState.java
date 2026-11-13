package br.com.gamerhause.dragonsafety.api;

public interface DragonFlightState {
    boolean ghs$isFlying();

    default boolean ghs$usesShiftBoost() {
        return false;
    }
}
