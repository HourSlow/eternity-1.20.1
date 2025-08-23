package net.hour.eternity.util.packets;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.minecraft.client.MinecraftClient;
import team.lodestar.lodestone.handlers.ScreenshakeHandler;
import team.lodestar.lodestone.systems.easing.Easing;
import team.lodestar.lodestone.systems.screenshake.ScreenshakeInstance;

public class ModClientPackets {

    public static void registerClientPackets() {
        ClientPlayNetworking.registerGlobalReceiver(ModPackets.BEAM_SPAWN_ID, (client, handler, buf, responseSender) -> {
            BeamSpawnPacket packet = new BeamSpawnPacket(buf);
            packet.handle(MinecraftClient.getInstance());
            int durationTicks = 80;
            double iStart = 0.8f;                // start intensity
            double iSustain = 0.5f;              // sustain intensity
            double iEnd = 0.01f;                  // end intensity

            ScreenshakeInstance inst = new ScreenshakeInstance(durationTicks)
                    .setIntensity((float) iStart, (float) iSustain, (float) iEnd)
                    .setEasing(Easing.ELASTIC_IN, Easing.ELASTIC_OUT);

            ScreenshakeHandler.addScreenshake(inst);
        });
    }
}
