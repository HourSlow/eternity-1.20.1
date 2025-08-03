package net.hour.eternity;

import net.fabricmc.api.ClientModInitializer;
import net.hour.eternity.shader.GrayscaleProcessor;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class EternityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PostProcessHandler.addInstance(GrayscaleProcessor.INSTANCE);
    }
}
