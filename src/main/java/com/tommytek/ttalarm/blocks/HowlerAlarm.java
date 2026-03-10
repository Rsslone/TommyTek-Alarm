package com.tommytek.ttalarm.blocks;

import com.tommytek.ttalarm.tileentities.TileEntityHowlerAlarm;
import com.zuxelus.zlib.blocks.FacingBlockSmall;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class HowlerAlarm extends FacingBlockSmall {
    protected static final AxisAlignedBB AABB_DOWN = new AxisAlignedBB(0.125D, 0.5625D, 0.125D, 0.875D, 1.0D, 0.875D);
    protected static final AxisAlignedBB AABB_UP = new AxisAlignedBB(0.125D, 0.0D, 0.125D, 0.875D, 0.4375D, 0.875D);
    protected static final AxisAlignedBB AABB_NORTH = new AxisAlignedBB(0.125D, 0.125D, 0.5625D, 0.875D, 0.875D, 1.0D);
    protected static final AxisAlignedBB AABB_SOUTH = new AxisAlignedBB(0.125D, 0.125D, 0.0D, 0.875D, 0.875D, 0.4375D);
    protected static final AxisAlignedBB AABB_WEST = new AxisAlignedBB(0.5625D, 0.125D, 0.125D, 1.0D, 0.875D, 0.875D);
    protected static final AxisAlignedBB AABB_EAST = new AxisAlignedBB(0.0D, 0.125D, 0.125D, 0.4375D, 0.875D, 0.875D);

    @Override
    protected com.zuxelus.zlib.tileentities.TileEntityFacing createTileEntity() {
        return new TileEntityHowlerAlarm();
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, net.minecraft.block.Block block, BlockPos fromPos) {
        super.neighborChanged(state, world, pos, block, fromPos);
        if (!world.isRemote) {
            world.notifyBlockUpdate(pos, state, state, 2);
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, net.minecraft.world.IBlockAccess source, BlockPos pos) {
        switch (state.getValue(FACING)) {
        case EAST:
            return AABB_EAST;
        case WEST:
            return AABB_WEST;
        case SOUTH:
            return AABB_SOUTH;
        case NORTH:
        default:
            return AABB_NORTH;
        case UP:
            return AABB_UP;
        case DOWN:
            return AABB_DOWN;
        }
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, net.minecraft.util.EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (world.isRemote)
            player.openGui(com.tommytek.ttalarm.TTAlarm.instance, getBlockGuiId(), world, pos.getX(), pos.getY(), pos.getZ());
        return true;
    }

    public int getBlockGuiId() {
        return 0;
    }
}
