package net.hour.eternity;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseEntityCallback;
import net.fabricmc.fabric.api.networking.v1.EntityTrackingEvents;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.command.DimTPCommand;
import net.hour.eternity.command.DreamscapeCommand;
import net.hour.eternity.command.HostCommand;
import net.hour.eternity.entity.ModBoats;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.hour.eternity.entity.custom.LilGuyEntity;
import net.hour.eternity.entity.custom.MenaceEntity;
import net.hour.eternity.item.ModItemGroup;
import net.hour.eternity.item.ModItems;
import net.hour.eternity.util.DreamerEntity;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.util.host.*;
import net.hour.eternity.util.inv.InventorySwapHandler;
import net.hour.eternity.util.inv.RespawnCopyHandler;
import net.hour.eternity.util.loottable.ModLootTableModifier;
import net.hour.eternity.util.packets.ModPackets;
import net.hour.eternity.world.dimension.ModDimensions;
import net.hour.eternity.world.gen.ModWorldGeneration;
import net.hour.eternity.world.structure.CenterStructureState;
import net.hour.eternity.world.structure.StructurePlacer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eternity implements ModInitializer {


	public static final String MOD_ID = "eternity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final Identifier DREAM_SYNC_PACKET = new Identifier(MOD_ID, "dream_sync");
    public static final Identifier ORBITAL_VFX = new Identifier(Eternity.MOD_ID, "orbital_strike_vfx");
    public static final Identifier ORBITAL_IMPACT = new Identifier(Eternity.MOD_ID, "orbital_strike_impact");

    //---Useful Commands in Minecraft for testing Eternity:

    // /execute in eternity:limbo_dim run tp Tarr_Mischief ~ ~ ~
    // /locate biome eternity:{biome_name}


	@Override
	public void onInitialize() {
        HostInvisibilityManager.register();
        HostUtil.register();

        ModBlocks.registerModBlocks();

		ModItems.registerModItems();
        ModItemGroup.registerItemGroup();

        ModWorldGeneration.generateModWorldGen();
		ModDimensions.register();

		ModEntities.registerModEntities();
        ModBoats.registerBoats();

        ModLootTableModifier.modifyLootTables();

        ModPackets.registerPackets();

        InventorySwapHandler.register();
        RespawnCopyHandler.register();

        //Wood Registries

        StrippableBlockRegistry.register(ModBlocks.EVERGLOOM_LOG, ModBlocks.STRIPPED_EVERGLOOM_LOG);
        StrippableBlockRegistry.register(ModBlocks.EVERGLOOM_WOOD, ModBlocks.STRIPPED_EVERGLOOM_WOOD);

        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_EVERGLOOM_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_EVERGLOOM_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_LEAVES, 30, 60);

        //Entity Registries

		FabricDefaultAttributeRegistry.register(ModEntities.THE_FORGOTTEN,
				ForgottenEntity.createTheForgottenAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LILGUY,
                LilGuyEntity.createLilGuyAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MENACE,
               MenaceEntity.createTheMenaceAttributes());

        // Command Registration Events

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                HostCommand.register(dispatcher)
        );
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            DreamscapeCommand.register(dispatcher);
        });
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            DimTPCommand.register(dispatcher);
        });
        //        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
//                DisguiseCommand.register(dispatcher, registryAccess)
//        );



        //DREAMSCAPE Event Handlers

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) -> {
            if (((DreamerEntity) player).isDreaming()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (((DreamerEntity) player).isDreaming()) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            boolean playerDreaming = ((DreamerEntity) player).isDreaming();

            boolean targetDreaming = false;
            if (entity instanceof DreamerEntity targetDreamer) {
                targetDreaming = targetDreamer.isDreaming();
            }

            if (playerDreaming != targetDreaming) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });

        UseEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            boolean playerDreaming = ((DreamerEntity) player).isDreaming();

            boolean targetDreaming = false;
            if (entity instanceof DreamerEntity targetDreamer) {
                targetDreaming = targetDreamer.isDreaming();
            }

            if (playerDreaming != targetDreaming) {
                return ActionResult.FAIL;
            }

            return ActionResult.PASS;
        });

        EntityTrackingEvents.START_TRACKING.register((trackedEntity, trackingPlayer) -> {
            if (trackedEntity instanceof DreamerEntity dreamer) {
                boolean isDreaming = dreamer.isDreaming();

                PacketByteBuf buf = PacketByteBufs.create();
                buf.writeInt(trackedEntity.getId());
                buf.writeBoolean(isDreaming);

                ServerPlayNetworking.send(trackingPlayer, Eternity.DREAM_SYNC_PACKET, buf);
            }
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity player = handler.player;
            boolean isDreaming = ((DreamerEntity) player).isDreaming();

            PacketByteBuf buf = PacketByteBufs.create();
            buf.writeInt(player.getId());
            buf.writeBoolean(isDreaming);

            ServerPlayNetworking.send(player, Eternity.DREAM_SYNC_PACKET, buf);
        });


        //Limbo Death stuff

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            if (!alive && oldPlayer.getWorld().getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY)) {
                ServerWorld customWorld = oldPlayer.getServer().getWorld(ModDimensions.LIMBO_DIM_KEY);
                if (customWorld != null) {
                    BlockPos spawn = customWorld.getSpawnPos();

                    InventorySwapHandler.SKIP_NEXT_SWAP.add(newPlayer.getUuid());

                    newPlayer.teleport(customWorld, spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5,
                            newPlayer.getYaw(), newPlayer.getPitch());

                    InventorySwapHandler.SKIP_NEXT_SWAP.remove(newPlayer.getUuid());
                }
            }
        });

        // Temple in Center of World

        ServerWorldEvents.LOAD.register((server, world) -> {
            if (world.getRegistryKey() != net.minecraft.world.World.OVERWORLD) {
                return;
            }
            CenterStructureState state = CenterStructureState.getState(world);

            if (!state.isSpawned) {
                int centerX = 0;
                int centerZ = 0;

                int centerY = world.getChunk(centerX >> 4, centerZ >> 4)
                        .getHeightmap(net.minecraft.world.Heightmap.Type.WORLD_SURFACE)
                        .get(centerX & 15, centerZ & 15);

                BlockPos spawnPos = new BlockPos(centerX, centerY - 80, centerZ);

                StructurePlacer.placeStructure(world, spawnPos);

                state.isSpawned = true;
                state.markDirty();
            }
        });



	}

    public static MinecraftServer SERVER_INSTANCE;

    public static boolean isHost(ServerPlayerEntity player) {
        if (SERVER_INSTANCE == null) {
            return false;
        }
        HostStorage storage = HostStorageManager.get(SERVER_INSTANCE);
        return storage.hosts.contains(player.getUuid());
    }
    public static boolean isClientHost(ClientPlayerEntity player) {
        if (SERVER_INSTANCE == null) {
            return false;
        }
        HostStorage storage = HostStorageManager.get(SERVER_INSTANCE);
        return storage.hosts.contains(player.getUuid());
    }
}