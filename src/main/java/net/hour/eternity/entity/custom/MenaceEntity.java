package net.hour.eternity.entity.custom;

import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class MenaceEntity extends HostileEntity {

    public final AnimationState idleAnimationStateMenace = new AnimationState();
    private int idleAnimationTimeoutMenace = 0;

    public MenaceEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    private void setupAnimationStates() {
        if (this.idleAnimationTimeoutMenace <= 0) {
            this.idleAnimationTimeoutMenace = this.random.nextInt(40) + 80;
            this.idleAnimationStateMenace.start(this.age);
        } else {
            this.idleAnimationTimeoutMenace--;
        }
    }

    @Override
    protected void updateLimbs(float posDelta) {
        float f;
        if (this.getPose() == EntityPose.STANDING) {
            f = Math.min(posDelta * 6.0F, 1.0F);
        } else {
            f = 0.0F;
        }

        this.limbAnimator.updateLimbs(f, 0.2F);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.getWorld().isClient()) {
            setupAnimationStates();
        }
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1,new LookAtEntityGoal(this, PlayerEntity.class,20f));

        this.goalSelector.add(3,new LookAroundGoal(this));
    }

    public static DefaultAttributeContainer.Builder createTheMenaceAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 500)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.2f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 20)
                .add(EntityAttributes.GENERIC_ARMOR, 500f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 99999);
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ILLUSIONER_DEATH;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isFireImmune() {
        return true;
    }

    @Override
    public boolean isImmuneToExplosion() {
        return true;
    }

    @Override
    protected boolean isDisallowedInPeaceful() {
        return true;
    }

    @Override
    public boolean isAffectedBySplashPotions() {
        return false;
    }
}
