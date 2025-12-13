package net.hour.eternity.world.structure;

import net.hour.eternity.Eternity;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplateManager;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;

public class StructurePlacer {
    private static final Identifier STRUCTURE_ID = new Identifier(Eternity.MOD_ID, "tarr_temple");

    public static void placeStructure(ServerWorld world, BlockPos pos) {
        MinecraftServer server = world.getServer();
        if (server == null) return;
        StructureTemplateManager manager = server.getStructureTemplateManager();

        manager.getTemplate(STRUCTURE_ID).ifPresent(template -> {
            StructurePlacementData placementData = new StructurePlacementData()
                    .setRotation(BlockRotation.NONE)
                    .setMirror(net.minecraft.util.BlockMirror.NONE)
                    .setIgnoreEntities(false);

            template.place(world, pos, pos, placementData, world.getRandom(), 2);

            System.out.println("Placed structure at: " + pos.getX() + ", " + pos.getZ());
        });
    }
}
