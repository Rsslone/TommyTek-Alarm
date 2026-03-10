package com.zuxelus.zlib.blocks;

import net.minecraft.block.BlockDirectional;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class FacingBlock extends BlockDirectional implements ITileEntityProvider {

    public FacingBlock() {
        super(net.minecraft.block.material.Material.IRON);
        setHardness(3.0F);
    }

    protected abstract com.zuxelus.zlib.tileentities.TileEntityFacing createTileEntity();

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        com.zuxelus.zlib.tileentities.TileEntityFacing te = createTileEntity();
        if (te != null)
            te.setFacing(meta);
        return te;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(FACING, EnumFacing.values()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        if (placer.rotationPitch >= 65)
            return getDefaultState().withProperty(FACING, EnumFacing.UP);
        if (placer.rotationPitch <= -65)
            return getDefaultState().withProperty(FACING, EnumFacing.DOWN);
        switch (net.minecraft.util.math.MathHelper.floor(placer.rotationYaw * 4.0F / 360.0F + 0.5D) & 3) {
        case 0:
            return getDefaultState().withProperty(FACING, EnumFacing.NORTH);
        case 1:
            return getDefaultState().withProperty(FACING, EnumFacing.EAST);
        case 2:
            return getDefaultState().withProperty(FACING, EnumFacing.SOUTH);
        case 3:
            return getDefaultState().withProperty(FACING, EnumFacing.WEST);
        }
        return getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof com.zuxelus.zlib.tileentities.TileEntityFacing) {
            com.zuxelus.zlib.tileentities.TileEntityFacing facing = (com.zuxelus.zlib.tileentities.TileEntityFacing) te;
            switch (facing.getFacing()) {
            case UP:
            case DOWN:
                facing.setRotation(placer.getHorizontalFacing().getOpposite());
                break;
            default:
                facing.setRotation(EnumFacing.DOWN);
                break;
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof net.minecraft.inventory.IInventory) {
            net.minecraft.inventory.InventoryHelper.dropInventoryItems(world, pos, (net.minecraft.inventory.IInventory) te);
            world.updateComparatorOutputLevel(pos, this);
        }
        super.breakBlock(world, pos, state);
    }

    protected abstract int getBlockGuiId();
}
