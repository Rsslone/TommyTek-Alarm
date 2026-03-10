package com.tommytek.ttalarm;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeInt;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = TTAlarm.MODID)
@LangKey("ttalarm.config.title")
@EventBusSubscriber(modid = TTAlarm.MODID)
public class TTAlarmConfig {

    @RangeInt(min = 0, max = 256)
    @LangKey("ttalarm.config.maxAlarmRange")
    public static int maxAlarmRange = 128;

    @RangeInt(min = 0, max = 256)
    @LangKey("ttalarm.config.howlerAlarmRange")
    public static int howlerAlarmRange = 64;

    @RangeInt(min = 0, max = 2000)
    @LangKey("ttalarm.config.alarmPause")
    public static int alarmPause = 0;

    @SubscribeEvent
    public static void onConfigChanged(final ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(TTAlarm.MODID)) {
            ConfigManager.sync(TTAlarm.MODID, Config.Type.INSTANCE);
        }
    }
}
