package net.hour.eternity.world.gen;

import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.hour.eternity.world.ModPlacedFeatures;
import net.hour.eternity.world.biome.ModBiome;
import net.minecraft.world.gen.GenerationStep;

public class ModTreeGeneration {
    public static void generateTrees() {
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(ModBiome.WASTES),
                GenerationStep.Feature.VEGETAL_DECORATION, ModPlacedFeatures.EVERGLOOM_PLACED_KEY);
    }
}
