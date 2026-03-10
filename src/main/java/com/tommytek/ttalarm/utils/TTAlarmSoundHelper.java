package com.tommytek.ttalarm.utils;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.tommytek.ttalarm.TTAlarm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TTAlarmSoundHelper {
    private static final Gson gson = new Gson();

    public static void importSound() {
        if (Minecraft.getMinecraft() == null)
            return;
        try {
            List<String> loaded = new ArrayList<>();
            List<IResource> list = Minecraft.getMinecraft().getResourceManager().getAllResources(new ResourceLocation(TTAlarm.MODID, "sounds.json"));
            for (int i = list.size() - 1; i >= 0; --i) {
                IResource iresource = list.get(i);
                Map<String, Object> map = gson.fromJson(new InputStreamReader(iresource.getInputStream()), Map.class);
                for (String key : map.keySet()) {
                    if (key.startsWith("alarm-"))
                        loaded.add(key.replace("alarm-", ""));
                }
            }
            // Only update the list once the load succeeds, so "default" is never lost on failure
            TTAlarm.instance.availableAlarms.clear();
            TTAlarm.instance.availableAlarms.addAll(loaded);
        } catch (Throwable ex) {
            TTAlarm.logger.error("Failed to load sounds.json: " + ex.getMessage());
        }
    }
}
