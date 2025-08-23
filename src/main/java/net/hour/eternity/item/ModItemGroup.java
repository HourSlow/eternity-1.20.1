package net.hour.eternity.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.hour.eternity.Eternity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroup {

    public static final ItemGroup ETERNITY_TAB = Registry.register(Registries.ITEM_GROUP,
            new Identifier(Eternity.MOD_ID, "eternity_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemGroup.eternity_tab"))
                    .icon(() -> new ItemStack(Items.ACACIA_BOAT)).entries((displayContext, entries) -> {

                        entries.add(ModItems.SUNDIAL);

                    }).build());



    public static void registerItemGroup() {
        Eternity.LOGGER.debug(Eternity.MOD_ID + " - ModItemGroup");
    }
}
