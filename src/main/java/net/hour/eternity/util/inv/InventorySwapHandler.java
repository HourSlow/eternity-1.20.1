package net.hour.eternity.util.inv;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.hour.eternity.util.host.Excluded;
import net.hour.eternity.world.dimension.ModDimensions;

import java.util.UUID;

public final class InventorySwapHandler {
    public static void register() {
        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((serverPlayer, origin, destination) -> {
            UUID excluded = Excluded.get(serverPlayer.getServer());

            if (excluded != null && serverPlayer.getUuid().equals(excluded)) return;

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