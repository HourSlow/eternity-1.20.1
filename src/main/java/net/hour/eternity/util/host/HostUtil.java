package net.hour.eternity.util.host;

import net.fabricmc.fabric.api.entity.event.v1.ServerEntityWorldChangeEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;

import java.util.UUID;

public class HostUtil {

    public static void register() {

        ServerEntityWorldChangeEvents.AFTER_PLAYER_CHANGE_WORLD.register((player, origin, destination) -> {
            var storage = HostStorageManager.get(player.getServer());
            boolean isMaster = player.getUuid().toString().equals("f06622d6-fb39-419d-9eed-535aaef3c894");
            boolean isHost = storage != null && storage.hosts.contains(player.getUuid());

            if (isMaster || isHost) {
                player.getAbilities().allowFlying = true;
                player.sendAbilitiesUpdate();
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            var storage = HostStorageManager.get(server);
            boolean isMaster = player.getUuid().toString().equals("f06622d6-fb39-419d-9eed-535aaef3c894");
            boolean isHost = storage != null && storage.hosts.contains(player.getUuid());

            if (isMaster || isHost) {
                player.getAbilities().allowFlying = true;
                player.sendAbilitiesUpdate();
            }
        });

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            var storage = HostStorageManager.get(server);
            Scoreboard scoreboard = server.getScoreboard();

            Team hostTeam = scoreboard.getTeam("eternity_hosts");
            if (hostTeam == null) {
                hostTeam = scoreboard.addTeam("eternity_hosts");
                hostTeam.setNameTagVisibilityRule(AbstractTeam.VisibilityRule.NEVER);

                hostTeam.setCollisionRule(AbstractTeam.CollisionRule.NEVER);
            }

            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                if (player.isCreative() || player.isSpectator()) continue;

                boolean isMaster = player.getUuid().toString().equals("f06622d6-fb39-419d-9eed-535aaef3c894");
                boolean isHost = storage.hosts.contains(player.getUuid());

                if (isMaster || isHost) {
                    if (!player.getAbilities().allowFlying) {
                        player.getAbilities().allowFlying = true;
                        player.sendAbilitiesUpdate();
                    }

                    if (player.getScoreboardTeam() != hostTeam) {
                        scoreboard.addPlayerToTeam(player.getEntityName(), hostTeam);
                    }

                    if (!player.isSilent()) {
                        player.setSilent(true);
                    }

                    if (!player.isInvulnerable()) {
                        player.setInvulnerable(true);
                    }

                } else {
                    if (player.getAbilities().allowFlying) {
                        player.getAbilities().allowFlying = false;
                        player.getAbilities().flying = false;
                        player.sendAbilitiesUpdate();
                    }

                    if (player.getScoreboardTeam() == hostTeam) {
                        scoreboard.removePlayerFromTeam(player.getEntityName(), hostTeam);
                    }

                    if (player.isSilent()) {
                        player.setSilent(false);
                    }

                    if (player.isInvulnerable()) {
                        player.setInvulnerable(false);
                    }
                }
            }
        });
    }
}