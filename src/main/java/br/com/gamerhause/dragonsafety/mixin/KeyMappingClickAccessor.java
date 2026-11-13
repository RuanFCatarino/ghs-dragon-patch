package br.com.gamerhause.dragonsafety.mixin;

import net.minecraft.client.KeyMapping;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyMapping.class)
public interface KeyMappingClickAccessor {
    @Accessor("clickCount")
    void ghs$setClickCount(int count);
}
