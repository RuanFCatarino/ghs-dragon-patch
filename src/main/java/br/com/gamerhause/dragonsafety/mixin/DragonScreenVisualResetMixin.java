package br.com.gamerhause.dragonsafety.mixin;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

/** Refreshes the dragon's client-only model after its inventory preview closes. */
@Mixin(Gui.class)
public abstract class DragonScreenVisualResetMixin {
    private static final String DRAGON_SCREEN = "nordmods.uselessreptile.client.gui.URDragonScreen";

    @Shadow
    private Screen screen;

    @Inject(method = "setScreen", at = @At("HEAD"))
    private void ghs$refreshDragonVisuals(Screen nextScreen, CallbackInfo callback) {
        Screen previousScreen = screen;
        if (previousScreen == null || previousScreen == nextScreen
                || !DRAGON_SCREEN.equals(previousScreen.getClass().getName())) return;

        try {
            Field entityField = previousScreen.getClass().getDeclaredField("entity");
            entityField.setAccessible(true);
            Object dragon = entityField.get(previousScreen);
            if (dragon != null) dragon.getClass().getMethod("clearVariant").invoke(dragon);
        } catch (ReflectiveOperationException exception) {
            // A future Useless Reptile version may rename its preview field.
            // Keep the game usable; F3+T remains the manual visual reset.
        }
    }
}
