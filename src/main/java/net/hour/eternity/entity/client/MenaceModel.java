package net.hour.eternity.entity.client;

import net.hour.eternity.entity.animation.ModAnimations;
import net.hour.eternity.entity.custom.MenaceEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.12.6
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class MenaceModel<T extends MenaceEntity> extends SinglePartEntityModel<T> {
	private final ModelPart menace;
	private final ModelPart torso;
	private final ModelPart leg_left;
	private final ModelPart upper_leg_left;
	private final ModelPart lower_leg_left;
	private final ModelPart leg_right;
	private final ModelPart lower_leg_right;
	private final ModelPart upper_leg_right;
	private final ModelPart arm_right;
	private final ModelPart upper_arm_right;
	private final ModelPart lower_arm_right;
	private final ModelPart fingers_right;
	private final ModelPart finger3;
	private final ModelPart finger2;
	private final ModelPart finger1;
	private final ModelPart arm_left;
	private final ModelPart upper_arm_left;
	private final ModelPart lower_arm_left;
	private final ModelPart fingers_left;
	private final ModelPart finger4;
	private final ModelPart finger5;
	private final ModelPart finger6;
	private final ModelPart head;
	private final ModelPart cranium;
	private final ModelPart horns;
	private final ModelPart horn1;
	private final ModelPart horn2;
	private final ModelPart yaw;
	public MenaceModel(ModelPart root) {
		this.menace = root.getChild("menace");
		this.torso = this.menace.getChild("torso");
		this.leg_left = this.menace.getChild("leg_left");
		this.upper_leg_left = this.leg_left.getChild("upper_leg_left");
		this.lower_leg_left = this.leg_left.getChild("lower_leg_left");
		this.leg_right = this.menace.getChild("leg_right");
		this.lower_leg_right = this.leg_right.getChild("lower_leg_right");
		this.upper_leg_right = this.leg_right.getChild("upper_leg_right");
		this.arm_right = this.menace.getChild("arm_right");
		this.upper_arm_right = this.arm_right.getChild("upper_arm_right");
		this.lower_arm_right = this.arm_right.getChild("lower_arm_right");
		this.fingers_right = this.lower_arm_right.getChild("fingers_right");
		this.finger3 = this.fingers_right.getChild("finger3");
		this.finger2 = this.fingers_right.getChild("finger2");
		this.finger1 = this.fingers_right.getChild("finger1");
		this.arm_left = this.menace.getChild("arm_left");
		this.upper_arm_left = this.arm_left.getChild("upper_arm_left");
		this.lower_arm_left = this.arm_left.getChild("lower_arm_left");
		this.fingers_left = this.lower_arm_left.getChild("fingers_left");
		this.finger4 = this.fingers_left.getChild("finger4");
		this.finger5 = this.fingers_left.getChild("finger5");
		this.finger6 = this.fingers_left.getChild("finger6");
		this.head = menace.getChild("head");
		this.cranium = this.head.getChild("cranium");
		this.horns = this.cranium.getChild("horns");
		this.horn1 = this.horns.getChild("horn1");
		this.horn2 = this.horns.getChild("horn2");
		this.yaw = this.head.getChild("yaw");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData menace = modelPartData.addChild("menace", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 24.0F, 0.0F));

		ModelPartData torso = menace.addChild("torso", ModelPartBuilder.create().uv(31, 32).cuboid(-5.0F, 0.0F, -3.0F, 10.0F, 12.0F, 5.0F, new Dilation(0.0F))
		.uv(8, 94).cuboid(-5.0F, 12.0F, -3.0F, 10.0F, 2.0F, 5.0F, new Dilation(0.0F))
		.uv(0, 32).cuboid(-5.0F, -13.0F, -3.0F, 10.0F, 13.0F, 5.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -44.0F, 0.0F));

		ModelPartData leg_left = menace.addChild("leg_left", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -32.0F, 0.0F));

		ModelPartData upper_leg_left = leg_left.addChild("upper_leg_left", ModelPartBuilder.create().uv(57, 70).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 16.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData lower_leg_left = leg_left.addChild("lower_leg_left", ModelPartBuilder.create().uv(-6, 75).cuboid(-3.0F, 16.0F, -4.0F, 7.0F, 0.0F, 7.0F, new Dilation(0.0F))
		.uv(57, 50).cuboid(-1.0F, 0.0F, -2.0F, 3.0F, 16.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData leg_right = menace.addChild("leg_right", ModelPartBuilder.create(), ModelTransform.pivot(-2.0F, -32.0F, 0.0F));

		ModelPartData lower_leg_right = leg_right.addChild("lower_leg_right", ModelPartBuilder.create().uv(62, 20).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 16.0F, 3.0F, new Dilation(0.0F))
		.uv(11, 75).cuboid(-4.0F, 16.0F, -4.0F, 7.0F, 0.0F, 7.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 16.0F, 0.0F));

		ModelPartData upper_leg_right = leg_right.addChild("upper_leg_right", ModelPartBuilder.create().uv(70, 40).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 16.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData arm_right = menace.addChild("arm_right", ModelPartBuilder.create(), ModelTransform.pivot(-5.0F, -55.0F, 0.0F));

		ModelPartData upper_arm_right = arm_right.addChild("upper_arm_right", ModelPartBuilder.create().uv(31, 50).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 20.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 0.0F, 0.0F));

		ModelPartData lower_arm_right = arm_right.addChild("lower_arm_right", ModelPartBuilder.create().uv(44, 50).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 20.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(-1.0F, 18.0F, 0.0F));

		ModelPartData fingers_right = lower_arm_right.addChild("fingers_right", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 21.0F, -2.0F));

		ModelPartData finger3 = fingers_right.addChild("finger3", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r1 = finger3.addChild("cube_r1", ModelPartBuilder.create().uv(26, 51).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r2 = finger3.addChild("cube_r2", ModelPartBuilder.create().uv(62, 40).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData finger2 = fingers_right.addChild("finger2", ModelPartBuilder.create(), ModelTransform.pivot(-1.8F, 0.0F, 0.0F));

		ModelPartData cube_r3 = finger2.addChild("cube_r3", ModelPartBuilder.create().uv(26, 55).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r4 = finger2.addChild("cube_r4", ModelPartBuilder.create().uv(62, 44).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData finger1 = fingers_right.addChild("finger1", ModelPartBuilder.create(), ModelTransform.of(0.2F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r5 = finger1.addChild("cube_r5", ModelPartBuilder.create().uv(58, 20).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r6 = finger1.addChild("cube_r6", ModelPartBuilder.create().uv(26, 63).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData arm_left = menace.addChild("arm_left", ModelPartBuilder.create(), ModelTransform.pivot(5.0F, -55.0F, 0.0F));

		ModelPartData upper_arm_left = arm_left.addChild("upper_arm_left", ModelPartBuilder.create().uv(0, 51).cuboid(-2.0F, -2.0F, -2.0F, 3.0F, 20.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 0.0F, 0.0F));

		ModelPartData lower_arm_left = arm_left.addChild("lower_arm_left", ModelPartBuilder.create().uv(13, 51).cuboid(-2.0F, 0.0F, -2.0F, 3.0F, 20.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.0F, 18.0F, 0.0F));

		ModelPartData fingers_left = lower_arm_left.addChild("fingers_left", ModelPartBuilder.create(), ModelTransform.of(-1.0F, 21.0F, 1.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData finger4 = fingers_left.addChild("finger4", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r7 = finger4.addChild("cube_r7", ModelPartBuilder.create().uv(58, 24).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r8 = finger4.addChild("cube_r8", ModelPartBuilder.create().uv(65, 40).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData finger5 = fingers_left.addChild("finger5", ModelPartBuilder.create(), ModelTransform.pivot(-1.8F, 0.0F, 0.0F));

		ModelPartData cube_r9 = finger5.addChild("cube_r9", ModelPartBuilder.create().uv(58, 28).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r10 = finger5.addChild("cube_r10", ModelPartBuilder.create().uv(65, 44).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData finger6 = fingers_left.addChild("finger6", ModelPartBuilder.create(), ModelTransform.of(0.2F, 0.0F, 3.0F, 0.0F, 3.1416F, 0.0F));

		ModelPartData cube_r11 = finger6.addChild("cube_r11", ModelPartBuilder.create().uv(26, 59).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 1.9F, 0.0F, 0.1745F, 0.0F, 0.0F));

		ModelPartData cube_r12 = finger6.addChild("cube_r12", ModelPartBuilder.create().uv(26, 67).cuboid(0.0F, -2.0F, -1.0F, 1.0F, 3.0F, 0.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, -0.6981F, 0.0F, 0.0F));

		ModelPartData head = menace.addChild("head", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, -57.0F, 0.0F));

		ModelPartData cranium = head.addChild("cranium", ModelPartBuilder.create().uv(0, 0).cuboid(-5.0F, -7.0F, -10.0F, 10.0F, 7.0F, 10.0F, new Dilation(0.0F))
		.uv(41, 10).cuboid(-4.0F, 0.0F, -9.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
		.uv(3, 84).cuboid(-5.0F, -9.0F, -10.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -3.0F, 4.0F));

		ModelPartData horns = cranium.addChild("horns", ModelPartBuilder.create(), ModelTransform.pivot(2.0F, -4.0F, -4.0F));

		ModelPartData horn1 = horns.addChild("horn1", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData cube_r13 = horn1.addChild("cube_r13", ModelPartBuilder.create().uv(70, 71).cuboid(-1.0F, -6.0F, -1.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData cube_r14 = horn1.addChild("cube_r14", ModelPartBuilder.create().uv(41, 20).cuboid(-1.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData horn2 = horns.addChild("horn2", ModelPartBuilder.create(), ModelTransform.of(-5.0F, 2.0F, 0.0F, 3.1416F, 0.0F, 2.7053F));

		ModelPartData cube_r15 = horn2.addChild("cube_r15", ModelPartBuilder.create().uv(74, 0).cuboid(-1.0F, -6.0F, -1.0F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.of(3.0F, -3.0F, 0.0F, 0.0F, 0.0F, -0.3054F));

		ModelPartData cube_r16 = horn2.addChild("cube_r16", ModelPartBuilder.create().uv(70, 60).cuboid(-1.0F, -6.0F, -2.0F, 4.0F, 6.0F, 4.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.6545F));

		ModelPartData yaw = head.addChild("yaw", ModelPartBuilder.create().uv(0, 18).cuboid(-5.0F, -1.0F, -10.0F, 10.0F, 3.0F, 10.0F, new Dilation(0.0F))
		.uv(41, 0).cuboid(-4.0F, -2.0F, -9.0F, 8.0F, 1.0F, 8.0F, new Dilation(0.0F))
		.uv(0, 89).cuboid(-5.0F, 2.0F, -10.0F, 10.0F, 2.0F, 10.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, -2.0F, 4.0F));
		return TexturedModelData.of(modelData, 128, 128);
	}
	@Override
	public void setAngles(MenaceEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        this.setHeadAngles(netHeadYaw, headPitch);

        this.animateMovement(ModAnimations.WALK_MENACE, limbSwing, limbSwingAmount, 1.0f, 1.0f);
        this.updateAnimation(entity.idleAnimationStateMenace, ModAnimations.IDLE_MENACE, ageInTicks, 1f);
	}

    private void setHeadAngles(float headYaw, float headPitch) {
        headYaw = MathHelper.clamp(headYaw, -30.0F, 30.0F);
        headPitch = MathHelper.clamp(headPitch, -25.0F, 45.0F);

        this.head.yaw = headYaw * 0.017453292F;
        this.head.pitch = headPitch * 0.017453292F;
    }

	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		menace.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

    @Override
    public ModelPart getPart() {
        return menace;
    }
}