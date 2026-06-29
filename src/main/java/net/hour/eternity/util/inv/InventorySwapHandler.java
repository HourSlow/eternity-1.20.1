package net.hour.eternity.util.inv;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.hour.eternity.util.host.Excluded;
import net.hour.eternity.world.dimension.ModDimensions;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public final class InventorySwapHandler {

    public static final Set<UUID> SKIP_NEXT_SWAP = new HashSet<>();

    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((serverPlayer, origin, destination) -> {

            UUID excluded = Excluded.get(serverPlayer.getServer());
            if (excluded != null && serverPlayer.getUuid().equals(excluded)) return;

            if (SKIP_NEXT_SWAP.contains(serverPlayer.getUuid())) {
                return;
            }

            boolean fromLimbo = origin.getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY);
            boolean toLimbo   = destination.getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY);

            DimensionInventoryHolder holder = (DimensionInventoryHolder) serverPlayer;

            if (!fromLimbo && toLimbo) {
                holder.saveOutsideInventory(serverPlayer.getInventory());
                holder.loadLimboInventory(serverPlayer.getInventory());
            } else if (fromLimbo && !toLimbo) {
                holder.saveLimboInventory(serverPlayer.getInventory());
                holder.loadOutsideInventory(serverPlayer.getInventory());
            }

            serverPlayer.currentScreenHandler.sendContentUpdates();
        });
    }
}