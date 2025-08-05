package net.hour.eternity.world.biome;

import net.hour.eternity.Eternity;
import net.hour.eternity.entity.ModEntities;
import net.minecraft.client.sound.MusicType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeEffects;
import net.minecraft.world.biome.GenerationSettings;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiome {

    public static final RegistryKey<Biome> WASTES = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(Eternity.MOD_ID, "wastes"));

    public static void bootstrap(Registerable<Biome> context) {
        context.register(WASTES, wastesBiome(context));
    }

    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addSprings(builder);
        DefaultBiomeFeatures.addMossyRocks(builder);
        DefaultBiomeFeatures.addDesertDeadBushes(builder);
    }

    public static Biome wastesBiome(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        spawnBuilder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(ModEntities.THE_FORGOTTEN, 100, 1, 3));

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH_BADLANDS);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(0.0f)
                .temperature(2.0f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0x5c4033)
                        .waterFogColor(0x3e2f24)
                        .skyColor(0x6b5e4e)
                        .grassColor(0x665e3f)
                        .foliageColor(0x4b3f2f)
                        .fogColor(0x3c2f27)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(RegistryEntry.of(SoundEvents.ENTITY_ENDERMITE_AMBIENT))).build())
                .build();
    }
}
