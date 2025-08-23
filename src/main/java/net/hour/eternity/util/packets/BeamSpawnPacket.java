package net.hour.eternity.util.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;

public class BeamSpawnPacket {
    private final Vec3d position;
    private final int startingColor;
    private final int endingColor;

    public BeamSpawnPacket(Vec3d position, int startingColor, int endingColor) {
        this.position = position;
        this.startingColor = startingColor;
        this.endingColor = endingColor;
    }

    public BeamSpawnPacket(PacketByteBuf buf) {
        this.position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.startingColor = buf.readInt();
        this.endingColor = buf.readInt();
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
        buf.writeInt(startingColor);
        buf.writeInt(endingColor);
    }

    public void handle(MinecraftClient client) {
        client.execute(() -> {
            World level = client.world;
            if (level != null) {
                Color startColor = new Color(startingColor);
                Color endColor = new Color(endingColor);
                spawnBeamParticles(level, position, startColor, endColor);
            }
        });
    }

    public static void spawnBeamParticles(World level, Vec3d pos, Color startingColor, Color endingColor) {
        WorldParticleBuilder.create(LodestoneParticleRegistry.WISP_PARTICLE)
                .setScaleData(GenericParticleData.create(1f, 0.5f, 0f).build())
                .setTransparencyData(GenericParticleData.create(1f, 0f).build())
                .setColorData(ColorParticleData.create(startingColor, endingColor).setCoefficient(3.5f).setEasing(Easing.BOUNCE_IN_OUT).build())
                .setScaleData(GenericParticleData.create(5f, 5f).build())
                .setLifetime(210)
                .repeatRandomFace(level, BlockPos.ofFloored(pos), 50)
                .enableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);

    }
}