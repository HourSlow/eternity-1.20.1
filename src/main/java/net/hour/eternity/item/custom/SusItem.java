package net.hour.eternity.item.custom;

import net.hour.eternity.block.ModBlocks;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;


public class SusItem extends Item {

    public SusItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if (!world.isClient) {
            if (world.getBlockState(pos).isOf(ModBlocks.STATUE)) {

                LightningEntity lightning = EntityType.LIGHTNING_BOLT.create(world);
                if (lightning != null) {
                    lightning.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(pos));
                    world.spawnEntity(lightning);
                }
                world.breakBlock(pos, false);
                world.createExplosion(null, pos.getX(), pos.getY(), pos.getZ(), 2.0f, World.ExplosionSourceType.BLOCK);

                ServerPlayerEntity targetPlayer = world.getServer().getPlayerManager().getPlayer("Tarr_Mischief");
                if (targetPlayer != null) {
                    targetPlayer.teleport(
                            pos.getX() + 0.5,
                            pos.getY() + 0.5,
                            pos.getZ() + 0.5
                    );
                }
                return ActionResult.SUCCESS;
            }
        }
        return ActionResult.PASS;
    }
}
