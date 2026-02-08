package net.hour.eternity.entity.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.world.World;

import java.util.List;

public class OrbitalStrikeEntity extends Entity {
    public int age = 0;
    public final int maxAge = 40;

    public OrbitalStrikeEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void tick() {
        super.tick();

        if (age++ > maxAge) {
            if (!this.getWorld().isClient) {
                ServerWorld world = (ServerWorld) this.getWorld();
                double x = this.getX();
                double y = this.getY();
                double z = this.getZ();
                world.createExplosion(
                        this,
                        x, y, z,
                        15.0f,
                        true,
                        World.ExplosionSourceType.TNT
                );

                double shockwaveRadius = 20.0;
                List<Entity> entities = world.getOtherEntities(this, this.getBoundingBox().expand(shockwaveRadius));
                for (Entity entity : entities) {
                    if (entity instanceof LivingEntity living) {
                        double dx = living.getX() - x;
                        double dz = living.getZ() - z;
                        living.setVelocity(dx * 0.5, 1.5, dz * 0.5);

                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.WITHER, 200, 2));
                        living.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOWNESS, 100, 4));
                    }
                }

                world.playSound(null, x, y, z, SoundEvents.ENTITY_GENERIC_EXPLODE, SoundCategory.AMBIENT, 10.0f, 0.5f);
                world.playSound(null, x, y, z, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.AMBIENT, 10.0f, 1.0f);

                this.discard();
            }
        }
    }

    @Override protected void initDataTracker() {}
    @Override protected void readCustomDataFromNbt(NbtCompound nbt) {}
    @Override protected void writeCustomDataToNbt(NbtCompound nbt) {}
}
