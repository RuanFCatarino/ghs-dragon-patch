package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.api.DragonFlightState;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "nordmods.uselessreptile.common.entity.base.URRideableFlyingDragonEntity")
public abstract class ClientFlyingDragonBoostMixin implements DragonFlightState {
    private static final double MAX_BOOST = 1.15;

    @Unique
    private double ghs$boost = 1.0;

    @Shadow
    public abstract boolean isFlying();

    @Override
    public boolean ghs$isFlying() {
        return isFlying();
    }

    @Override
    public boolean ghs$usesShiftBoost() {
        return true;
    }

    @Inject(method = "updateMovementInput", at = @At("RETURN"), cancellable = true)
    private void ghs$boostWhenShiftHeld(Player rider, Vec3 movementInput, CallbackInfoReturnable<Vec3> result) {
        boolean accelerating = isFlying() && rider instanceof LocalPlayer local
                && local.input.keyPresses.sprint() && result.getReturnValue().z > 0;
        ghs$boost = Math.clamp(ghs$boost + (accelerating ? 0.015 : -0.03), 1.0, MAX_BOOST);
        if (!accelerating) return;
        Vec3 speed = result.getReturnValue();
        result.setReturnValue(new Vec3(speed.x, speed.y, speed.z * ghs$boost));
    }

    @Inject(method = "getRotationSpeed", at = @At("RETURN"), cancellable = true)
    private void ghs$keepSteeringResponsive(CallbackInfoReturnable<Float> result) {
        if (isFlying() && ghs$boost > 1.0)
            result.setReturnValue((float) (result.getReturnValue() * (1.0 + (ghs$boost - 1.0))));
    }
}
