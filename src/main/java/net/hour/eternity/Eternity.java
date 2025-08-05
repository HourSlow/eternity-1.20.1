package net.hour.eternity;

import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.hour.eternity.item.ModItems;
import net.hour.eternity.shader.GrayscaleProcessor;
import net.hour.eternity.world.dimension.ModDimensions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Eternity implements ModInitializer {

	public static final String MOD_ID = "eternity";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModDimensions.register();

		FabricDefaultAttributeRegistry.register(ModEntities.THE_FORGOTTEN,
				ForgottenEntity.createTheForgottenAttributes());

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
	}
}