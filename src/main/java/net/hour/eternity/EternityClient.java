package net.hour.eternity;

import com.terraformersmc.terraform.boat.api.client.TerraformBoatClientHelper;
import com.terraformersmc.terraform.sign.SpriteIdentifierRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.entity.ModBoats;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.client.*;
import net.hour.eternity.util.DreamerEntity;
import net.hour.eternity.shader.DreamscapeProcessor;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.util.host.HostInvisibilityManager;
import net.hour.eternity.util.packets.ModClientPackets;
import net.hour.eternity.world.dimension.ModDimensions;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.TexturedRenderLayers;
import net.minecraft.client.util.SpriteIdentifier;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import team.lodestar.lodestone.systems.postprocess.PostProcessHandler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EternityClient implements ClientModInitializer {

    public static final Map<Integer, Boolean> DREAM_STATES = new ConcurrentHashMap<>();
    private boolean wasDreaming = false;

    @Override
    public void onInitializeClient() {
        HostInvisibilityManager.registerClient();
        ModClientPackets.registerClientPackets();

        PostProcessHandler.addInstance(GrayscaleProcessor.INSTANCE);
        PostProcessHandler.addInstance(DreamscapeProcessor.INSTANCE);

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_LEAVES, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.EVERGLOOM_SAPLING, RenderLayer.getCutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.DEAD_OAK_SAPLING, RenderLayer.getCutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STATUE, RenderLayer.getCutout());

        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.EVERGLOOM_SIGN_TEXTURE));
        SpriteIdentifierRegistry.INSTANCE.addIdentifier(new SpriteIdentifier(TexturedRenderLayers.SIGNS_ATLAS_TEXTURE, ModBlocks.EVERGLOOM_HANGING_SIGN_TEXTURE));

        TerraformBoatClientHelper.registerModelLayers(ModBoats.EVERGLOOM_BOAT_ID, false);


        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.THE_FORGOTTEN, ForgottenModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.THE_FORGOTTEN, ForgottenRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.LILGUY, LilGuyModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.LILGUY, LilGuyRenderer::new);

        EntityModelLayerRegistry.registerModelLayer(ModModelLayers.MENACE, MenaceModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.MENACE, MenaceRenderer::new);

        EntityRendererRegistry.register(ModEntities.STRIKE, OrbitalStrikeRenderer::new);

        //Grayscale Post Processing Handler

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.world == null) {
                GrayscaleProcessor.INSTANCE.setActive(false);
                return;
            }

            RegistryKey<World> current = client.world.getRegistryKey();
            if (current == ModDimensions.LIMBO_DIM_KEY) {
                GrayscaleProcessor.INSTANCE.setActive(true);
            } else {
                GrayscaleProcessor.INSTANCE.setActive(false);
            }
        });

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
    }
}
