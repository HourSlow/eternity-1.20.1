package net.hour.eternity.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.argument.RegistryEntryArgumentType;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import xyz.nucleoid.disguiselib.api.EntityDisguise;

import java.util.Optional;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DisguiseCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("disguise")
                .requires(source -> source.hasPermissionLevel(2))

                .then(literal("player")
                        .then(argument("target", StringArgumentType.word())
                                .executes(ctx -> disguiseAsPlayer(ctx))
                        )
                )
                .then(literal("mob")
                        .then(argument("type", RegistryEntryArgumentType.registryEntry(registryAccess, RegistryKeys.ENTITY_TYPE))
                                .executes(ctx -> disguiseAsMob(ctx))
                        )
                )
                .then(literal("clear")
                        .executes((CommandContext<ServerCommandSource> ctx) -> {
                            ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
                            ((EntityDisguise) player).removeDisguise();

                            ctx.getSource().sendFeedback(() -> Text.literal("Disguise Cleared!"), false);
                            return 1;
                        })
                )
        );
    }

    private static int disguiseAsPlayer(CommandContext<ServerCommandSource> ctx) throws CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
        String targetName = StringArgumentType.getString(ctx, "target");

        Optional<GameProfile> profileOpt = ctx.getSource().getServer().getUserCache().findByName(targetName);

        if (profileOpt.isEmpty()) {
            ctx.getSource().sendFeedback(() -> Text.literal("§cCould not find player: " + targetName), false);
            return 0;
        }

        GameProfile targetProfile = profileOpt.get();
        EntityDisguise disguise = (EntityDisguise) player;
        disguise.disguiseAs(EntityType.PLAYER);
        disguise.setGameProfile(targetProfile);

        ctx.getSource().sendFeedback(() -> Text.literal("Disguised as " + targetProfile.getName()), false);
        return 1;
    }

    private static int disguiseAsMob(CommandContext<ServerCommandSource> ctx) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();

        var entry = RegistryEntryArgumentType.getRegistryEntry(ctx, "type", RegistryKeys.ENTITY_TYPE);
        EntityType<?> type = entry.value();

        EntityDisguise disguise = (EntityDisguise) player;
        disguise.disguiseAs(type);

        net.minecraft.entity.Entity dummy = disguise.getDisguiseEntity();
        if (dummy != null) {

            dummy.setNoGravity(false);
            dummy.setInvisible(false);

            dummy.calculateDimensions();
        }
        ctx.getSource().sendFeedback(() -> Text.literal("Disguised as " + type.getName().getString()), false);
        return 1;
    }
}