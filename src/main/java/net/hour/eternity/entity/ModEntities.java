package net.hour.eternity.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.hour.eternity.Eternity;
import net.hour.eternity.entity.custom.ForgottenEntity;
import net.hour.eternity.entity.custom.LilGuyEntity;
import net.hour.eternity.entity.custom.MenaceEntity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {

    public static final EntityType<ForgottenEntity> THE_FORGOTTEN = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Eternity.MOD_ID, "the_forgotten"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, ForgottenEntity::new)
                    .dimensions(EntityDimensions.fixed(0.6f,1.9f)).build());

    public static final EntityType<LilGuyEntity> LILGUY = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Eternity.MOD_ID, "lilguy"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, LilGuyEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f,0.7f)).build());

    public static final EntityType<MenaceEntity> MENACE = Registry.register(Registries.ENTITY_TYPE,
            new Identifier(Eternity.MOD_ID, "menace"),
            FabricEntityTypeBuilder.create(SpawnGroup.MONSTER, MenaceEntity::new)
                    .dimensions(EntityDimensions.fixed(0.7f,4.2f)).build());



    public static void registerModEntities() {
        Eternity.LOGGER.debug(Eternity.MOD_ID, " - ModItems");
    }
}
