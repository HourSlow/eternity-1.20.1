package net.hour.eternity.util.inv;

import net.minecraft.entity.player.PlayerInventory;

public interface DimensionInventoryHolder {
    void saveOutsideInventory(PlayerInventory inv);
    void loadOutsideInventory(PlayerInventory inv);

    void saveLimboInventory(PlayerInventory inv);
    void loadLimboInventory(PlayerInventory inv);

    void clearSavedInventories(); // clears both saved fields
    void copyFrom(DimensionInventoryHolder other);
}
