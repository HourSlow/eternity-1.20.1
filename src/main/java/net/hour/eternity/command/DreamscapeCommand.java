package net.hour.eternity.command;

import com.mojang.brigadier.CommandDispatcher;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hour.eternity.Eternity;
import net.hour.eternity.util.DreamerEntity;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import java.util.Collection;

public class DreamscapeCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(CommandManager.literal("dreamscape")
                .requires(source -> source.hasPermissionLevel(2))

                .then(CommandManager.literal("endall")
                        .executes(context -> {
                            for (ServerPlayerEntity player : context.getSource().getServer().getPlayerManager().getPlayerList()) {
                                stopDreaming(player);
                            }
                            context.getSource().sendFeedback(() -> Text.literal("Ended dreamscape for all players."), true);
                            return 1;
                        })
                )

                .then(CommandManager.literal("end")
                        .executes(context -> {
                            stopDreaming(context.getSource().getPlayer());
                            return 1;
                        })
                        .then(CommandManager.argument("target", EntityArgumentType.players())
                                .executes(context -> {
                                    Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "target");
                                    for (ServerPlayerEntity target : targets) {
                                        stopDreaming(target);
                                    }
                                    return targets.size();
                                })
                        )
                )

                .then(CommandManager.argument("target", EntityArgumentType.players())
                        .executes(context -> {
                            ServerPlayerEntity sender = context.getSource().getPlayer();
                            Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "target");

                            if (sender != null) {
                                startDreaming(sender);
                            }

                            for (ServerPlayerEntity target : targets) {
                                if (target != sender) {
                                    startDreaming(target);
                                }
                            }
                            return targets.size();
                        })
                )
        );
    }

    private static void startDreaming(ServerPlayerEntity player) {
        if (player == null) return;
        ((DreamerEntity) player).setDreaming(true);
        syncDreamState(player, true);
        player.sendMessage(Text.literal("The world begins to fade..."), true);
    }

    private static void stopDreaming(ServerPlayerEntity player) {
        if (player == null) return;

        if (!((DreamerEntity) player).isDreaming()) return;

        ((DreamerEntity) player).setDreaming(false);
        syncDreamState(player, false);
        player.sendMessage(Text.literal("You have returned to reality."), true);
    }

    private static void syncDreamState(ServerPlayerEntity player, boolean isDreaming) {
        PacketByteBuf buf = PacketByteBufs.create();

        buf.writeInt(player.getId());
        buf.writeBoolean(isDreaming);

        for (ServerPlayerEntity target : player.server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(target, Eternity.DREAM_SYNC_PACKET, buf);
        }
    }
}
