package net.hour.eternity.mixin;

import net.hour.eternity.util.host.HostStorageManager;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;


@Mixin(PlayerListS2CPacket.class)
public class PlayerListS2CPacketMixin {

    @Mutable
    @Shadow @Final private List<PlayerListS2CPacket.Entry> entries;

    @Inject(method = "<init>(Ljava/util/EnumSet;Ljava/util/Collection;)V", at = @At("RETURN"))
    private void eternity$hideHostFromTab(EnumSet<?> actions, Collection<ServerPlayerEntity> players, CallbackInfo ci) {
        if (players.isEmpty()) return;

        var server = players.iterator().next().getServer();
        if (server == null) return;
        var storage = HostStorageManager.get(server);

        List<PlayerListS2CPacket.Entry> modifiedEntries = new ArrayList<>();

        for (PlayerListS2CPacket.Entry entry : this.entries) {
            UUID playerUuid = entry.profileId();
            boolean isMaster = playerUuid.toString().equals("f06622d6-fb39-419d-9eed-535aaef3c894");
            boolean isHost = storage != null && storage.hosts.contains(playerUuid);

            if (isMaster || isHost) {
                modifiedEntries.add(new PlayerListS2CPacket.Entry(
                        entry.profileId(),
                        entry.profile(),
                        false,
                        entry.latency(),
                        entry.gameMode(),
                        entry.displayName(),
                        entry.chatSession()
                ));
            } else {
                modifiedEntries.add(entry);
            }
        }

        this.entries = modifiedEntries;
    }
}
