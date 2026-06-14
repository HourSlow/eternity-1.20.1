package net.hour.eternity.mixin;

import net.hour.eternity.util.DreamerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ClientPlayerEntity.class)
public class ClientPlayerEntityMixin implements DreamerEntity {
    private boolean isDreaming = false;

    @Override
    public boolean isDreaming() { return isDreaming; }

    @Override
    public void setDreaming(boolean dreaming) { this.isDreaming = dreaming; }

    @Override
    public boolean canPickUpLoot() {
        return false;
    }
}
