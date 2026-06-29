package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.hour.eternity.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        getOrCreateTagBuilder(ItemTags.PLANKS)
                .add(ModBlocks.EVERGLOOM_PLANKS.asItem());

        getOrCreateTagBuilder(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.EVERGLOOM_LOG.asItem())
                .add(ModBlocks.STRIPPED_EVERGLOOM_LOG.asItem())
                .add(ModBlocks.EVERGLOOM_WOOD.asItem())
                .add(ModBlocks.EVERGLOOM_DOOR.asItem())
                .add(ModBlocks.EVERGLOOM_TRAPDOOR.asItem())
                .add(ModBlocks.EVERGLOOM_STAIRS.asItem())
                .add(ModBlocks.EVERGLOOM_SLAB.asItem())
                .add(ModBlocks.EVERGLOOM_FENCE.asItem())
                .add(ModBlocks.EVERGLOOM_FENCE_GATE.asItem())
                .add(ModBlocks.STRIPPED_EVERGLOOM_WOOD.asItem());

        getOrCreateTagBuilder(ItemTags.LEAVES)
                .add(ModBlocks.EVERGLOOM_LEAVES.asItem());
    }
}
