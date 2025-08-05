package net.hour.eternity.entity.client;

import net.hour.eternity.Eternity;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class ForgottenRenderer extends MobEntityRenderer<ForgottenEntity, TheForgotten<ForgottenEntity>> {

    private static final Identifier TEXTURE = new Identifier(Eternity.MOD_ID, "textures/entity/forgotten.png");

    public ForgottenRenderer(EntityRendererFactory.Context context) {
        super(context, new TheForgotten<>(context.getPart(ModModelLayers.THE_FORGOTTEN)),
                0.35f);
    }

    @Override
    public Identifier getTexture(ForgottenEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderLayer getRenderLayer(ForgottenEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier texture = this.getTexture(entity);
        return RenderLayer.getEntityTranslucent(texture);
    }
}
