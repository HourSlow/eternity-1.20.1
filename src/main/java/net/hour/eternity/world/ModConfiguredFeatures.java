package net.hour.eternity.world;

import net.hour.eternity.Eternity;
import net.hour.eternity.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.ConstantIntProvider;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.FeatureConfig;
import net.minecraft.world.gen.feature.TreeFeatureConfig;
import net.minecraft.world.gen.feature.size.TwoLayersFeatureSize;
import net.minecraft.world.gen.foliage.DarkOakFoliagePlacer;
import net.minecraft.world.gen.foliage.LargeOakFoliagePlacer;
import net.minecraft.world.gen.stateprovider.BlockStateProvider;
import net.minecraft.world.gen.trunk.DarkOakTrunkPlacer;
import net.minecraft.world.gen.trunk.LargeOakTrunkPlacer;

public class ModConfiguredFeatures {

    public static final RegistryKey<ConfiguredFeature<?, ?>> EVERGLOOM_KEY = registerKey("evergloom");
    public static final RegistryKey<ConfiguredFeature<?, ?>> DEAD_OAK_KEY = registerKey("dead_oak");


    public static void bootstrap(Registerable<ConfiguredFeature<?, ?>> context) {
        register(context, EVERGLOOM_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(ModBlocks.EVERGLOOM_LOG),
                new DarkOakTrunkPlacer(6, 1, 1),
                BlockStateProvider.of(ModBlocks.EVERGLOOM_LEAVES),
                new DarkOakFoliagePlacer(ConstantIntProvider.create(0),ConstantIntProvider.create(0)),

                new TwoLayersFeatureSize(1, 0, 2)).build());

        register(context, DEAD_OAK_KEY, Feature.TREE, new TreeFeatureConfig.Builder(
                BlockStateProvider.of(Blocks.OAK_LOG),
                new LargeOakTrunkPlacer(5, 3, 3),
                BlockStateProvider.of(Blocks.AIR),
                new LargeOakFoliagePlacer(ConstantIntProvider.create(2),ConstantIntProvider.create(2),2),

                new TwoLayersFeatureSize(1, 0, 2)).build());
    }



    public static RegistryKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(Eternity.MOD_ID, name));
    }

    private static <FC extends FeatureConfig, F extends Feature<FC>> void register(Registerable<ConfiguredFeature<?, ?>> context,
                                                                                   RegistryKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration) {
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }

}
