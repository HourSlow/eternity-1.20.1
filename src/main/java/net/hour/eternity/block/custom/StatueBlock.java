package net.hour.eternity.block.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ShapeContext;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.function.BooleanBiFunction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.util.shape.VoxelShapes;
import net.minecraft.world.BlockView;
import org.jetbrains.annotations.Nullable;

public class StatueBlock extends Block {
    public static final DirectionProperty FACING = Properties.HORIZONTAL_FACING;

    public StatueBlock(Settings settings) {
        super(settings);
    }


    public VoxelShape makeShape(){
        VoxelShape SHAPE_V = VoxelShapes.empty();
        SHAPE_V = VoxelShapes.combine(SHAPE_V, VoxelShapes.cuboid(0, 0, 0, 1, 0.125, 1), BooleanBiFunction.OR);
        SHAPE_V = VoxelShapes.combine(SHAPE_V, VoxelShapes.cuboid(0.1875, 0.125, 0.1875, 0.8125, 2, 0.8125), BooleanBiFunction.OR);

        return SHAPE_V;
    }

    @Override
    public VoxelShape getOutlineShape(BlockState state, BlockView world, BlockPos pos, ShapeContext context) {
        return makeShape();
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx) {
        return this.getDefaultState().with(FACING, ctx.getHorizontalPlayerFacing().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation) {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror) {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
