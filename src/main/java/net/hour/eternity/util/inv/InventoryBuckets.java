package net.hour.eternity.util.inv;

import net.hour.eternity.world.dimension.ModDimensions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;

public final class InventoryBuckets {
    public static String bucketFor(RegistryKey<World> key) {
        return key.equals(ModDimensions.LIMBO_DIM_KEY) ? "limbo" : "main";
    }
}
