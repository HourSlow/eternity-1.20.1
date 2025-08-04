package net.hour.eternity.world.dimension;

import net.hour.eternity.Eternity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.minecraft.world.dimension.DimensionType;

public class ModDimensions {

    public static final RegistryKey<World> LIMBO_DIM_KEY = RegistryKey.of(RegistryKeys.WORLD,
            new Identifier(Eternity.MOD_ID, "limbo_dim"));

    public static final RegistryKey<DimensionType> LIMBO_DIM_TYPE = RegistryKey.of(RegistryKeys.DIMENSION_TYPE,
            LIMBO_DIM_KEY.getValue());



    public static void register() {
        Eternity.LOGGER.debug("Eternity - Dimensions");
    }
}