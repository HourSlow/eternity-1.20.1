package net.hour.eternity.block;

import com.terraformersmc.terraform.sign.block.TerraformHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallHangingSignBlock;
import com.terraformersmc.terraform.sign.block.TerraformWallSignBlock;
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
import net.minecraft.data.family.BlockFamilies;
import net.minecraft.data.family.BlockFamily;
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

    public static final Block EVERGLOOM_LEAVES = registerBlock("evergloom_leaves",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_LEAVES).strength(0.3f).nonOpaque()));



    public static final Block EVERGLOOM_PLANKS = registerBlock("evergloom_planks",
            new Block(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).strength(2f)));

    public static final Block EVERGLOOM_STAIRS = registerBlock("evergloom_stairs",
            new StairsBlock(ModBlocks.EVERGLOOM_PLANKS.getDefaultState(), FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block EVERGLOOM_SLAB = registerBlock("evergloom_slab",
            new SlabBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));

    public static final Block EVERGLOOM_BUTTON = registerBlock("evergloom_button",
            new ButtonBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).collidable(false), BlockSetType.DARK_OAK, 10, true));
    public static final Block EVERGLOOM_PRESSURE_PLATE = registerBlock("evergloom_pressure_plate",
            new PressurePlateBlock(PressurePlateBlock.ActivationRule.EVERYTHING,
                    FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).collidable(false), BlockSetType.DARK_OAK));

    public static final Block EVERGLOOM_FENCE = registerBlock("evergloom_fence",
            new FenceBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS)));
    public static final Block EVERGLOOM_FENCE_GATE = registerBlock("evergloom_fence_gate",
            new FenceGateBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS), WoodType.DARK_OAK));

    public static final Block EVERGLOOM_DOOR = registerBlock("evergloom_door",
            new DoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque(), BlockSetType.DARK_OAK));
    public static final Block EVERGLOOM_TRAPDOOR = registerBlock("evergloom_trapdoor",
            new TrapdoorBlock(FabricBlockSettings.copyOf(Blocks.OAK_PLANKS).nonOpaque(), BlockSetType.DARK_OAK));

    public static final Identifier EVERGLOOM_SIGN_TEXTURE = new Identifier(Eternity.MOD_ID, "entity/signs/evergloom");
    public static final Identifier EVERGLOOM_HANGING_SIGN_TEXTURE = new Identifier(Eternity.MOD_ID, "entity/signs/hanging/evergloom");
    public static final Identifier EVERGLOOM_HANGING_GUI_SIGN_TEXTURE = new Identifier(Eternity.MOD_ID, "textures/gui/hanging_signs/evergloom");

    public static final Block STANDING_EVERGLOOM_SIGN = Registry.register(Registries.BLOCK, new Identifier(Eternity.MOD_ID, "standing_evergloom_sign"),
            new TerraformSignBlock(EVERGLOOM_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_SIGN)));
    public static final Block WALL_EVERGLOOM_SIGN = Registry.register(Registries.BLOCK, new Identifier(Eternity.MOD_ID, "wall_evergloom_sign"),
            new TerraformWallSignBlock(EVERGLOOM_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_WALL_SIGN)));
    public static final Block HANGING_EVERGLOOM_SIGN = Registry.register(Registries.BLOCK, new Identifier(Eternity.MOD_ID, "hanging_evergloom_sign"),
            new TerraformHangingSignBlock(EVERGLOOM_HANGING_SIGN_TEXTURE, EVERGLOOM_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_HANGING_SIGN)));
    public static final Block WALL_HANGING_EVERGLOOM_SIGN = Registry.register(Registries.BLOCK, new Identifier(Eternity.MOD_ID, "wall_hanging_evergloom_sign"),
        new TerraformWallHangingSignBlock(EVERGLOOM_HANGING_SIGN_TEXTURE, EVERGLOOM_HANGING_GUI_SIGN_TEXTURE, FabricBlockSettings.copyOf(Blocks.OAK_WALL_HANGING_SIGN)));

    public static final BlockFamily EVERGLOOM_FAMILY = BlockFamilies.register(ModBlocks.EVERGLOOM_PLANKS)
            .sign(ModBlocks.STANDING_EVERGLOOM_SIGN, ModBlocks.WALL_EVERGLOOM_SIGN)
            .group("wooden").unlockCriterionName("has_planks").build();


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
        entries.add(STRIPPED_EVERGLOOM_WOOD);
        entries.add(STRIPPED_EVERGLOOM_LOG);
        entries.add(EVERGLOOM_LEAVES);
        entries.add(EVERGLOOM_PLANKS);
        entries.add(EVERGLOOM_STAIRS);
        entries.add(EVERGLOOM_SLAB);
        entries.add(EVERGLOOM_BUTTON);
        entries.add(EVERGLOOM_PRESSURE_PLATE);
        entries.add(EVERGLOOM_FENCE);
        entries.add(EVERGLOOM_FENCE_GATE);
        entries.add(EVERGLOOM_TRAPDOOR);
        entries.add(EVERGLOOM_DOOR);
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
