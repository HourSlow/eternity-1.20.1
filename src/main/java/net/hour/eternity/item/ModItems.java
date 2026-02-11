package net.hour.eternity.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.hour.eternity.Eternity;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.item.custom.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {


    public static final Item SUNDIAL = registerItem("sundial",
            new OrbitalLaserItem(new FabricItemSettings().maxCount(1)));

    public static final Item TESTDIAL = registerItem("testdial",
            new BeaconBeamItem(new FabricItemSettings().maxCount(1)));

    public static final Item SUS_ITEM = registerItem("sus_item",
            new SusItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.EPIC)));

    public static final Item MIRROR = registerItem("mirror",
            new LimboTPItem(new FabricItemSettings().maxCount(1)));

    public static final Item ETERNAL_LANTERN = registerItem("eternal_lantern",
            new RevealItem(new FabricItemSettings().maxCount(1)));

    public static final Item MACE = registerItem("mace",
            new MaceItem(new FabricItemSettings().maxCount(1).fireproof().rarity(Rarity.EPIC)));



    public static final Item THE_FORGOTTEN_SPAWN_EGG = registerItem("forgotten_spawn_egg",
            new SpawnEggItem(ModEntities.THE_FORGOTTEN, 0xFFFFFF, 0xD3D3D3, new FabricItemSettings()));

    public static final Item LILGUY_SPAWN_EGG = registerItem("lilguy_spawn_egg",
            new SpawnEggItem(ModEntities.LILGUY, 0x2E7D32, 0xB7E8B4, new FabricItemSettings()));

    public static final Item MENACE_SPAWN_EGG = registerItem("menace_spawn_egg",
            new SpawnEggItem(ModEntities.MENACE, 0x8B4C4C, 0xA8A8A8, new FabricItemSettings()));

    private static void addItemToSpawnEggsItemGroup(FabricItemGroupEntries entries) {
        entries.add(THE_FORGOTTEN_SPAWN_EGG);
        entries.add(LILGUY_SPAWN_EGG);
        entries.add(MENACE_SPAWN_EGG);
    }

    private static void addItemToCombatItemGroup(FabricItemGroupEntries entries) {
        entries.add(ETERNAL_LANTERN);
    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(Eternity.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Eternity.LOGGER.debug(Eternity.MOD_ID, " - ModItems");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.SPAWN_EGGS).register(ModItems::addItemToSpawnEggsItemGroup);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(ModItems::addItemToCombatItemGroup);
    }
}
