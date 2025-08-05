package net.hour.eternity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.client.ForgottenRenderer;
import net.hour.eternity.entity.client.ModModelLayers;
import net.hour.eternity.entity.client.ForgottenModel;
import net.hour.eternity.shader.GrayscaleProcessor;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class EternityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        PostProcessHandler.addInstance(GrayscaleProcessor.INSTANCE);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.THE_FORGOTTEN, ForgottenModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.THE_FORGOTTEN, ForgottenRenderer::new);

    }
}
