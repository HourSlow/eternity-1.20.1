package net.hour.eternity.entity.client;

import net.hour.eternity.entity.custom.OrbitalStrikeEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BeaconBlockEntityRenderer;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class OrbitalStrikeRenderer extends EntityRenderer<OrbitalStrikeEntity> {
    public static final Identifier BEAM_TEXTURE = new Identifier("textures/entity/beacon_beam.png");

    public OrbitalStrikeRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(OrbitalStrikeEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        long worldTime = entity.getWorld().getTime();

        float[] color = {1.0f, 0.2f, 0.2f}; // RGB Colors

        matrices.push();
        float innerRadius = 1.5f;
        float outerRadius = 2.2f;

        BeaconBlockEntityRenderer.renderBeam(
                matrices,
                vertexConsumers,
                BEAM_TEXTURE,
                tickDelta,
                1.0f,
                worldTime,
                0,
                256,
                color,
                innerRadius,
                outerRadius
        );
        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
    @Override
    public Identifier getTexture(OrbitalStrikeEntity entity) {
        return BEAM_TEXTURE;
    }
}
