package net.hour.eternity.mixin;

import net.hour.eternity.Eternity;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerCommandSource.class)
public class ServerCommandSourceMixin {

    @Inject(method = "hasPermissionLevel", at = @At("HEAD"), cancellable = true)
    private void overrideHostPermission(int level, CallbackInfoReturnable<Boolean> cir) {
        ServerCommandSource source = (ServerCommandSource) (Object) this;

        if (source.getEntity() instanceof ServerPlayerEntity player) {

            if (level <= 2 && Eternity.isHost(player)) {
                cir.setReturnValue(true);
            }
        }
    }
}
