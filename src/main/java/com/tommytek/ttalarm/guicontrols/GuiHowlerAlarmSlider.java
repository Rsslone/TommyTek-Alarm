package com.tommytek.ttalarm.guicontrols;

import com.tommytek.ttalarm.TTAlarm;
import com.tommytek.ttalarm.TTAlarmConfig;
import com.tommytek.ttalarm.network.NetworkHelper;
import com.tommytek.ttalarm.tileentities.TileEntityHowlerAlarm;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHowlerAlarmSlider extends GuiButton {
    private static final ResourceLocation TEXTURE = new ResourceLocation(TTAlarm.MODID, "textures/gui/gui_howler_alarm.png");

    public float sliderValue;
    public boolean dragging;
    private static final int MIN_VALUE = 0;
    private int maxValue;
    private int step = 8;
    private TileEntityHowlerAlarm alarm;

    public GuiHowlerAlarmSlider(int id, int x, int y, TileEntityHowlerAlarm alarm) {
        super(id, x, y, 107, 16, "");
        this.alarm = alarm;
        dragging = false;
        maxValue = TTAlarmConfig.maxAlarmRange;
        int currentRange = alarm.getRange();
        if (currentRange > maxValue)
            currentRange = maxValue;
        sliderValue = ((float) currentRange - MIN_VALUE) / (maxValue - MIN_VALUE);
        String ds = I18n.format("msg.ec.HowlerAlarmSoundRange", getNormalizedValue());
        if (ds.equals("msg.ec.HowlerAlarmSoundRange"))
            ds = com.tommytek.ttalarm.utils.TTAlarmLangHelper.translate("msg.ec.HowlerAlarmSoundRange").replace("%s", Integer.toString(getNormalizedValue()));
        displayString = ds;
    }

    private int getNormalizedValue() {
        return (MIN_VALUE + (int) Math.floor((maxValue - MIN_VALUE) * sliderValue)) / step * step;
    }

    private void setSliderPos(int targetX) {
        sliderValue = (float) (targetX - (x + 4)) / (float) (width - 8);

        if (sliderValue < 0.0F)
            sliderValue = 0.0F;

        if (sliderValue > 1.0F)
            sliderValue = 1.0F;

        int newValue = getNormalizedValue();
        if (alarm.getRange() != newValue) {
            NetworkHelper.updateSeverTileEntity(alarm.getPos(), 2, newValue);
            alarm.setRange(newValue);
        }
        String ds2 = I18n.format("msg.ec.HowlerAlarmSoundRange", newValue);
        if (ds2.equals("msg.ec.HowlerAlarmSoundRange"))
            ds2 = com.tommytek.ttalarm.utils.TTAlarmLangHelper.translate("msg.ec.HowlerAlarmSoundRange").replace("%s", Integer.toString(newValue));
        displayString = ds2;
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (!visible)
            return;
        mc.getTextureManager().bindTexture(TEXTURE);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        if (dragging)
            setSliderPos(mouseX);

        drawTexturedModalRect(x + (int) (sliderValue * (width - 8)), y, 131, 0, 8, 16);
        mc.fontRenderer.drawString(displayString, x, y - 12, 0x404040);
    }

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        if (!super.mousePressed(mc, mouseX, mouseY))
            return false;
        setSliderPos(mouseX);
        dragging = true;
        return true;
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        super.mouseReleased(mouseX, mouseY);
        dragging = false;
    }
}
