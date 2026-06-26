package net.hour.eternity.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.hour.eternity.util.DreamerEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(EntityRenderDispatcher.class)
public class EntityVisibilityMixin {

    @Inject(method = "render", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void filterDreamscapeVisibility(
            E entity, double x, double y, double z, float yaw, float tickDelta,
            MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light,
            CallbackInfo ci) {

        ClientPlayerEntity localPlayer = MinecraftClient.getInstance().player;

        if (localPlayer == null || entity == localPlayer) {
            return;
        }

        boolean playerInDreamscape = this.isEntityInDreamscape(localPlayer);
        boolean targetInDreamscape = this.isEntityInDreamscape(entity);

        if (playerInDreamscape != targetInDreamscape) {
            ci.cancel();
        }
    }

    private boolean isEntityInDreamscape(Entity entity) {
        if (entity instanceof DreamerEntity dreamer) {
            return dreamer.isDreaming();
        }
        return false;
    }
}
