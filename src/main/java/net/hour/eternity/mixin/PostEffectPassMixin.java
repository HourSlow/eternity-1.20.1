package net.hour.eternity.mixin;

import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostEffectPass.class)
public class PostEffectPassMixin {
    @Shadow
    @Final
    private JsonEffectShaderProgram program;

    @Inject(method = "render", at = @At("HEAD"))
    private void injectTimeUniform(float tickDelta, CallbackInfo ci) {
        GlUniform timeUniform = this.program.getUniformByName("InTime");

        if (timeUniform != null) {
            float time = (Util.getMeasuringTimeMs() % 1000000) / 1000.0f;
            timeUniform.set(time);
        }
    }
}
