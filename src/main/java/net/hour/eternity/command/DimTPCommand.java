package net.hour.eternity.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.argument.DimensionArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;

public class DimTPCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dimtp")
                .requires(source -> source.hasPermissionLevel(2))
                .then(CommandManager.argument("player", EntityArgumentType.player())
                        .then(CommandManager.argument("dimension", DimensionArgumentType.dimension())
                                .executes(DimTPCommand::execute))));
    }

    private static int execute(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = EntityArgumentType.getPlayer(context, "player");
        ServerWorld targetWorld = DimensionArgumentType.getDimensionArgument(context, "dimension");

        BlockPos spawnPos = targetWorld.getSpawnPos();


        player.teleport(
                targetWorld,
                spawnPos.getX() + 0.5,
                spawnPos.getY(),
                spawnPos.getZ() + 0.5,
                player.getYaw(),
                player.getPitch()
        );

        context.getSource().sendFeedback(() -> Text.literal("Teleported " + player.getName().getString() + " to " + targetWorld.getRegistryKey().getValue().getPath() + " spawn."), true);

        return 1;
    }
}
