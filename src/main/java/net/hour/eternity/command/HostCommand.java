package net.hour.eternity.command;

import com.mojang.brigadier.CommandDispatcher;
import net.hour.eternity.util.host.HostStorageManager;
import net.hour.eternity.util.host.ItemGiver;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class HostCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
        dispatcher.register(
                CommandManager.literal("host")
                        .requires(src -> src.hasPermissionLevel(3))
                        .then(CommandManager.literal("set")
                                .then(CommandManager.argument("player", net.minecraft.command.argument.GameProfileArgumentType.gameProfile())
                                        .executes(ctx -> {
                                            var profiles = net.minecraft.command.argument.GameProfileArgumentType.getProfileArgument(ctx, "player");
                                            var server = ctx.getSource().getServer();
                                            var storage = HostStorageManager.get(server);

                                            if (!storage.hosts.isEmpty()) {
                                                ctx.getSource().sendMessage(Text.literal("§cHost Already Set!"));
                                                return 0;
                                            }


                                            if (profiles.size() > 1) {
                                                ctx.getSource().sendMessage(Text.literal("§cOnly One Player Can Be Host!"));
                                                return 0;
                                            }

                                            profiles.forEach(profile -> {
                                                storage.hosts.add(profile.getId());

                                                ServerPlayerEntity player = server.getPlayerManager().getPlayer(profile.getId());
                                                if (player != null) {
                                                    ItemGiver.giveHostItems(player);
                                                    player.getAbilities().allowFlying = true;
                                                    player.sendAbilitiesUpdate();
                                                }
                                            });

                                            storage.markDirty();
                                            ctx.getSource().sendMessage(Text.literal("§aHost Added!"));
                                            return 1;
                                        })
                                )
                        )
                        .then(CommandManager.literal("remove")
                                .executes(ctx -> {
                                    var source = ctx.getSource();
                                    var server = source.getServer();
                                    var storage = HostStorageManager.get(server);

                                    if (storage.hosts.isEmpty()) {
                                        source.sendMessage(Text.literal("§cNo Hosts Found!"));
                                        return 0;
                                    }

                                    for (java.util.UUID uuid : storage.hosts) {
                                        ServerPlayerEntity player = server.getPlayerManager().getPlayer(uuid);

                                        if (player != null) {
                                            if (!player.isCreative() && !player.isSpectator()) {
                                                player.getAbilities().allowFlying = false;
                                                player.getAbilities().flying = false;
                                                player.sendAbilitiesUpdate();
                                            }
                                            source.sendMessage(Text.literal("§eRemoved Host: " + player.getName().getString()));
                                        }
                                    }

                                    int count = storage.hosts.size();
                                    storage.hosts.clear();
                                    storage.markDirty();

                                    source.sendMessage(Text.literal("§cRemoved " + count + " Host(s)!"));
                                    return count;
                                })

                                .then(CommandManager.argument("player", net.minecraft.command.argument.GameProfileArgumentType.gameProfile())
                                        .executes(ctx -> {
                                            var profiles = net.minecraft.command.argument.GameProfileArgumentType.getProfileArgument(ctx, "player");
                                            var server = ctx.getSource().getServer();
                                            var storage = HostStorageManager.get(server);

                                            if (storage.hosts.isEmpty()) {
                                                ctx.getSource().sendMessage(Text.literal("§cNo Host Found!"));
                                                return 0;
                                            }

                                            profiles.forEach(profile -> {
                                                if (storage.hosts.contains(profile.getId())) {
                                                    storage.hosts.remove(profile.getId());

                                                    ServerPlayerEntity player = server.getPlayerManager().getPlayer(profile.getId());
                                                    if (player != null) {
                                                        if (!player.isCreative() && !player.isSpectator()) {
                                                            player.getAbilities().allowFlying = false;
                                                            player.getAbilities().flying = false;
                                                            player.sendAbilitiesUpdate();
                                                        }
                                                    }
                                                    ctx.getSource().sendMessage(Text.literal("§cRemoved Host: " + profile.getName()));
                                                } else {
                                                    ctx.getSource().sendMessage(Text.literal("§c" + profile.getName() + " Is Not A Host!"));
                                                }
                                            });

                                            storage.markDirty();
                                            return 1;
                                        })
                                )
                        )
        );
    }
}
