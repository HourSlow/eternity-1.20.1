package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;

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

        ShapedRecipeJsonBuilder.create(RecipeCategory.TOOLS, Items.TOTEM_OF_UNDYING)
                .pattern(" G ")
                .pattern("EIE")
                .pattern("GGG")
                .input('I', ModItems.SUS_ITEM)
                .input('G', Items.GOLD_BLOCK)
                .input('E', Items.EMERALD)
                .criterion(hasItem(ModItems.SUS_ITEM), conditionsFromItem(ModItems.SUS_ITEM))
                .offerTo(exporter);
    }
}
