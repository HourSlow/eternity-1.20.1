package net.hour.eternity.util.host;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.PersistentState;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HostStorage extends PersistentState {
    public final Set<UUID> hosts = new HashSet<>();

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        int i = 0;
        for (UUID uuid : hosts) {
            nbt.putUuid("host_" + i, uuid);
            i++;
        }
        nbt.putInt("size", hosts.size());
        return nbt;
    }

    public static HostStorage createFromNbt(NbtCompound nbt) {
        HostStorage storage = new HostStorage();
        int size = nbt.getInt("size");

        for (int i = 0; i < size; i++) {
            UUID id = nbt.getUuid("host_" + i);
            storage.hosts.add(id);
        }
        return storage;
    }
}
