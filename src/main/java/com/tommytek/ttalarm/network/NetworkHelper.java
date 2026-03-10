package com.tommytek.ttalarm.network;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

import com.zuxelus.zlib.network.PacketTileEntity;

public class NetworkHelper {
    public static SimpleNetworkWrapper network;

    public static void createChannel(String name) {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(name);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerClientToServer(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> request, int id) {
        network.registerMessage(handler, request, id, Side.SERVER);
    }

    public static <REQ extends IMessage, REPLY extends IMessage> void registerServerToClient(Class<? extends IMessageHandler<REQ, REPLY>> handler, Class<REQ> request, int id) {
        network.registerMessage(handler, request, id, Side.CLIENT);
    }

    public static void updateSeverTileEntity(BlockPos pos, int type, String string) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("type", type);
        tag.setString("string", string);
        network.sendToServer(new PacketTileEntity(pos, tag));
    }

    public static void updateSeverTileEntity(BlockPos pos, int type, int value) {
        NBTTagCompound tag = new NBTTagCompound();
        tag.setInteger("type", type);
        tag.setInteger("value", value);
        network.sendToServer(new PacketTileEntity(pos, tag));
    }

    public static void updateSeverTileEntity(BlockPos pos, NBTTagCompound tag) {
        network.sendToServer(new PacketTileEntity(pos, tag));
    }

    public static void sendPacketToAllAround(World world, BlockPos pos, int dist, IMessage packet) {
        network.sendToAllAround(packet, new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), dist));
    }
}
