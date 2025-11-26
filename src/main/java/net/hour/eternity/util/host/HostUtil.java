package net.hour.eternity.util.host;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.server.network.ServerPlayerEntity;

public class HostUtil {

    public static void register() {

        ServerLivingEntityEvents.ALLOW_DAMAGE.register((entity, source, amount) -> {
            if (entity instanceof ServerPlayerEntity player) {

                var storage = HostStorageManager.get(player.getServer());

                if (storage.hosts.contains(player.getUuid())) {
                    return false;
                }
            }
            return true;
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var storage = HostStorageManager.get(server);

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {

                if (storage.hosts.contains(player.getUuid())) {

                    if (!player.getAbilities().allowFlying) {
                        player.getAbilities().allowFlying = true;
                        player.sendAbilitiesUpdate();
                    }
                }
            }
        });
    }
}
