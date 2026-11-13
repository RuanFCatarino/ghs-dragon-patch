package br.com.gamerhause.dragonsafety;

import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/** Keeps each dragon integration optional when a pack adds or removes a dragon mod. */
public final class DragonMixinPlugin implements IMixinConfigPlugin {
    @Override public void onLoad(String mixinPackage) { }
    @Override public String getRefMapperConfig() { return null; }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (mixinClassName.endsWith("SkywyrmsFlightStateMixin"))
            return FabricLoader.getInstance().isModLoaded("skywyrms");
        if (mixinClassName.endsWith("PetTargetMouseMixin")
                || mixinClassName.endsWith("PetTargetKeyMappingMixin")
                || mixinClassName.endsWith("KeyMappingClickAccessor"))
            return FabricLoader.getInstance().isModLoaded("petcompanions");
        if (mixinClassName.endsWith("ClientFlyingDragonBoostMixin")
                || mixinClassName.endsWith("FlyingDragonBoostMixin"))
            return FabricLoader.getInstance().isModLoaded("uselessreptile");
        return true;
    }

    @Override public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) { }
    @Override public List<String> getMixins() { return null; }
    @Override public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
    @Override public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) { }
}
