package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.PetTargetBridge;
import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyMapping.class)
public abstract class PetTargetKeyMappingMixin {
    @Shadow private int clickCount;
    @Shadow public abstract String getName();

    @Inject(method = "consumeClick", at = @At("HEAD"), cancellable = true)
    private void ghs$onlyShiftMiddleForPetTarget(CallbackInfoReturnable<Boolean> result) {
        if (!"key.mcpets.target".equals(getName())) return;
        if (PetTargetBridge.consumePending()) {
            clickCount = 1;
            return;
        }
        clickCount = 0;
        result.setReturnValue(false);
    }
}
