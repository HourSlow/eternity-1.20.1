package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.hour.eternity.block.ModBlocks;

public class ModLootTableProvider extends FabricBlockLootTableProvider {

    public ModLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.EVERGLOOM_PLANKS);
        addDrop(ModBlocks.EVERGLOOM_LOG);
        addDrop(ModBlocks.STRIPPED_EVERGLOOM_LOG);
        addDrop(ModBlocks.EVERGLOOM_WOOD);
        addDrop(ModBlocks.STRIPPED_EVERGLOOM_WOOD);
        addDrop(ModBlocks.EVERGLOOM_SAPLING);
        addDrop(ModBlocks.DEAD_OAK_SAPLING);

        addDrop(ModBlocks.EVERGLOOM_STAIRS);
        addDrop(ModBlocks.EVERGLOOM_FENCE);
        addDrop(ModBlocks.EVERGLOOM_FENCE_GATE);
        addDrop(ModBlocks.EVERGLOOM_TRAPDOOR);
        addDrop(ModBlocks.EVERGLOOM_BUTTON);
        addDrop(ModBlocks.EVERGLOOM_PRESSURE_PLATE);

        addDrop(ModBlocks.EVERGLOOM_DOOR, doorDrops(ModBlocks.EVERGLOOM_DOOR));
        addDrop(ModBlocks.EVERGLOOM_SLAB, slabDrops(ModBlocks.EVERGLOOM_SLAB));

        addDrop(ModBlocks.EVERGLOOM_LEAVES, leavesDrops(ModBlocks.EVERGLOOM_LEAVES, ModBlocks.EVERGLOOM_SAPLING, 0.15f));
    }
}
