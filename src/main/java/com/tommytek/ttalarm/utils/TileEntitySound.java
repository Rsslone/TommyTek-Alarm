package com.tommytek.ttalarm.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class TileEntitySound {
    private ISound sound;

    public TileEntitySound() { }

    public void playAlarm(double x, double y, double z, String name, float range) {
        ResourceLocation loc = new ResourceLocation(name);
        sound = new HowlerAlarmSound(loc, (float) x, (float) y, (float) z, range);
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    }

    public void stopAlarm() {
        if (sound != null) {
            Minecraft.getMinecraft().getSoundHandler().stopSound(sound);
            sound = null;
        }
    }

    public boolean isPlaying() {
        return sound != null && Minecraft.getMinecraft().getSoundHandler().isSoundPlaying(sound);
    }
}
