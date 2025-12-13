package net.hour.eternity.world.structure;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.PersistentState;
import net.minecraft.world.PersistentStateManager;
import net.minecraft.world.World;

public class CenterStructureState extends PersistentState {

    private static final String ID = "center_structure_spawn_flag";
    public boolean isSpawned = false;

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putBoolean("is_spawned", isSpawned);
        return nbt;
    }

    public static CenterStructureState createFromNbt(NbtCompound nbt) {
        CenterStructureState state = new CenterStructureState();
        state.isSpawned = nbt.getBoolean("is_spawned");
        return state;
    }

    public static CenterStructureState getState(World world) {
        if (!(world instanceof ServerWorld serverWorld)) {

            throw new IllegalStateException("Attempted to get state from client world!");
        }
        PersistentStateManager manager = serverWorld.getPersistentStateManager();

        return manager.getOrCreate(CenterStructureState::createFromNbt, CenterStructureState::new, ID);
    }
}
