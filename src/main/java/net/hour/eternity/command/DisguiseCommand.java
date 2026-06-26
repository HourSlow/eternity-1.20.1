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
//import xyz.nucleoid.disguiselib.api.EntityDisguise;

import java.util.Optional;
import java.util.UUID;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class DisguiseCommand {

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess) {
        dispatcher.register(literal("disguise")
                .requires(source -> source.hasPermissionLevel(2))

                .then(literal("clear")
                        .executes(ctx -> clearDisguise(ctx))
                )

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
        );
    }

    private static int disguiseAsPlayer(CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
            String targetName = StringArgumentType.getString(ctx, "target");

            GameProfile targetProfile = ctx.getSource().getServer().getUserCache()
                    .findByName(targetName)
                    .orElse(new GameProfile(UUID.randomUUID(), targetName));

            //EntityDisguise disguise = (EntityDisguise) player;

           // disguise.removeDisguise();

           // disguise.disguiseAs(EntityType.PLAYER);
           // disguise.setGameProfile(targetProfile);

            player.setNoGravity(false);
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().flying = false;
            }
            player.sendAbilitiesUpdate();

            ctx.getSource().sendFeedback(() -> Text.literal("§aDisguised as player: " + targetName), false);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendError(Text.literal("Failed to set player disguise: " + e.getMessage()));
            return 0;
        }
    }

    private static int disguiseAsMob(CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
            var entry = RegistryEntryArgumentType.getRegistryEntry(ctx, "type", RegistryKeys.ENTITY_TYPE);
            EntityType<?> type = entry.value();

           // EntityDisguise disguise = (EntityDisguise) player;

          //  disguise.removeDisguise();
          //  disguise.disguiseAs(type);

         //   var dummy = disguise.getDisguiseEntity();
          //  if (dummy != null) {
        //        dummy.setNoGravity(false);
        //        dummy.setInvisible(false);
        //        dummy.calculateDimensions();
        //    }

            player.setNoGravity(false);
            player.setVelocity(0, player.getVelocity().y, 0);

            ctx.getSource().sendFeedback(() -> Text.literal("§aDisguised as mob: " + type.getName().getString()), false);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendError(Text.literal("Failed to set mob disguise: " + e.getMessage()));
            return 0;
        }
    }

    private static int clearDisguise(CommandContext<ServerCommandSource> ctx) {
        try {
            ServerPlayerEntity player = ctx.getSource().getPlayerOrThrow();
//            EntityDisguise disguise = (EntityDisguise) player;
//
//            disguise.removeDisguise();

            player.setNoGravity(false);
            if (!player.isCreative() && !player.isSpectator()) {
                player.getAbilities().flying = false;
                player.getAbilities().allowFlying = false;
            }
            player.sendAbilitiesUpdate();
            player.setVelocity(0, 0, 0);
            player.fallDistance = 0;

            ctx.getSource().sendFeedback(() -> Text.literal("§eDisguise cleared!"), false);
            return 1;
        } catch (Exception e) {
            ctx.getSource().sendError(Text.literal("Failed to clear disguise: " + e.getMessage()));
            return 0;
        }
    }
}