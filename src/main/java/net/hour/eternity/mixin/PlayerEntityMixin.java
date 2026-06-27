package net.hour.eternity.mixin;

import net.hour.eternity.util.DreamerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends Entity implements DreamerEntity {

    public PlayerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Unique
    private static final TrackedData<Boolean> IS_DREAMING = DataTracker.registerData(PlayerEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    @Inject(method = "initDataTracker", at = @At("TAIL"))
    protected void eternity$initDreamTracker(CallbackInfo ci) {
        this.dataTracker.startTracking(IS_DREAMING, false);
    }

    @Override
    public boolean isDreaming() {
        return this.dataTracker.get(IS_DREAMING);
    }

    @Override
    public void setDreaming(boolean dreaming) {
        this.dataTracker.set(IS_DREAMING, dreaming);
    }

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void eternity$writeDreamData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putBoolean("IsDreaming", this.isDreaming());
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void eternity$readDreamData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("IsDreaming")) {
            this.setDreaming(nbt.getBoolean("IsDreaming"));
        }
    }
}
