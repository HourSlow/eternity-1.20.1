package net.hour.eternity.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import java.util.List;

public class RevealItem extends Item {

    public RevealItem(Settings settings) {
        super(settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) return;
        if (!(entity instanceof PlayerEntity holder)) return;

        boolean isHolding = holder.getMainHandStack() == stack || holder.getOffHandStack() == stack;

        double searchRadius = 25.0;

        List<LivingEntity> targets = world.getEntitiesByClass(
                LivingEntity.class,
                holder.getBoundingBox().expand(searchRadius),
                e -> e instanceof HostileEntity || (e instanceof PlayerEntity && e != holder)
        );
        for (LivingEntity target : targets) {
            double distance = target.squaredDistanceTo(holder);
            boolean inRange = distance <= 20 * 20;

            if (isHolding && inRange) {
                target.setGlowing(true);
            } else if (target.isGlowing()) {
                target.setGlowing(false);
            }
        }
    }
}
