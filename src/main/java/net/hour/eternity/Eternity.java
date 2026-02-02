package net.hour.eternity;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerWorldEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.hour.eternity.block.ModBlocks;
import net.hour.eternity.command.HostCommand;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.hour.eternity.entity.custom.LilGuyEntity;
import net.hour.eternity.entity.custom.MenaceEntity;
import net.hour.eternity.item.ModItemGroup;
import net.hour.eternity.item.ModItems;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.util.host.HostInvisibilityManager;
import net.hour.eternity.util.host.HostStorage;
import net.hour.eternity.util.host.HostStorageManager;
import net.hour.eternity.util.host.HostUtil;
import net.hour.eternity.util.inv.InventorySwapHandler;
import net.hour.eternity.util.inv.RespawnCopyHandler;
import net.hour.eternity.util.packets.ModClientPackets;
import net.hour.eternity.util.packets.ModPackets;
import net.hour.eternity.world.dimension.ModDimensions;
import net.hour.eternity.world.gen.ModWorldGeneration;
import net.hour.eternity.world.structure.CenterStructureState;
import net.hour.eternity.world.structure.StructurePlacer;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eternity implements ModInitializer {

	public static final String MOD_ID = "eternity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);


    public static final Identifier ORBITAL_VFX = new Identifier(Eternity.MOD_ID, "orbital_strike_vfx");
    public static final Identifier ORBITAL_IMPACT = new Identifier(Eternity.MOD_ID, "orbital_strike_impact");

    //---Useful Commands in Minecraft for testing Eternity:

    // /execute in eternity:limbo_dim run tp Tarr_Mischief ~ ~ ~
    // /locate biome eternity:{biome_name}


	@Override
	public void onInitialize() {
        HostInvisibilityManager.register();

        ModBlocks.registerModBlocks();

		ModItems.registerModItems();
        ModItemGroup.registerItemGroup();

        ModWorldGeneration.generateModWorldGen();
		ModDimensions.register();

		ModEntities.registerModEntities();
        HostUtil.register();

        ModPackets.registerPackets();
        ModClientPackets.registerClientPackets();

        InventorySwapHandler.register();
        RespawnCopyHandler.register();


        StrippableBlockRegistry.register(ModBlocks.EVERGLOOM_LOG, ModBlocks.STRIPPED_EVERGLOOM_LOG);
        StrippableBlockRegistry.register(ModBlocks.EVERGLOOM_WOOD, ModBlocks.STRIPPED_EVERGLOOM_WOOD);


        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_EVERGLOOM_LOG, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.STRIPPED_EVERGLOOM_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_WOOD, 5, 5);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_PLANKS, 5, 20);
        FlammableBlockRegistry.getDefaultInstance().add(ModBlocks.EVERGLOOM_LEAVES, 30, 60);


		FabricDefaultAttributeRegistry.register(ModEntities.THE_FORGOTTEN,
				ForgottenEntity.createTheForgottenAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.LILGUY,
                LilGuyEntity.createLilGuyAttributes());
        FabricDefaultAttributeRegistry.register(ModEntities.MENACE,
               MenaceEntity.createTheMenaceAttributes());


        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) ->
                HostCommand.register(dispatcher)
        );



		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (client.world == null) {
				GrayscaleProcessor.INSTANCE.setActive(false);
				return;
			}

			RegistryKey<World> current = client.world.getRegistryKey();
			if (current == ModDimensions.LIMBO_DIM_KEY) {
				GrayscaleProcessor.INSTANCE.setActive(true);
			} else {
				GrayscaleProcessor.INSTANCE.setActive(false);
			}
		});

		ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
			if (!alive && oldPlayer.getWorld().getRegistryKey().equals(ModDimensions.LIMBO_DIM_KEY)) {
				ServerWorld customWorld = oldPlayer.getServer().getWorld(ModDimensions.LIMBO_DIM_KEY);
				if (customWorld != null) {
					BlockPos spawn = customWorld.getSpawnPos();

					newPlayer.teleport(customWorld, spawn.getX() + 0.5, spawn.getY(), spawn.getZ() + 0.5,
							newPlayer.getYaw(), newPlayer.getPitch());
				}
			}
		});


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