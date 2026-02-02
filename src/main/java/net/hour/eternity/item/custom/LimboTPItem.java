package net.hour.eternity.item.custom;

import net.fabricmc.fabric.api.dimension.v1.FabricDimensions;
import net.hour.eternity.world.dimension.ModDimensions;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;

public class LimboTPItem extends Item {

    public LimboTPItem(Settings settings) {
        super(settings);
    }


    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        if (user.getWorld().isClient) return ActionResult.SUCCESS;

        if (entity.getWorld() instanceof ServerWorld currentWorld) {
            RegistryKey<World> targetKey = currentWorld.getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY)
                    ? World.OVERWORLD
                    : ModDimensions.LIMBO_DIM_KEY;

            ServerWorld targetWorld = currentWorld.getServer().getWorld(targetKey);

            if (targetWorld != null) {
                TeleportTarget teleportTarget = new TeleportTarget(
                        entity.getPos(),
                        entity.getVelocity(),
                        entity.getYaw(),
                        entity.getPitch()
                );
                FabricDimensions.teleport(entity, targetWorld, teleportTarget);
            }
        }
        return ActionResult.SUCCESS;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack stack = user.getStackInHand(hand);

        if (world.isClient) return TypedActionResult.success(stack);

        ServerPlayerEntity player = (ServerPlayerEntity) user;
        ServerWorld currentWorld = player.getServerWorld();

        if (player.isSneaking()) {
            RegistryKey<World> targetKey = currentWorld.getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY)
                    ? World.OVERWORLD
                    : ModDimensions.LIMBO_DIM_KEY;

            ServerWorld targetWorld = player.getServer().getWorld(targetKey);
            if (targetWorld != null) {
                Vec3d pos = player.getPos();
                player.teleport(targetWorld, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
            }

            return TypedActionResult.success(stack);
        }

        return TypedActionResult.pass(stack);
    }
}
