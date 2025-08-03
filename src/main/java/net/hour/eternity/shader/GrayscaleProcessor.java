package net.hour.eternity.shader;

import net.hour.eternity.Eternity;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import team.lodestar.lodestone.systems.postprocess.PostProcessor;

public class GrayscaleProcessor extends PostProcessor {
    public static final GrayscaleProcessor INSTANCE = new GrayscaleProcessor();

    static {
        INSTANCE.setActive(false);
    }
    @Override
    public Identifier getPostChainLocation() {
        return new Identifier(Eternity.MOD_ID, "grayscale_post");
    }

    @Override
    public void beforeProcess(MatrixStack matrixStack) {

    }

    @Override
    public void afterProcess() {

    }
}
