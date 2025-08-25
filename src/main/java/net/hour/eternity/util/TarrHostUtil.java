package net.hour.eternity.util;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class TarrHostUtil {

    private static final UUID GODMODE_UUID = UUID.fromString("f06622d6-fb39-419d-9eed-535aaef3c894");

    public static void register() {
        // Cancel damage
        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {
                if (player.getUuid().equals(GODMODE_UUID)) {
                    return false; // deny damage
                }
            }
            return true; // allow others
        });

        // Give flight every tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.getUuid().equals(GODMODE_UUID)) {
                    if (!player.getAbilities().allowFlying) {
                        player.getAbilities().allowFlying = true;
                        player.sendAbilitiesUpdate();
                    }
                }
            }
        });
    }
}
