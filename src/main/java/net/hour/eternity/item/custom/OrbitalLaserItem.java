package net.hour.eternity.item.custom;

import io.netty.buffer.Unpooled;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hour.eternity.util.packets.ModPackets;
import net.hour.eternity.util.packets.BeamSpawnPacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

import java.awt.*;

public class OrbitalLaserItem extends Item {

    public OrbitalLaserItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClient()) {
            BlockHitResult hitResult = raycast(world, player, 50.0);

            if (hitResult.getType() == HitResult.Type.BLOCK) {
                BlockPos blockPos = hitResult.getBlockPos();
                Vec3d targetPos = Vec3d.ofCenter(blockPos);

                Color startingColor = new Color(200, 82, 0);
                Color endingColor = new Color(105, 17, 3);
                BeamSpawnPacket packet = new BeamSpawnPacket(targetPos, startingColor.getRGB(), endingColor.getRGB());
                PacketByteBuf buf = new PacketByteBuf(Unpooled.buffer());
                packet.toBytes(buf);

                ServerPlayNetworking.send((ServerPlayerEntity) player, ModPackets.BEAM_SPAWN_ID, buf);

                world.createExplosion(
                        null,
                        targetPos.x,
                        targetPos.y,
                        targetPos.z,
                        24.0F,
                        World.ExplosionSourceType.BLOCK
                );

                ScreenshakeHandler.addScreenshake(new ScreenshakeInstance(40));

                return TypedActionResult.success(player.getStackInHand(hand));
            }
        }
        return TypedActionResult.pass(player.getStackInHand(hand));
    }



    public static BlockHitResult raycast(World world, PlayerEntity player, double maxDistance) {
        float tickDelta = 1.0F;
        Vec3d eyePos = player.getCameraPosVec(tickDelta);
        Vec3d lookVec = player.getRotationVec(tickDelta);
        Vec3d endPos = eyePos.add(lookVec.multiply(maxDistance));

        return world.raycast(new RaycastContext(
                eyePos,
                endPos,
                RaycastContext.ShapeType.OUTLINE,
                RaycastContext.FluidHandling.NONE,
                player
        ));
    }

}