package net.hour.eternity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.client.*;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.util.host.HostInvisibilityManager;
import net.minecraft.client.render.RenderLayer;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

public class EternityClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        HostInvisibilityManager.registerClient();

        PostProcessHandler.addInstance(GrayscaleProcessor.INSTANCE);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_OAK_SAPLING, RenderLayer.getCutout());


        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.THE_FORGOTTEN, ForgottenModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.THE_FORGOTTEN, ForgottenRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LILGUY, LilGuyModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LILGUY, LilGuyRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MENACE, MenaceModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MENACE, MenaceRenderer::new);
    }
}
