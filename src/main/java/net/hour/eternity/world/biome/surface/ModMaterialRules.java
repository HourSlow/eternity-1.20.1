package net.hour.eternity.world.biome.surface;

import net.hour.eternity.world.biome.ModBiome;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;

public class ModMaterialRules {
    private static final MaterialRules.MaterialRule DIRT = makeStateRule(Blocks.DIRT);
    private static final MaterialRules.MaterialRule GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);
    private static final MaterialRules.MaterialRule COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final MaterialRules.MaterialRule STONE = makeStateRule(Blocks.STONE);


    public static MaterialRules.MaterialRule makeRules() {
        // Helper: is at or above water level
        MaterialRules.MaterialCondition isAtOrAboveWaterLevel = MaterialRules.water(-1, 0);

        // === Biome-specific rules ===
        MaterialRules.MaterialRule wastesRule = MaterialRules.condition(
                MaterialRules.biome(ModBiome.WASTES),
                MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, COARSE_DIRT),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, DIRT)
                )
        );

        MaterialRules.MaterialRule eerieForestRule = MaterialRules.condition(
                MaterialRules.biome(ModBiome.EERIE_FOREST),
                MaterialRules.sequence(
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, GRASS_BLOCK),
                        MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, DIRT)
                )
        );

        // === Default rule if no biome matches ===
        MaterialRules.MaterialRule defaultRule = MaterialRules.sequence(
                MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, GRASS_BLOCK),
                MaterialRules.condition(MaterialRules.STONE_DEPTH_CEILING, DIRT)
        );

        return MaterialRules.sequence(
                wastesRule,
                eerieForestRule,
                defaultRule
        );
    }

    private static MaterialRules.MaterialRule makeStateRule(Block block) {
        return MaterialRules.block(block.getDefaultState());
    }
}
