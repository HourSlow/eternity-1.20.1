package net.hour.eternity.mixin;

import net.hour.eternity.shader.DreamscapeProcessor;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.gl.JsonEffectShaderProgram;
import net.minecraft.client.gl.PostEffectPass;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PostEffectPass.class)
public abstract class PostEffectPassMixin {

    @Shadow
    public abstract JsonEffectShaderProgram getProgram();

    @Inject(method = "render", at = @At("HEAD"))
    private void injectCustomUniforms(float tickDelta, CallbackInfo ci) {
        JsonEffectShaderProgram shaderProgram = this.getProgram();
        if (shaderProgram == null) return;

        GlUniform timeUniform = shaderProgram.getUniformByName("InTime");
        if (timeUniform != null) {
            float time = (Util.getMeasuringTimeMs() % 1000000) / 1000.0f;
            timeUniform.set(time);
        }

        GlUniform fadeUniform = shaderProgram.getUniformByName("u_Fade");
        if (fadeUniform != null) {
            float currentFade = DreamscapeProcessor.INSTANCE.getFadeAmount();
            fadeUniform.set(currentFade);
        }
    }
}
