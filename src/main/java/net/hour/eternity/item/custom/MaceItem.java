package net.hour.eternity.item.custom;

import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Vanishable;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.UUID;
import java.util.function.Predicate;

public class MaceItem extends Item implements Vanishable {
    private static final UUID ATTACK_DAMAGE_MODIFIER_ID = UUID.fromString("CB3F55D3-645C-4F38-A497-9C13A33DB5CF");
    private static final UUID ATTACK_SPEED_MODIFIER_ID = UUID.fromString("FA233E1C-4180-4865-B01B-BCCE9785ACA3");

    private final Multimap<EntityAttribute, EntityAttributeModifier> attributeModifiers;

    public MaceItem(Item.Settings settings) {
        super(settings);
        ImmutableMultimap.Builder<EntityAttribute, EntityAttributeModifier> builder = ImmutableMultimap.builder();
        builder.put(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(ATTACK_DAMAGE_MODIFIER_ID, "Weapon modifier", 5.0, EntityAttributeModifier.Operation.ADDITION));
        builder.put(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(ATTACK_SPEED_MODIFIER_ID, "Weapon modifier", -3.4F, EntityAttributeModifier.Operation.ADDITION));
        this.attributeModifiers = builder.build();
    }

    @Override
    public Multimap<EntityAttribute, EntityAttributeModifier> getAttributeModifiers(EquipmentSlot slot) {
        return slot == EquipmentSlot.MAINHAND ? this.attributeModifiers : super.getAttributeModifiers(slot);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (shouldDealAdditionalDamage(attacker)) {
            World world = attacker.getWorld();

            if (world instanceof ServerWorld serverWorld) {
                attacker.setVelocity(attacker.getVelocity().withAxis(Direction.Axis.Y, 0.01F));

                if (attacker instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket(serverPlayer));
                }

                float bonusDamage = getBonusDamage(attacker);
                target.damage(attacker.getDamageSources().mobAttack(attacker), bonusDamage);

                if (target.isOnGround()) {
                    SoundEvent sound = attacker.fallDistance > 5.0F ? SoundEvents.BLOCK_ANVIL_LAND : SoundEvents.BLOCK_GRINDSTONE_USE;
                    world.playSound(null, attacker.getX(), attacker.getY(), attacker.getZ(), sound, attacker.getSoundCategory(), 0.6F, 1.0F);
                }
                knockbackNearbyEntities(world, attacker, target);
            }
            attacker.onLanding();
        }
        return true;
    }

    private float getBonusDamage(LivingEntity attacker) {
        float f = attacker.fallDistance;
        float g;
        if (f <= 3.0F) {
            g = 4.0F * f;
        } else if (f <= 8.0F) {
            g = 12.0F + 2.0F * (f - 3.0F);
        } else {
            g = 22.0F + (f - 8.0F);
        }
        return g;
    }

    public static boolean shouldDealAdditionalDamage(LivingEntity attacker) {
        return attacker.fallDistance > 1.5F && !attacker.isFallFlying();
    }

    private static void knockbackNearbyEntities(World world, Entity attacker, Entity attacked) {
        world.getEntitiesByClass(LivingEntity.class, attacked.getBoundingBox().expand(3.5), getKnockbackPredicate(attacker, attacked)).forEach(entity -> {
            Vec3d vec3d = entity.getPos().subtract(attacked.getPos());
            double d = getKnockback(attacker, entity, vec3d);
            Vec3d velocity = vec3d.normalize().multiply(d);
            if (d > 0.0) {
                entity.addVelocity(velocity.x, 0.7F, velocity.z);
                if (entity instanceof ServerPlayerEntity serverPlayer) {
                    serverPlayer.networkHandler.sendPacket(new net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket(serverPlayer));
                }
            }
        });
    }

    private static Predicate<LivingEntity> getKnockbackPredicate(Entity attacker, Entity attacked) {
        return entity -> {
            boolean isValid = !entity.isSpectator() && entity != attacker && entity != attacked;
            boolean isNotTeammate = !attacker.isTeammate(entity);
            boolean isNotTamed = !(entity instanceof TameableEntity tameable && attacked instanceof LivingEntity living && tameable.isTamed() && tameable.isOwner(living));
            boolean isNotMarker = !(entity instanceof ArmorStandEntity armorStand && armorStand.isMarker());
            return isValid && isNotTeammate && isNotTamed && isNotMarker;
        };
    }

    private static double getKnockback(Entity attacker, LivingEntity attacked, Vec3d distance) {
        double resistance = attacked.getAttributeValue(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        return (3.5 - distance.length()) * 0.7F * (attacker.fallDistance > 5.0F ? 2 : 1) * (1.0 - resistance);
    }
}
