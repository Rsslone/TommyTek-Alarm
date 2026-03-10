package com.tommytek.ttalarm.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import com.tommytek.ttalarm.TTAlarm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TTAlarmLangHelper {
    private static Map<String, String> map = new HashMap<>();

    public static void load() {
        map.clear();
        // try resource manager first
        try {
            IResource res;
            try {
                res = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(TTAlarm.MODID, "lang/en_us.lang"));
            } catch (Throwable t2) {
                res = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(TTAlarm.MODID, "lang/en_US.lang"));
            }
            try (BufferedReader br = new BufferedReader(new InputStreamReader(res.getInputStream()))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.contains("=") || line.startsWith("#"))
                        continue;
                    String[] parts = line.split("=", 2);
                    map.put(parts[0].trim(), parts[1].trim());
                }
            }
        } catch (Throwable t) {
            TTAlarm.logger.error("Failed to load lang file: " + t.getMessage());
        }
    }

    public static String translate(String key) {
        if (map.containsKey(key))
            return map.get(key);
        return key;
    }
}
