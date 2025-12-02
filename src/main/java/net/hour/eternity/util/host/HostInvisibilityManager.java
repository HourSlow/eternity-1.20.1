package net.hour.eternity.util.host;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.hour.eternity.Eternity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class HostInvisibilityManager {

    public static final Identifier TOGGLE_PACKET = new Identifier(Eternity.MOD_ID, "toggle_host_invis");
    public static final Identifier SYNC_PACKET = new Identifier(Eternity.MOD_ID, "sync_host_invis");

    public static final Set<UUID> INVISIBLE_PLAYERS = new HashSet<>();

    private static KeyBinding hostInvisKey;

    public static void register() {
        ServerPlayNetworking.registerGlobalReceiver(TOGGLE_PACKET, (server, player, handler, buf, responseSender) -> {
            server.execute(() -> {
                var storage = HostStorageManager.get(server);
                if (!storage.hosts.contains(player.getUuid())) {
                    player.sendMessage(Text.literal("§cYou must be a Host to use this!"), true);
                    return;
                }

                if (INVISIBLE_PLAYERS.contains(player.getUuid())) {
                    INVISIBLE_PLAYERS.remove(player.getUuid());
                    player.setInvisible(false);
                    player.sendMessage(Text.literal("§7[Host] §fInvisibility: §cOFF"), true);
                } else {
                    INVISIBLE_PLAYERS.add(player.getUuid());
                    player.setInvisible(true);
                    player.sendMessage(Text.literal("§7[Host] §fInvisibility: §aON"), true);
                }

                syncToAll(server);
            });
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(INVISIBLE_PLAYERS.size());
            INVISIBLE_PLAYERS.forEach(buf::writeUuid);
            sender.sendPacket(SYNC_PACKET, buf);
        });
    }

    private static void syncToAll(net.minecraft.server.MinecraftServer server) {
        PacketByteBuf buf = PacketByteBufs.create();
        buf.writeInt(INVISIBLE_PLAYERS.size());
        INVISIBLE_PLAYERS.forEach(buf::writeUuid);

        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            ServerPlayNetworking.send(player, SYNC_PACKET, buf);
        }
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        hostInvisKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.eternity.host_invis",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_MOUSE_BUTTON_5,
                "category.eternity.host"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (hostInvisKey.wasPressed()) {
                ClientPlayNetworking.send(TOGGLE_PACKET, PacketByteBufs.create());
            }
        });

        ClientPlayNetworking.registerGlobalReceiver(SYNC_PACKET, (client, handler, buf, responseSender) -> {
            int size = buf.readInt();
            Set<UUID> newSet = new HashSet<>();
            for (int i = 0; i < size; i++) {
                newSet.add(buf.readUuid());
            }
            client.execute(() -> {
                INVISIBLE_PLAYERS.clear();
                INVISIBLE_PLAYERS.addAll(newSet);
            });
        });
    }
}
