package net.hour.eternity;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.client.*;
import net.hour.eternity.util.DreamerEntity;
import net.hour.eternity.shader.DreamscapeProcessor;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.util.host.HostInvisibilityManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.entity.Entity;
import net.minecraft.text.Text;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EternityClient implements ClientModInitializer {

    public static final Map<Integer, Boolean> DREAM_STATES = new ConcurrentHashMap<>();
    private boolean wasDreaming = false;

    @Override
    public void onInitializeClient() {
        HostInvisibilityManager.registerClient();

        PostProcessHandler.addInstance(GrayscaleProcessor.INSTANCE);
        PostProcessHandler.addInstance(DreamscapeProcessor.INSTANCE);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_OAK_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STATUE, RenderLayer.getCutout());


        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.THE_FORGOTTEN, ForgottenModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.THE_FORGOTTEN, ForgottenRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LILGUY, LilGuyModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LILGUY, LilGuyRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MENACE, MenaceModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MENACE, MenaceRenderer::new);

        EntityRendererRegistry.register(ModEntities.STRIKE, OrbitalStrikeRenderer::new);

        //Dreamscape Processor Client-side Handling
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player != null) {
                boolean isCurrentlyDreaming = ((DreamerEntity) client.player).isDreaming();

                if (isCurrentlyDreaming != wasDreaming) {

                    DreamscapeProcessor.INSTANCE.setActive(isCurrentlyDreaming);

                    wasDreaming = isCurrentlyDreaming;

                    if (isCurrentlyDreaming) {
                        client.player.sendMessage(Text.literal("You drift of to sleep..."), true);
                    }
                }
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(Eternity.DREAM_SYNC_PACKET, (client, handler, buf, responseSender) -> {
            int entityId = buf.readInt();
            boolean dreaming = buf.readBoolean();

            client.execute(() -> {
                DREAM_STATES.put(entityId, dreaming);

                if (client.world != null) {
                    Entity targetEntity = client.world.getEntityById(entityId);
                    if (targetEntity instanceof DreamerEntity dreamer) {
                        dreamer.setDreaming(dreaming);
                    }
                }
            });
        });

        ClientPlayConnectionEvents.DISCONNECT.register((handler, client) -> {
            DREAM_STATES.clear();
        });
    }
}
