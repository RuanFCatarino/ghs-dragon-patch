package br.com.gamerhause.dragonsafety.mixin;

import br.com.gamerhause.dragonsafety.api.CrawlToggleState;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/** Keeps Z for the minimap and toggles Crawl with each Shift+Z press. */
@Mixin(KeyMapping.class)
public abstract class CrawlAndMinimapKeyMixin {
    private static final int Z_SCANCODE = 29;
    private static final int LEFT_SHIFT_SCANCODE = 225;
    private static final int RIGHT_SHIFT_SCANCODE = 229;

    @Shadow private String name;
    @Shadow private int clickCount;

    private static final CrawlToggleState ghs$toggle = new CrawlToggleState();
    private static boolean ghs$crawling;

    private static boolean ghs$shiftDown() {
        return InputConstants.isKeyDown(LEFT_SHIFT_SCANCODE)
                || InputConstants.isKeyDown(RIGHT_SHIFT_SCANCODE);
    }

    private static boolean ghs$comboDown() {
        Minecraft game = Minecraft.getInstance();
        return game != null && game.isWindowActive() && game.gui.screen() == null
                && InputConstants.isKeyDown(Z_SCANCODE)
                && ghs$shiftDown();
    }

    private static void ghs$updateCrawlToggle() {
        Minecraft game = Minecraft.getInstance();
        boolean focused = game != null && game.isWindowActive() && game.gui.screen() == null;
        ghs$crawling = ghs$toggle.update(focused && ghs$shiftDown(),
                focused && InputConstants.isKeyDown(Z_SCANCODE), focused);
    }

    @Inject(method = "isDown", at = @At("HEAD"), cancellable = true)
    private void ghs$routeHeldKey(CallbackInfoReturnable<Boolean> result) {
        ghs$updateCrawlToggle();
        if ("key.crawl".equals(name)) {
            result.setReturnValue(ghs$crawling);
        } else if ("gui.xaero_enlarge_map".equals(name) && ghs$comboDown()) {
            result.setReturnValue(false);
        }
    }

    @Inject(method = "consumeClick", at = @At("HEAD"), cancellable = true)
    private void ghs$preventMinimapZoomWhileCrawling(CallbackInfoReturnable<Boolean> result) {
        ghs$updateCrawlToggle();
        if ("gui.xaero_enlarge_map".equals(name) && ghs$comboDown()) {
            clickCount = 0;
            result.setReturnValue(false);
        }
    }
}
