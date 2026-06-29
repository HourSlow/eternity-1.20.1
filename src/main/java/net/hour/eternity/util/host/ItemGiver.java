package net.hour.eternity.util.host;

import net.hour.eternity.item.ModItems;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;

public class ItemGiver {

    public static void giveHostItems(ServerPlayerEntity player) {

        ItemStack item1 = new ItemStack(ModItems.MIRROR);
        //ItemStack item2 = new ItemStack(ModItems.SUNDIAL);

        player.getInventory().offerOrDrop(item1);
        //player.getInventory().offerOrDrop(item2);
    }
}
