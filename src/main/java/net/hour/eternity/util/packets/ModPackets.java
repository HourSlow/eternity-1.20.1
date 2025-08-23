package net.hour.eternity.util.packets;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hour.eternity.Eternity;
import net.minecraft.util.Identifier;

public class ModPackets {

    public static final Identifier BEAM_SPAWN_ID = new Identifier(Eternity.MOD_ID, "beam_spawn");

    public static void registerPackets() {
        ServerPlayNetworking.registerGlobalReceiver(BEAM_SPAWN_ID, (server, player, handler, buf, responseSender) -> {
            BeamSpawnPacket packet = new BeamSpawnPacket(buf);
            server.execute(() -> {
                // Server-side handling if needed
            });
        });
    }
}
