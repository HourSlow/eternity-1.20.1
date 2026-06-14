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
}
