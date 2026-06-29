package net.hour.eternity.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.hour.eternity.Eternity;
import net.hour.eternity.block.custom.EtherBlock;
import net.hour.eternity.block.custom.StatueBlock;
import net.hour.eternity.item.ModItemGroup;
import net.hour.eternity.world.tree.DeadOakGenerator;
import net.hour.eternity.world.tree.EvergloomSaplingGenerator;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModBlocks {


    public static final Block STATUE = registerBlock("statue_tarr",
            new StatueBlock(FabricBlockSettings.copyOf(Blocks.NETHERITE_BLOCK).nonOpaque().dropsNothing()));

    public static final Block WHITE_ETHER = registerBlock("white_ether",
            new EtherBlock(FabricBlockSettings.copyOf(Blocks.SEA_LANTERN).dropsNothing().luminance(15).dropsNothing().strength(-1f)));



    public static final Block EVERGLOOM_SAPLING = registerBlock("evergloom_sapling",
            new SaplingBlock(new EvergloomSaplingGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).breakInstantly()));

    public static final Block DEAD_OAK_SAPLING = registerBlock("dead_oak_sapling",
            new SaplingBlock(new DeadOakGenerator(), FabricBlockSettings.copyOf(Blocks.OAK_SAPLING).breakInstantly()));



    public static final Block EVERGLOOM_LOG = registerBlock("evergloom_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_LOG).strength(3f)));

    public static final Block EVERGLOOM_WOOD = registerBlock("evergloom_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.OAK_WOOD).strength(3f)));

    public static final Block STRIPPED_EVERGLOOM_LOG = registerBlock("stripped_evergloom_log",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_LOG).strength(3f)));

    public static final Block STRIPPED_EVERGLOOM_WOOD = registerBlock("stripped_evergloom_wood",
            new PillarBlock(FabricBlockSettings.copyOf(Blocks.STRIPPED_OAK_WOOD).strength(3f)));


    public static final Block EVERGLOOM_PLANKS = registerBlock("evergloom_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2f)));

    public static final Block EVERGLOOM_LEAVES = registerBlock("evergloom_leaves",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(0.3f).nonOpaque()));



    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, new Identifier(Eternity.MOD_ID, name),block);
    }

    private static void addBlockToNaturalItemGroup(FabricItemGroupEntries entries) {
        entries.add(EVERGLOOM_SAPLING);
        entries.add(DEAD_OAK_SAPLING);
    }

    private static void addBlockToBuildingBlocksItemGroup(FabricItemGroupEntries entries) {
        entries.add(EVERGLOOM_LOG);
        entries.add(EVERGLOOM_WOOD);
        entries.add(EVERGLOOM_PLANKS);
        entries.add(STRIPPED_EVERGLOOM_WOOD);
        entries.add(STRIPPED_EVERGLOOM_LOG);
        entries.add(EVERGLOOM_LEAVES);
        entries.add(WHITE_ETHER);
    }

    private static Item registerBlockItem(String name, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(Eternity.MOD_ID, name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        Eternity.LOGGER.debug(Eternity.MOD_ID + " - ModBlocks");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register(ModBlocks::addBlockToNaturalItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(ModBlocks::addBlockToBuildingBlocksItemGroup);
    }
}
