package net.hour.eternity.world.biome;

import net.minecraft.util.Identifier;
import terrablender.api.Region;
import terrablender.api.RegionType;

public class ModOverworldRegion extends Region {

    public ModOverworldRegion(Identifier name, int weight) {
        super(name, RegionType.OVERWORLD, weight);
    }
}
