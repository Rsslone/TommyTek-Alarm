package com.tommytek.ttalarm.utils;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class HowlerAlarmSound extends PositionedSound {

    public HowlerAlarmSound(ResourceLocation soundLocation, float x, float y, float z, float range) {
        super(soundLocation, SoundCategory.MASTER);
        this.xPosF = x;
        this.yPosF = y;
        this.zPosF = z;
        // Minecraft's default attenuation distance is 16 blocks.
        // Scale volume so the sound is still audible at the configured range.
        this.volume = Math.max(1.0F, range / 16.0F);
        this.pitch = 1.0F;
        this.repeat = false;
        this.repeatDelay = 0;
        this.attenuationType = ISound.AttenuationType.LINEAR;
    }
}
