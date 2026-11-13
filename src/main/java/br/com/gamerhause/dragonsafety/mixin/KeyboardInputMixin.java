package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.api.DragonFlightState;
import br.com.gamerhause.dragonsafety.api.DragonDismountState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.KeyboardInput;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Input;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardInput.class)
public abstract class KeyboardInputMixin {
    private final DragonDismountState ghs$dismount = new DragonDismountState();
    private boolean ghs$previousShift;

    @Inject(method = "tick", at = @At("TAIL"))
    private void ghs$protectFlyingDragons(CallbackInfo callback) {
        Minecraft minecraft = Minecraft.getInstance();
        if (minecraft.player == null) {
            ghs$dismount.reset();
            ghs$previousShift = false;
            return;
        }

        Entity mount = minecraft.player.getVehicle();
        if (!(mount instanceof DragonFlightState dragon)
                || (mount.onGround() && !dragon.ghs$isFlying())) {
            ghs$dismount.reset();
            ghs$previousShift = false;
            return;
        }

        KeyboardInput keyboard = (KeyboardInput) (Object) this;
        Input raw = keyboard.keyPresses;
        boolean shift = raw.shift();
        int remaining = ghs$dismount.update(shift, System.nanoTime());
        if (shift && !ghs$previousShift && remaining > 0)
            minecraft.player.sendOverlayMessage(Component.literal("Descer: Shift ×" + remaining));
        ghs$previousShift = shift;
        if (!shift || ghs$dismount.allowDismount()) return;

        keyboard.keyPresses = new Input(raw.forward(), raw.backward(), raw.left(), raw.right(),
                raw.jump(), false, dragon.ghs$usesShiftBoost() || raw.sprint());
    }
}
