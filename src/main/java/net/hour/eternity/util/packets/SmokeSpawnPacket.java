package net.hour.eternity.util.packets;

import net.minecraft.client.MinecraftClient;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import team.lodestar.lodestone.registry.common.particle.LodestoneParticleRegistry;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.particle.builder.WorldParticleBuilder;
import team.lodestar.lodestone.systems.particle.data.GenericParticleData;
import team.lodestar.lodestone.systems.particle.data.color.ColorParticleData;

import java.awt.*;

public class SmokeSpawnPacket {
    private final Vec3d position;
    private final int startingSmokeColor;
    private final int endingSmokeColor;

    public SmokeSpawnPacket(Vec3d position, int startingSmokeColor, int endingSmokeColor) {
        this.position = position;
        this.startingSmokeColor = startingSmokeColor;
        this.endingSmokeColor = endingSmokeColor;
    }

    public SmokeSpawnPacket(PacketByteBuf buf) {
        this.position = new Vec3d(buf.readDouble(), buf.readDouble(), buf.readDouble());
        this.startingSmokeColor = buf.readInt();
        this.endingSmokeColor = buf.readInt();
    }

    public void toBytes(PacketByteBuf buf) {
        buf.writeDouble(position.x);
        buf.writeDouble(position.y);
        buf.writeDouble(position.z);
        buf.writeInt(startingSmokeColor);
        buf.writeInt(endingSmokeColor);
    }

    public void handle(MinecraftClient client) {
        client.execute(() -> {
            World level = client.world;
            if (level != null) {
                Color starSmokeColor = new Color(startingSmokeColor);
                Color endSmokeColor = new Color(endingSmokeColor);
                spawnSmokeParticles(level, position, starSmokeColor, endSmokeColor);
            }
        });
    }

    public static void spawnSmokeParticles(World level, Vec3d pos, Color startingSmokeColor, Color endingSmokeColor) {
        WorldParticleBuilder.create(LodestoneParticleRegistry.SMOKE_PARTICLE)
                .setScaleData(GenericParticleData.create(1.5f, 5.5f, 0f).build())
                .setTransparencyData(GenericParticleData.create(0.9f, 0f).build())
                .setColorData(ColorParticleData.create(startingSmokeColor, endingSmokeColor).setCoefficient(0.5f).setEasing(Easing.ELASTIC_IN).build())
                .setScaleData(GenericParticleData.create(4f, 4f).build())
                .setLifetime(200)
                .setLifeDelay(40)
                .enableNoClip()
                .spawn(level, pos.x, pos.y, pos.z);

    }
}