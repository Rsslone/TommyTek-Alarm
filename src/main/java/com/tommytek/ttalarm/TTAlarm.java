package com.tommytek.ttalarm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.common.Mod;
import com.tommytek.ttalarm.init.ModBlocks;
import com.tommytek.ttalarm.client.GuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.relauncher.Side;

@Mod(modid = TTAlarm.MODID, name = "TTAlarm", version = "0.1")
public class TTAlarm {
    public static final String MODID = "ttalarm";
    public static final Logger logger = LogManager.getLogger(MODID);
    public static TTAlarm instance;
    public java.util.List<String> availableAlarms = new java.util.ArrayList<>();

    public TTAlarm() {
        instance = this;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        ModBlocks.registerTileEntities();
        NetworkRegistry.INSTANCE.registerGuiHandler(this, new GuiHandler());
        com.tommytek.ttalarm.network.NetworkHelper.createChannel(MODID);
        // register PacketTileEntity for client<->server updates
        com.tommytek.ttalarm.network.NetworkHelper.registerServerToClient(com.zuxelus.zlib.network.PacketTileEntity.class, com.zuxelus.zlib.network.PacketTileEntity.class, 5);
        com.tommytek.ttalarm.network.NetworkHelper.registerClientToServer(com.zuxelus.zlib.network.PacketTileEntity.class, com.zuxelus.zlib.network.PacketTileEntity.class, 6);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        if (event.getSide() == Side.CLIENT) {
            com.tommytek.ttalarm.utils.TTAlarmSoundHelper.importSound();
            com.tommytek.ttalarm.utils.TTAlarmLangHelper.load();
        }
    }
}
