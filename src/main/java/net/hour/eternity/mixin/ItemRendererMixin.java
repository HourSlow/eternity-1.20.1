package net.hour.eternity.mixin;

import net.hour.eternity.Eternity;
import net.hour.eternity.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public abstract class ItemRendererMixin {
    @ModifyVariable(
            method = "renderItem",
            at = @At(value = "HEAD"),
            argsOnly = true
    )
    public BakedModel useHandHeldModel(BakedModel value, ItemStack stack, ModelTransformationMode renderMode,
                                       boolean leftHanded, MatrixStack matrices,
                                       VertexConsumerProvider vertexConsumers, int light, int overlay) {

        if (stack.isOf(ModItems.MIRROR) && renderMode != ModelTransformationMode.GUI
                && renderMode != ModelTransformationMode.FIXED && renderMode !=ModelTransformationMode.GROUND) {
            return MinecraftClient.getInstance()
                    .getBakedModelManager()
                    .getModel(new ModelIdentifier(new Identifier(Eternity.MOD_ID, "mirror"), "inventory"));
        }

        return value;
    }
}
