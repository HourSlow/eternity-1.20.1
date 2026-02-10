package net.hour.eternity.util.loottable;

import net.fabricmc.fabric.api.loot.v1.FabricLootPoolBuilder;
import net.fabricmc.fabric.api.loot.v1.event.LootTableLoadingCallback;
import net.hour.eternity.item.ModItems;
import net.minecraft.loot.condition.RandomChanceLootCondition;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.ConstantLootNumberProvider;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.minecraft.util.Identifier;

public class ModLootTableModifier {

    private static final Identifier ANCIENT_CITY_CHEST_ID =
            new Identifier("minecraft", "chests/ancient_city");


    public static void modifyLootTables(){
        LootTableLoadingCallback.EVENT.register(((resourceManager,
                                                  manager, id, supplier, setter) -> {
            if (ANCIENT_CITY_CHEST_ID.equals(id)) {
                FabricLootPoolBuilder poolBuilder = FabricLootPoolBuilder.builder()
                        .rolls(ConstantLootNumberProvider.create(1))
                        .conditionally(RandomChanceLootCondition.builder(0.10F)) // Drops 10% of the time
                        .with(ItemEntry.builder(ModItems.SUS_ITEM))
                        .withFunction(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)).build());
                supplier.withPool(poolBuilder.build());
            }
        }));
    }
}
