package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hour.eternity.block.ModBlocks;
import net.minecraft.data.server.recipe.RecipeJsonProvider;

import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {

        offerShapelessRecipe(exporter, ModBlocks.EVERGLOOM_PLANKS, ModBlocks.EVERGLOOM_LOG, "evergloom", 4);
        offerShapelessRecipe(exporter, ModBlocks.EVERGLOOM_PLANKS, ModBlocks.STRIPPED_EVERGLOOM_LOG, "evergloom", 4);

        offerShapelessRecipe(exporter, ModBlocks.EVERGLOOM_PLANKS, ModBlocks.EVERGLOOM_WOOD, "evergloom", 4);
        offerShapelessRecipe(exporter, ModBlocks.EVERGLOOM_PLANKS, ModBlocks.STRIPPED_EVERGLOOM_WOOD, "evergloom", 4);

        offerBarkBlockRecipe(exporter, ModBlocks.EVERGLOOM_WOOD, ModBlocks.EVERGLOOM_LOG);
        offerBarkBlockRecipe(exporter, ModBlocks.STRIPPED_EVERGLOOM_WOOD, ModBlocks.STRIPPED_EVERGLOOM_LOG);
    }
}
