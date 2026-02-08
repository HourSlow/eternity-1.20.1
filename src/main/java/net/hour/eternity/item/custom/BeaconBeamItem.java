package net.hour.eternity.item.custom;

import net.hour.eternity.entity.ModEntities;
import net.hour.eternity.entity.custom.OrbitalStrikeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class BeaconBeamItem extends Item {

    public BeaconBeamItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        BlockHitResult hit = (BlockHitResult) user.raycast(100.0D, 0.0F, false);
        BlockPos pos = hit.getBlockPos();

        if (!world.isClient) {
            OrbitalStrikeEntity strike = new OrbitalStrikeEntity(ModEntities.STRIKE, world);
            strike.setPosition(pos.toCenterPos());
            world.spawnEntity(strike);
        }
        return TypedActionResult.success(user.getStackInHand(hand));
    }
}
