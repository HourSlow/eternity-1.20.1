package net.hour.eternity.entity;

import com.terraformersmc.terraform.boat.api.TerraformBoatType;
import com.terraformersmc.terraform.boat.api.TerraformBoatTypeRegistry;
import net.hour.eternity.Eternity;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.item.ModItems;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public class ModBoats {
    public static final Identifier EVERGLOOM_BOAT_ID = new Identifier(Eternity.MOD_ID, "evergloom_boat");
    public static final Identifier EVERGLOOM_CHEST_BOAT_ID = new Identifier(Eternity.MOD_ID, "evergloom_chest_boat");

    public static final RegistryKey<TerraformBoatType> EVERGLOOM_BOAT_KEY = TerraformBoatTypeRegistry.createKey(EVERGLOOM_BOAT_ID);

    public static void registerBoats(){
        TerraformBoatType evergloomBoat = new TerraformBoatType.Builder()
                .item(ModItems.EVERGLOOM_BOAT)
                .chestItem(ModItems.EVERGLOOM_CHEST_BOAT)
                .planks(ModBlocks.EVERGLOOM_PLANKS.asItem())
                .build();

        Registry.register(TerraformBoatTypeRegistry.INSTANCE, EVERGLOOM_BOAT_KEY, evergloomBoat);
    }
}
