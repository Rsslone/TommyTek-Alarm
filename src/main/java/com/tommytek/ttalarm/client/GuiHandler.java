package com.tommytek.ttalarm.client;

import com.tommytek.ttalarm.TTAlarm;
import com.tommytek.ttalarm.tileentities.TileEntityHowlerAlarm;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        // No server-side container for the howler alarm — it's a GuiScreen, not a GuiContainer
        return null;
    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEntityHowlerAlarm) {
            TileEntityHowlerAlarm alarm = (TileEntityHowlerAlarm) te;
            boolean isBig = TTAlarm.instance.availableAlarms.size() > 10;
            return new GuiHowlerAlarm(alarm, isBig);
        }
        return null;
    }
}
