package net.hour.eternity.entity.client;

import net.hour.eternity.Eternity;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;

public class ModModelLayers {
    public static final EntityModelLayer THE_FORGOTTEN =
            new EntityModelLayer(new Identifier(Eternity.MOD_ID,"the_forgotten"),"main");

    public static final EntityModelLayer LILGUY =
            new EntityModelLayer(new Identifier(Eternity.MOD_ID,"lilguy"),"main");

    public static final EntityModelLayer MENACE =
            new EntityModelLayer(new Identifier(Eternity.MOD_ID,"menace"),"main");
}
