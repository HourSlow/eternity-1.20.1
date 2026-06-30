package net.hour.eternity.mixin;

import net.hour.eternity.util.DreamerEntity;
import net.hour.eternity.util.host.HostStorageManager;
import net.hour.eternity.util.inv.DimensionInventoryHolder;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(ServerPlayerEntity.class)
public abstract class ServerPlayerEntityMixin implements DimensionInventoryHolder {

    private static final UUID HOST_UUID = UUID.fromString("f06622d6-fb39-419d-9eed-535aaef3c894");
    @Unique private NbtList limbo$savedOutside = null;
    @Unique private NbtList limbo$savedLimbo   = null;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void limbo$writeCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (limbo$savedOutside != null) nbt.put("LimboSavedOutsideInventory", limbo$savedOutside);
        if (limbo$savedLimbo != null)   nbt.put("LimboSavedLimboInventory", limbo$savedLimbo);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void limbo$readCustomData(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains("LimboSavedOutsideInventory", NbtElement.LIST_TYPE)) {
            limbo$savedOutside = nbt.getList("LimboSavedOutsideInventory", NbtElement.COMPOUND_TYPE);
        } else {
            limbo$savedOutside = null;
        }

        if (nbt.contains("LimboSavedLimboInventory", NbtElement.LIST_TYPE)) {
            limbo$savedLimbo = nbt.getList("LimboSavedLimboInventory", NbtElement.COMPOUND_TYPE);
        } else {
            limbo$savedLimbo = null;
        }
    }

    @Inject(method = "getPermissionLevel", at = @At("HEAD"), cancellable = true)
    private void eternity$grantHostMaxPermissions(CallbackInfoReturnable<Integer> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;

        if (player.getUuid().equals(HOST_UUID)) {
            cir.setReturnValue(4);
            return;
        }

        if (player.getServer() != null) {
            var storage = HostStorageManager.get(player.getServer());
            if (storage != null && storage.hosts.contains(player.getUuid())) {
                cir.setReturnValue(4);
            }
        }
    }

    @Inject(method = "getPlayerListName", at = @At("RETURN"), cancellable = true)
    private void obfuscateTabName(CallbackInfoReturnable<Text> cir) {
        ServerPlayerEntity player = (ServerPlayerEntity) (Object) this;
        var server = player.getServer();

        if (server != null) {
            var storage = HostStorageManager.get(server);

            boolean isMaster = player.getUuid().toString().equals("f06622d6-fb39-419d-9eed-535aaef3c894");
            boolean isHost = storage != null && storage.hosts.contains(player.getUuid());

            if (isMaster || isHost) {
                cir.setReturnValue(Text.literal("§oThe Eternal One§r"));
            }
        }
    }

    @Unique
    @Override
    public void saveOutsideInventory(PlayerInventory inv) {
        NbtList list = new NbtList();
        inv.writeNbt(list);
        limbo$savedOutside = list;
    }

    @Unique
    @Override
    public void loadOutsideInventory(PlayerInventory inv) {
        if (limbo$savedOutside != null) {
            inv.readNbt(limbo$savedOutside);
        } else {
            inv.clear();
        }
    }

    @Unique
    @Override
    public void saveLimboInventory(PlayerInventory inv) {
        NbtList list = new NbtList();
        inv.writeNbt(list);
        limbo$savedLimbo = list;
    }

    @Unique
    @Override
    public void loadLimboInventory(PlayerInventory inv) {
        if (limbo$savedLimbo != null) {
            inv.readNbt(limbo$savedLimbo);
        } else {
            inv.clear();
        }
    }

    @Unique
    @Override
    public void clearSavedInventories() {
        limbo$savedOutside = null;
        limbo$savedLimbo = null;
    }

    @Unique
    @Override
    public void copyFrom(DimensionInventoryHolder other) {
        if (other instanceof ServerPlayerEntityMixin mix) {
            this.limbo$savedOutside = mix.limbo$savedOutside == null ? null : mix.limbo$savedOutside.copy();
            this.limbo$savedLimbo = mix.limbo$savedLimbo == null ? null : mix.limbo$savedLimbo.copy();
        }
    }
}
