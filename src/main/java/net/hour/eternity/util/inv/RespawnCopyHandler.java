package net.hour.eternity.util.inv;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.hour.eternity.util.host.Excluded;
import net.hour.eternity.world.dimension.ModDimensions;

import java.util.UUID;

public final class RespawnCopyHandler {
    public static void register() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            UUID excluded = Excluded.get(oldPlayer.getServer());
            if (excluded != null && oldPlayer.getUuid().equals(excluded)) {
                ((DimensionInventoryHolder) newPlayer).copyFrom((DimensionInventoryHolder) oldPlayer);
                return;
            }

            DimensionInventoryHolder newHolder = (DimensionInventoryHolder) newPlayer;
            DimensionInventoryHolder oldHolder = (DimensionInventoryHolder) oldPlayer;
            newHolder.copyFrom(oldHolder);
        });
    }
}
