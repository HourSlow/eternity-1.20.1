package net.hour.eternity.util.host;

import net.minecraft.server.MinecraftServer;
import net.minecraft.world.PersistentStateManager;

public class HostStorageManager {

    public static HostStorage get(MinecraftServer server) {
        PersistentStateManager manager = server.getOverworld().getPersistentStateManager();

        return manager.getOrCreate(
                HostStorage::createFromNbt,
                HostStorage::new,
                "host_storage"        // File name: world/data/host_storage.dat
        );
    }
}
