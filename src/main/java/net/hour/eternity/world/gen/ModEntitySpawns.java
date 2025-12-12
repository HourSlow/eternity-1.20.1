package net.hour.eternity.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.world.biome.ModBiome;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.Heightmap;
import net.minecraft.world.ServerWorldAccess;

public class ModEntitySpawns {
    public static void addSpawns() {
        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiome.WASTES),
                SpawnGroup.MONSTER, ModEntities.THE_FORGOTTEN, 1,1,1);

        SpawnRestriction.register(ModEntities.THE_FORGOTTEN, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, HostileEntity::canSpawnInDark);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiome.EERIE_FOREST),
                SpawnGroup.CREATURE, ModEntities.LILGUY, 80,2,4);

        SpawnRestriction.register(ModEntities.LILGUY, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, AnimalEntity::canMobSpawn);

        BiomeModifications.addSpawn(BiomeSelectors.includeByKey(ModBiome.EERIE_FOREST),
                SpawnGroup.MONSTER, ModEntities.MENACE, 1,1,1);

        SpawnRestriction.register(ModEntities.MENACE, SpawnRestriction.Location.ON_GROUND,
                Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ModEntitySpawns::rareSpawnPredicate);
    }


    public static boolean rareSpawnPredicate(EntityType<? extends HostileEntity> type, ServerWorldAccess world, SpawnReason reason, BlockPos pos, Random random) {
        // 1% chance
        if (random.nextInt(100) != 0) {
            return false;
        }
        return HostileEntity.canSpawnInDark(type, world, reason, pos, random);
    }
}
