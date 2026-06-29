package net.hour.eternity.entity.custom;

import net.hour.eternity.entity.ai.FreezeWhenInViewGoal;
import net.hour.eternity.entity.ai.MenaceAttackGoal;
import net.minecraft.entity.AnimationState;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

public class MenaceEntity extends HostileEntity {

    public final AnimationState idleAnimationStateMenace = new AnimationState();
    private int idleAnimationTimeoutMenace = 0;

    public final AnimationState attackAnimationStateMenace = new AnimationState();
    public int attackAnimationTimeoutMenace = 0;

    private static final TrackedData<Boolean> ATTACKING_MENACE =
            DataTracker.registerData(MenaceEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

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
        if (this.isAttacking() && attackAnimationTimeoutMenace <=0) {
            attackAnimationTimeoutMenace = 40;
            attackAnimationStateMenace.start(this.age);
        } else {
            --this.attackAnimationTimeoutMenace;
        }
        if (!this.isAttacking()) {
            attackAnimationStateMenace.stop();
        }
    }


    public void setAttackingMenace(boolean attackingMenace) {
        this.dataTracker.set(ATTACKING_MENACE, attackingMenace);
    }

    @Override
    public boolean isAttacking() {
        return this.dataTracker.get(ATTACKING_MENACE);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.dataTracker.startTracking(ATTACKING_MENACE, false);
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
        this.goalSelector.add(1, new FreezeWhenInViewGoal(this));
        this.goalSelector.add(2, new MenaceAttackGoal(this, 1.1d,true));
        this.goalSelector.add(3,new LookAtEntityGoal(this, PlayerEntity.class,30f));
        this.goalSelector.add(4,new WanderAroundFarGoal(this, 1d));

        this.targetSelector.add(1, new ActiveTargetGoal<>(this, PlayerEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createTheMenaceAttributes() {
        return MobEntity.createMobAttributes()
                .add(EntityAttributes.GENERIC_MAX_HEALTH, 30)
                .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.4f)
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 25)
                .add(EntityAttributes.GENERIC_ARMOR, 0f)
                .add(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE, 99999);
    }


    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.BLOCK_WOOD_HIT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_HUSK_DEATH;
    }


    @Override
    public boolean damage(DamageSource source, float amount) {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return super.damage(source, amount);
        }

        if (source.isIn(DamageTypeTags.IS_FIRE)) {
            return super.damage(source, amount);
        }

        return false;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean isFireImmune() {
        return false;
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
