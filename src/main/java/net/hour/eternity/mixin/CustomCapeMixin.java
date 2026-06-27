package net.hour.eternity.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.hour.eternity.Eternity;
import net.minecraft.block.Blocks;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.Objects;

//@Mixin(CapeFeatureRenderer.class)
//public class CustomCapeMixin {
//
//    @ModifyArg(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V"
//            ,at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/VertexConsumerProvider;getBuffer(Lnet/minecraft/client/render/RenderLayer;)Lnet/minecraft/client/render/VertexConsumer;"))
//    private RenderLayer SW$changeCape(RenderLayer layer, @Local(argsOnly = true) AbstractClientPlayerEntity player) {
//        if (Objects.equals(player.getUuid().toString(), "f06622d6-fb39-419d-9eed-535aaef3c894")) return RenderLayer.getEndPortal();
//        return layer;
//    }
//}
