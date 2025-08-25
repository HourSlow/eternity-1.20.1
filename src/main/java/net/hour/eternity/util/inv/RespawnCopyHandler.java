package net.hour.eternity.util.inv;

import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.hour.eternity.util.Excluded;
import net.hour.eternity.world.dimension.ModDimensions;

public final class RespawnCopyHandler {
    public static void register() {
        ServerPlayerEvents.COPY_FROM.register((oldPlayer, newPlayer, alive) -> {
            if (oldPlayer.getUuid().equals(Excluded.TARR_MISCHIEF)) {
                ((DimensionInventoryHolder) newPlayer).copyFrom((DimensionInventoryHolder) oldPlayer);
                return;
            }

            DimensionInventoryHolder newHolder = (DimensionInventoryHolder) newPlayer;
            DimensionInventoryHolder oldHolder = (DimensionInventoryHolder) oldPlayer;

            if (oldPlayer.getWorld().getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY)) {
                // Died in LIMBO -> clear saved data for new player
                newHolder.clearSavedInventories();
                return;
            }

            if (alive) {
                newHolder.copyFrom(oldHolder);
            } else {
                newHolder.clearSavedInventories();
            }
        });
    }
}
