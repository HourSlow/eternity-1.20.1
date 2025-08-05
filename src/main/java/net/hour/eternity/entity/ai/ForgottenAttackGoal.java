package net.hour.eternity.entity.ai;

import net.hour.eternity.entity.custom.ForgottenEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.util.Hand;

public class ForgottenAttackGoal extends MeleeAttackGoal {
    private final ForgottenEntity entity;
    private int attackDelay = 5;
    private int ticksUntilNextAttack = 5;
    private boolean shouldCountTillNextAttack = false;

    public ForgottenAttackGoal(PathAwareEntity mob, double speed, boolean pauseWhenMobIdle) {
        super(mob, speed, pauseWhenMobIdle);
        entity = ((ForgottenEntity) mob);
    }

    @Override
    public void start() {
        super.start();
        attackDelay = 5;
        ticksUntilNextAttack = 5;
    }

    @Override
    protected void attack(LivingEntity target, double squaredDistance) {
        if (isEnemyWithinAttackDistance(target)) {
            shouldCountTillNextAttack = true;

            if (isTimeToStartAttackAnimation()) {
                entity.setAttacking(true);
            }

            if (isTimeToAttack()) {
                this.mob.getLookControl().lookAt(target.getX(), target.getEyeY(), target.getZ());
                performAttack(target);
            }
        } else {
            resetAttackCooldown();
            shouldCountTillNextAttack = false;
            entity.setAttacking(false);
            entity.attackAnimationTimeout = 0;
        }
    }

    private boolean isEnemyWithinAttackDistance(LivingEntity pEnemy) {
        return this.entity.distanceTo(pEnemy) <= 2f; // TODO
    }

    protected void resetAttackCooldown() {
        this.ticksUntilNextAttack = this.getTickCount(attackDelay * 2);
    }

    protected boolean isTimeToStartAttackAnimation() {
        return this.ticksUntilNextAttack <= attackDelay;
    }

    protected boolean isTimeToAttack() {
        return this.ticksUntilNextAttack <= 0;
    }

    protected void performAttack(LivingEntity pEnemy) {
        this.resetAttackCooldown();
        this.mob.swingHand(Hand.MAIN_HAND);
        this.mob.tryAttack(pEnemy);
    }

    @Override
    public void tick() {
        super.tick();
        if (shouldCountTillNextAttack) {
            this.ticksUntilNextAttack = Math.max(this.ticksUntilNextAttack - 1, 0);
        }
    }

    @Override
    public void stop() {
        entity.setAttacking(false);
        super.stop();
    }

}
