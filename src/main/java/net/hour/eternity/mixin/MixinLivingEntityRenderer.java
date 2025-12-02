package net.hour.eternity.mixin;

import net.hour.eternity.util.host.HostInvisibilityManager;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntityRenderer.class)
public class MixinLivingEntityRenderer {

    @Inject(
            method = "render",
            at = @At("HEAD"),
            cancellable = true
    )
    private void onRender(LivingEntity entity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if (entity instanceof PlayerEntity player) {
            if (HostInvisibilityManager.INVISIBLE_PLAYERS.contains(player.getUuid())) {
                ci.cancel();
            }
        }
    }
}
