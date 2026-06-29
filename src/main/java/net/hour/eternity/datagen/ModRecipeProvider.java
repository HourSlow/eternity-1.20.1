package net.hour.eternity.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
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

        offerPressurePlateRecipe(exporter, ModBlocks.EVERGLOOM_PRESSURE_PLATE, ModBlocks.EVERGLOOM_PLANKS);
        offerShapelessRecipe(exporter, ModBlocks.EVERGLOOM_BUTTON, ModBlocks.EVERGLOOM_PLANKS, "evergloom", 1);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_FENCE, 3)
                .pattern("   ")
                .pattern("ESE")
                .pattern("ESE")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_FENCE_GATE)
                .pattern("   ")
                .pattern("SES")
                .pattern("SES")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_TRAPDOOR, 2)
                .pattern("   ")
                .pattern("EEE")
                .pattern("EEE")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_DOOR, 3)
                .pattern("EE ")
                .pattern("EE ")
                .pattern("EE ")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);

        offerSlabRecipe(exporter, RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_SLAB, ModBlocks.EVERGLOOM_PLANKS);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModBlocks.EVERGLOOM_STAIRS,4)
                .pattern("E  ")
                .pattern("EE ")
                .pattern("EEE")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.EVERGLOOM_SIGN, 3)
                .pattern("EEE")
                .pattern("EEE")
                .pattern(" S ")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .input('S', Items.STICK)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, ModItems.EVERGLOOM_HANGING_SIGN, 6)
                .pattern("X X")
                .pattern("EEE")
                .pattern("EEE")
                .input('E', ModBlocks.EVERGLOOM_PLANKS)
                .input('X', Items.CHAIN)
                .criterion(hasItem(ModBlocks.EVERGLOOM_PLANKS), conditionsFromItem(ModBlocks.EVERGLOOM_PLANKS))
                .offerTo(exporter);
    }
}
