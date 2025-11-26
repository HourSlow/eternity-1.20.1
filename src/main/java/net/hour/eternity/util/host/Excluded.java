package net.hour.eternity.util.host;

import net.minecraft.server.MinecraftServer;

import java.util.UUID;

public class Excluded {

    private Excluded() {}

    public static UUID get(MinecraftServer server) {
        HostStorage storage = HostStorageManager.get(server);

        if (storage.hosts.isEmpty()) return null;

        return storage.hosts.iterator().next();
    }
}
