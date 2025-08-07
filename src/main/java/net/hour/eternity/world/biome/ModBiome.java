package net.hour.eternity.world.biome;

import net.hour.eternity.Eternity;
import net.minecraft.client.sound.MusicType;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.BiomeMoodSound;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.*;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.minecraft.world.gen.feature.VegetationPlacedFeatures;

public class ModBiome {

    public static final RegistryKey<Biome> WASTES = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(Eternity.MOD_ID, "wastes"));

    public static final RegistryKey<Biome> EERIE_FOREST = RegistryKey.of(RegistryKeys.BIOME,
            new Identifier(Eternity.MOD_ID, "eerie_forest"));



    public static void bootstrap(Registerable<Biome> context) {
        context.register(WASTES, wastesBiome(context));
        context.register(EERIE_FOREST, eerieForestBiome(context));
    }


    public static void globalOverworldGeneration(GenerationSettings.LookupBackedBuilder builder) {
        DefaultBiomeFeatures.addLandCarvers(builder);
        DefaultBiomeFeatures.addSprings(builder);
    }



    public static Biome eerieForestBiome(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_LARGE_FERN);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_TALL_GRASS);
        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_GRASS_NORMAL);

        return new Biome.Builder()
                .precipitation(true)
                .downfall(1.0f)
                .temperature(0.0f)
                .generationSettings(biomeBuilder.build())
                .spawnSettings(spawnBuilder.build())
                .effects((new BiomeEffects.Builder())
                        .waterColor(0xc9e4de)
                        .waterFogColor(0xaad2c6)
                        .skyColor(0xdedede)
                        .grassColor(0xcfdac8)
                        .foliageColor(0xd5e1d5)
                        .fogColor(0xdddddd)
                        .moodSound(BiomeMoodSound.CAVE)
                        .music(MusicType.createIngameMusic(RegistryEntry.of(SoundEvents.ENTITY_ENDERMITE_AMBIENT))).build())
                .build();
    }


    public static Biome wastesBiome(Registerable<Biome> context) {
        SpawnSettings.Builder spawnBuilder = new SpawnSettings.Builder();

        GenerationSettings.LookupBackedBuilder biomeBuilder =
                new GenerationSettings.LookupBackedBuilder(context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
                        context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER));

        globalOverworldGeneration(biomeBuilder);
        DefaultBiomeFeatures.addMossyRocks(biomeBuilder);
        DefaultBiomeFeatures.addDesertDeadBushes(biomeBuilder);

        biomeBuilder.feature(GenerationStep.Feature.VEGETAL_DECORATION, VegetationPlacedFeatures.PATCH_DEAD_BUSH_BADLANDS);

        return new Biome.Builder()
                .precipitation(false)
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
