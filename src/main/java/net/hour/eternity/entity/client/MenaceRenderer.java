package net.hour.eternity.entity.client;

import net.hour.eternity.Eternity;
import net.hour.eternity.entity.custom.MenaceEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class MenaceRenderer extends MobEntityRenderer<MenaceEntity, MenaceModel<MenaceEntity>> {

    private static final Identifier TEXTURE = new Identifier(Eternity.MOD_ID, "textures/entity/menace.png");

    public MenaceRenderer(EntityRendererFactory.Context context) {
        super(context, new MenaceModel<>(context.getPart(ModModelLayers.MENACE)),
                0.7f);
    }

    @Override
    public Identifier getTexture(MenaceEntity entity) {
        return TEXTURE;
    }

    @Override
    protected @Nullable RenderLayer getRenderLayer(MenaceEntity entity, boolean showBody, boolean translucent, boolean showOutline) {
        Identifier texture = this.getTexture(entity);
        return RenderLayer.getEntityTranslucent(texture);
    }
}
