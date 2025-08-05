package net.hour.eternity.entity.client;

import net.hour.eternity.entity.animation.ModAnimations;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

public class ForgottenModel<T extends ForgottenEntity> extends SinglePartEntityModel<T> {
	private final ModelPart forgotten;
	private final ModelPart right_arm;
	private final ModelPart left_arm;
	private final ModelPart torso;
	private final ModelPart left_leg;
	private final ModelPart right_leg;
	private final ModelPart head;
	public ForgottenModel(ModelPart root) {
		this.forgotten = root.getChild("forgotten");
		this.right_arm = this.forgotten.getChild("right_arm");
		this.left_arm = this.forgotten.getChild("left_arm");
		this.torso = this.forgotten.getChild("torso");
		this.left_leg = this.forgotten.getChild("left_leg");
		this.right_leg = this.forgotten.getChild("right_leg");
		this.head = this.forgotten.getChild("head");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData forgotten = modelPartData.addChild("forgotten", ModelPartBuilder.create(), ModelTransform.pivot(6.0F, 12.0F, 0.0F));

		ModelPartData right_arm = forgotten.addChild("right_arm", ModelPartBuilder.create().uv(16, 32).cuboid(-4.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-10.0F, -10.0F, 0.0F));

		ModelPartData left_arm = forgotten.addChild("left_arm", ModelPartBuilder.create().uv(32, 0).cuboid(0.0F, -2.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-2.0F, -10.0F, 0.0F));

		ModelPartData torso = forgotten.addChild("torso", ModelPartBuilder.create().uv(0, 16).cuboid(-4.0F, -6.0F, -2.0F, 8.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -6.0F, 0.0F));

		ModelPartData left_leg = forgotten.addChild("left_leg", ModelPartBuilder.create().uv(0, 32).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-4.0F, 0.0F, 0.0F));

		ModelPartData right_leg = forgotten.addChild("right_leg", ModelPartBuilder.create().uv(24, 16).cuboid(-2.0F, 0.0F, -2.0F, 4.0F, 12.0F, 4.0F, new Dilation(0.0F)), ModelTransform.pivot(-8.0F, 0.0F, 0.0F));

		ModelPartData head = forgotten.addChild("head", ModelPartBuilder.create().uv(0, 0).cuboid(-4.0F, -8.0F, -4.0F, 8.0F, 8.0F, 8.0F, new Dilation(0.0F)), ModelTransform.pivot(-6.0F, -12.0F, 0.0F));
		return TexturedModelData.of(modelData, 64, 64);
	}
	@Override
	public void setAngles(ForgottenEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.WALK, limbSwing, limbSwingAmount, 0f, 0f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.attackAnimationState, ModAnimations.ATTACK, ageInTicks, 1f);
	}

	private void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
		headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		forgotten.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return forgotten;
	}
}