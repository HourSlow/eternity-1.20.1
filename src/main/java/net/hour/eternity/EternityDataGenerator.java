package net.hour.eternity;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.hour.eternity.datagen.*;
import net.hour.eternity.world.ModConfiguredFeatures;
import net.hour.eternity.world.ModPlacedFeatures;
import net.hour.eternity.world.biome.ModBiome;
import net.minecraft.registry.RegistryBuilder;
import net.minecraft.registry.RegistryKeys;

public class EternityDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModWorldGenerator::new);
		pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModLootTableProvider::new);
        pack.addProvider(ModRecipeProvider::new);
	}

	@Override
	public void buildRegistry(RegistryBuilder registryBuilder) {
		registryBuilder.addRegistry(RegistryKeys.BIOME, ModBiome::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap);
        registryBuilder.addRegistry(RegistryKeys.PLACED_FEATURE, ModPlacedFeatures::bootstrap);
	}
}
