package net.hour.eternity.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.hour.eternity.Eternity;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {
    public static final EntityType<ForgottenEntity> THE_FORGOTTEN = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Eternity.MOD_ID, "the_forgotten"),
            FabricEntityTypeBuilder.create(SpawnGroup.MISC, ForgottenEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f,2f)).build());
}
