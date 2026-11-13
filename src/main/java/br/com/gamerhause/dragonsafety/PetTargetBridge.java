package br.com.gamerhause.dragonsafety;

import br.com.gamerhause.dragonsafety.mixin.KeyMappingClickAccessor;
import net.minecraft.client.KeyMapping;

import java.lang.reflect.Field;

/** Routes one Shift + middle-click to Pet Companions without queuing a normal ping. */
public final class PetTargetBridge {
    private static KeyMapping target;
    private static boolean pending;

    private PetTargetBridge() { }

    public static boolean trigger() {
        try {
            if (target == null) {
                Class<?> client = Class.forName("de.petcompanions.client.PetCompanionsClient");
                Field field = client.getDeclaredField("markTarget");
                field.setAccessible(true);
                target = (KeyMapping) field.get(null);
            }
            if (target == null) return false;
            pending = true;
            ((KeyMappingClickAccessor) target).ghs$setClickCount(1);
            return true;
        } catch (ReflectiveOperationException | ClassCastException exception) {
            return false;
        }
    }

    public static boolean consumePending() {
        boolean result = pending;
        pending = false;
        return result;
    }
}
