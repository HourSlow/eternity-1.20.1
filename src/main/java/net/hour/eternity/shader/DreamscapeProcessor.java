package net.hour.eternity.shader;

import net.hour.eternity.Eternity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.GlUniform;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

import java.lang.reflect.Field;

public class DreamscapeProcessor extends PostProcessor {
    public static final DreamscapeProcessor INSTANCE = new DreamscapeProcessor();

    private boolean targetActive = false;
    private float fadeAmount = 0.0f;
    public static final float FADE_SPEED = 0.05f; // Higher = faster, Lower = slower fade (0.05 is ~1 second)

    static {
        INSTANCE.setActive(false);
    }

    @Override
    public Identifier getPostChainLocation() {
        return new Identifier(Eternity.MOD_ID, "dreamscape_post");
    }

    @Override
    public void beforeProcess(MatrixStack matrixStack) {
    }

    @Override
    public void afterProcess() {
    }

    public float getFadeSpeed() {
        return this.FADE_SPEED;
    }

    public void setTargetActive(boolean active) {
        this.targetActive = active;
    }

    public boolean isTargetActive() {
        return this.targetActive;
    }

    public float getFadeAmount() {
        return this.fadeAmount;
    }

    public boolean shouldShaderBeActive() {
        return targetActive || fadeAmount > 0.0f;
    }

    public void tickAnimation() {
        if (targetActive) {
            fadeAmount = Math.min(1.0f, fadeAmount + FADE_SPEED);
        } else {
            fadeAmount = Math.max(0.0f, fadeAmount - FADE_SPEED);
        }
    }
}
