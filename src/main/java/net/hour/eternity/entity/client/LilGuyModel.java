package net.hour.eternity.entity.client;

import net.hour.eternity.entity.animation.ModAnimations;
import net.hour.eternity.entity.custom.LilGuyEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.12.5
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class LilGuyModel<T extends LilGuyEntity> extends SinglePartEntityModel<T> {
	private final ModelPart lilguy;
	private final ModelPart torso;
	private final ModelPart front_legs;
	private final ModelPart front_left_leg;
	private final ModelPart front_right_leg;
	private final ModelPart back_legs;
	private final ModelPart back_right_leg;
	private final ModelPart back_left_leg;
	private final ModelPart head;
	private final ModelPart lower_head;
	private final ModelPart upper_head;
	public LilGuyModel(ModelPart root) {
		this.lilguy = root.getChild("lilguy");
		this.torso = this.lilguy.getChild("torso");
		this.front_legs = this.lilguy.getChild("front_legs");
		this.front_left_leg = this.front_legs.getChild("front_left_leg");
		this.front_right_leg = this.front_legs.getChild("front_right_leg");
		this.back_legs = this.lilguy.getChild("back_legs");
		this.back_right_leg = this.back_legs.getChild("back_right_leg");
		this.back_left_leg = this.back_legs.getChild("back_left_leg");
		this.head = lilguy.getChild("head");
		this.lower_head = this.head.getChild("lower_head");
		this.upper_head = this.head.getChild("upper_head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData lilguy = modelPartData.addChild("lilguy", ModelPartBuilder.create(), ModelTransform.pivot(-1.0F, 24.0F, 0.0F));

		ModelPartData torso = lilguy.addChild("torso", ModelPartBuilder.create().uv(0, 28).cuboid(-3.0F, -1.0F, -4.0F, 6.0F, 2.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -3.0F, 0.0F));

		ModelPartData front_legs = lilguy.addChild("front_legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData front_left_leg = front_legs.addChild("front_left_leg", ModelPartBuilder.create().uv(26, 34).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -3.0F, -2.8F));

		ModelPartData front_right_leg = front_legs.addChild("front_right_leg", ModelPartBuilder.create().uv(26, 28).cuboid(-1.0F, 0.0F, -1.0F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -3.0F, -2.8F));

		ModelPartData back_legs = lilguy.addChild("back_legs", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData back_right_leg = back_legs.addChild("back_right_leg", ModelPartBuilder.create().uv(35, 34).cuboid(-1.0F, 0.0F, -0.75F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.5F, -3.0F, 2.2F));

		ModelPartData back_left_leg = back_legs.addChild("back_left_leg", ModelPartBuilder.create().uv(35, 29).cuboid(-1.0F, 0.0F, -0.75F, 2.0F, 3.0F, 2.0F, new Dilation(0.0F)), ModelTransform.pivot(3.5F, -3.0F, 2.2F));

		ModelPartData head = lilguy.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData lower_head = head.addChild("lower_head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, 0.0F, -11.0F, 8.0F, 3.0F, 11.0F, new Dilation(0.0F))
		.uv(0, 53).cuboid(-3.0F, -1.0F, -10.0F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -7.0F, 4.0F));

		ModelPartData upper_head = head.addChild("upper_head", ModelPartBuilder.create().uv(0, 14).cuboid(-4.0F, -3.0F, -11.0F, 8.0F, 3.0F, 11.0F, new Dilation(0.0F))
		.uv(34, 53).cuboid(-3.0F, 0.0F, -10.0F, 6.0F, 1.0F, 9.0F, new Dilation(0.0F)), ModelTransform.pivot(1.0F, -7.0F, 4.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}

    @Override
    public void setAngles(LilGuyEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(ModAnimations.WALK_LIL, limbSwing, limbSwingAmount, 1.5f, 2.5f);
        this.updateAnimation(entity.idleAnimationStateLilGuy, ModAnimations.IDLE_LIL, ageInTicks, 1f);
    }

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		lilguy.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
        return lilguy;
    }
}