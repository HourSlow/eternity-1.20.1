package net.hour.eternity.entity.client;

import net.hour.eternity.Eternity;
import net.hour.eternity.entity.custom.LilGuyEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class LilGuyRenderer extends MobEntityRenderer<LilGuyEntity, LilGuyModel<LilGuyEntity>> {

    private static final Identifier TEXTURE = new Identifier(Eternity.MOD_ID,"textures/entity/lilguy.png");

    public LilGuyRenderer(EntityRendererFactory.Context context) {
        super(context, new LilGuyModel<>(context.getPart(ModModelLayers.LILGUY)), 0.5f);
    }

    @Override
    public Identifier getTexture(LilGuyEntity entity) {
        return TEXTURE;
    }

    @Override
    public void render(LilGuyEntity mobEntity, float f, float g, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumerProvider, int i) {

        if (mobEntity.isBaby()) {
            matrixStack.scale(0.7f, 0.7f, 0.7f);
        } else {
            matrixStack.scale(1.2f, 1.2f, 1.2f);
        }

        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
