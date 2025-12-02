package net.hour.eternity.mixin;

import net.hour.eternity.util.host.HostInvisibilityManager;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class MixinEntity {

    @Inject(method = "isInvisible", at = @At("HEAD"), cancellable = true)
    private void forceInvisibility(CallbackInfoReturnable<Boolean> cir) {
        if (HostInvisibilityManager.INVISIBLE_PLAYERS.contains(((Entity) (Object) this).getUuid())) {
            cir.setReturnValue(true);
        }
    }
}
