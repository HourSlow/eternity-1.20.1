package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Model;
import net.minecraft.data.client.Models;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class ModModelProvider extends FabricModelProvider {

    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {

        blockStateModelGenerator.registerLog(ModBlocks.EVERGLOOM_LOG).log(ModBlocks.EVERGLOOM_LOG).wood(ModBlocks.EVERGLOOM_WOOD);
        blockStateModelGenerator.registerLog(ModBlocks.STRIPPED_EVERGLOOM_LOG).log(ModBlocks.STRIPPED_EVERGLOOM_LOG).wood(ModBlocks.STRIPPED_EVERGLOOM_WOOD);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.EVERGLOOM_LEAVES);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.WHITE_ETHER);
        blockStateModelGenerator.registerTintableCross(ModBlocks.EVERGLOOM_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);
        blockStateModelGenerator.registerTintableCross(ModBlocks.DEAD_OAK_SAPLING, BlockStateModelGenerator.TintType.NOT_TINTED);

        BlockStateModelGenerator.BlockTexturePool evergloomPlanksPool = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.EVERGLOOM_PLANKS);
        evergloomPlanksPool.stairs(ModBlocks.EVERGLOOM_STAIRS);
        evergloomPlanksPool.slab(ModBlocks.EVERGLOOM_SLAB);
        evergloomPlanksPool.button(ModBlocks.EVERGLOOM_BUTTON);
        evergloomPlanksPool.pressurePlate(ModBlocks.EVERGLOOM_PRESSURE_PLATE);
        evergloomPlanksPool.fence(ModBlocks.EVERGLOOM_FENCE);
        evergloomPlanksPool.fenceGate(ModBlocks.EVERGLOOM_FENCE_GATE);

        evergloomPlanksPool.family(ModBlocks.EVERGLOOM_FAMILY);

        blockStateModelGenerator.registerDoor(ModBlocks.EVERGLOOM_DOOR);
        blockStateModelGenerator.registerOrientableTrapdoor(ModBlocks.EVERGLOOM_TRAPDOOR);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

        itemModelGenerator.register(ModItems.MIRROR, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SUNDIAL, Models.GENERATED);
        itemModelGenerator.register(ModItems.SUS_ITEM, Models.GENERATED);
        itemModelGenerator.register(ModItems.MACE, Models.HANDHELD);
        itemModelGenerator.register(ModItems.ETERNAL_LANTERN, Models.GENERATED);

        itemModelGenerator.register(ModItems.THE_FORGOTTEN_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.LILGUY_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));
        itemModelGenerator.register(ModItems.MENACE_SPAWN_EGG,
                new Model(Optional.of(new Identifier("item/template_spawn_egg")), Optional.empty()));

        itemModelGenerator.register(ModItems.EVERGLOOM_HANGING_SIGN, Models.GENERATED);

        itemModelGenerator.register(ModItems.EVERGLOOM_BOAT, Models.GENERATED);
        itemModelGenerator.register(ModItems.EVERGLOOM_CHEST_BOAT, Models.GENERATED);
    }
}
