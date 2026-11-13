package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.PetTargetBridge;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.input.MouseButtonInfo;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class PetTargetMouseMixin {
    private boolean ghs$capturedMiddle;

    @Inject(method = "onButton", at = @At("HEAD"), cancellable = true)
    private void ghs$sendPetTarget(long window, MouseButtonInfo button, int action, CallbackInfo callback) {
        if (button.button() != 2) return;
        Minecraft minecraft = Minecraft.getInstance();
        if (action == 0 && ghs$capturedMiddle) {
            ghs$capturedMiddle = false;
            callback.cancel();
            return;
        }
        if (action != 1 || !button.hasShiftDown() || minecraft.player == null
                || minecraft.gui.screen() != null || minecraft.getWindow().handle() != window) return;
        if (!PetTargetBridge.trigger()) return;
        ghs$capturedMiddle = true;
        callback.cancel();
    }
}
