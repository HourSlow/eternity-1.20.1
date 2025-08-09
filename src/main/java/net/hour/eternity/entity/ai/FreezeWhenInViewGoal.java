package net.hour.eternity.entity.ai;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;
import java.util.List;

public class FreezeWhenInViewGoal extends Goal {
    private final MobEntity mob;
    private float frozenYaw;
    private float frozenPitch;

    public FreezeWhenInViewGoal(MobEntity mob) {
        this.mob = mob;
        // When active, this goal controls these so other goals can't
        this.setControls(EnumSet.of(Control.MOVE, Control.LOOK, Control.JUMP));
    }

    @Override
    public boolean canStart() {
        return isAnyPlayerSeeingMob();
    }

    @Override
    public boolean shouldContinue() {
        return isAnyPlayerSeeingMob();
    }

    @Override
    public void start() {
        frozenYaw = mob.getYaw();
        frozenPitch = mob.getPitch();
        mob.getNavigation().stop();
    }

    @Override
    public void tick() {
        // Block all horizontal motion, let gravity happen
        mob.setVelocity(0, mob.getVelocity().y, 0);

        // No AI-driven movement
        mob.getNavigation().stop();
        mob.setJumping(false);
        mob.setSprinting(false);

        // Freeze head/body rotation
        mob.headYaw = frozenYaw;
        mob.setYaw(frozenYaw);
        mob.setPitch(frozenPitch);

        // Prevent fall damage
        mob.fallDistance = 0;
    }

    private boolean isAnyPlayerSeeingMob() {
        List<PlayerEntity> players = mob.getWorld().getEntitiesByClass(
                PlayerEntity.class,
                mob.getBoundingBox().expand(16.0D),
                player -> player.isAlive() && !player.isSpectator()
        );

        for (PlayerEntity player : players) {
            if (isMobInPlayerFov(player, mob) && player.canSee(mob)) {
                return true;
            }
        }
        return false;
    }

    private boolean isMobInPlayerFov(PlayerEntity player, Entity target) {
        Vec3d playerLook = player.getRotationVec(1.0F).normalize();
        Vec3d toEntity = target.getEyePos().subtract(player.getEyePos()).normalize();

        double dot = playerLook.dotProduct(toEntity);
        double angle = Math.toDegrees(Math.acos(dot));

        return angle < 60.0; // ~on-screen
    }
}
