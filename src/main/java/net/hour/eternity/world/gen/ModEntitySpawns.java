package net.hour.eternity.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.world.biome.ModBiome;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.world.Heightmap;

public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiome.WASTES),
                SpawnGroup.MONSTER, ModEntities.THE_FORGOTTEN, 100,1,2);

        SpawnRestriction.register(ModEntities.THE_FORGOTTEN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);
    }
}
