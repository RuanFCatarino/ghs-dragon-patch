package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.api.DragonFlightState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(targets = "com.pokato.skywyrms.entity.DragonEntity")
public abstract class SkywyrmsFlightStateMixin implements DragonFlightState {
    @Shadow
    public abstract boolean isFlying();

    @Override
    public boolean ghs$isFlying() {
        return isFlying();
    }
}
