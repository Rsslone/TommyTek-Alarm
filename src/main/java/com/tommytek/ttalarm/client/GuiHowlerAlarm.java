package com.tommytek.ttalarm.client;

import java.util.ArrayList;
import java.util.List;

import com.tommytek.ttalarm.TTAlarm;
import com.tommytek.ttalarm.guicontrols.GuiHowlerAlarmListBox;
import com.tommytek.ttalarm.guicontrols.GuiHowlerAlarmSlider;
import com.tommytek.ttalarm.tileentities.TileEntityHowlerAlarm;
import com.zuxelus.zlib.gui.GuiBase;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class GuiHowlerAlarm extends GuiBase {
    private TileEntityHowlerAlarm alarm;
    private GuiHowlerAlarmSlider slider;
    private GuiHowlerAlarmListBox listBox;
    private boolean isBig;

    private int titleX;

    public GuiHowlerAlarm(TileEntityHowlerAlarm alarm, boolean isBig) {
        super("tile.howler_alarm.name", 131, isBig ? 236 : 136, isBig ?
            TTAlarm.MODID + ":textures/gui/gui_howler_alarm_big.png" :
            TTAlarm.MODID + ":textures/gui/gui_howler_alarm.png");
        this.alarm = alarm;
        this.isBig = isBig;
    }

    @Override
    public void initGui() {
        super.initGui();
        titleX = (xSize - fontRenderer.getStringWidth(name)) / 2;
        slider = new GuiHowlerAlarmSlider(3, guiLeft + 12, guiTop + 33, alarm);
        List<String> items = new ArrayList<String>(TTAlarm.instance.availableAlarms);

        listBox = new GuiHowlerAlarmListBox(4, guiLeft + 13, guiTop + 63, 105, isBig ? 165 : 65, items, alarm);
        addButton(slider);
        addButton(listBox);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        // name is already translated by GuiBase constructor via I18n.format
        // If translation failed (raw key returned), use our fallback helper
        String title = name;
        if (title.equals("tile.howler_alarm.name"))
            title = com.tommytek.ttalarm.utils.TTAlarmLangHelper.translate("tile.howler_alarm.name");
        fontRenderer.drawString(title, titleX, 6, 0x404040);
        String soundLabel = I18n.format("msg.ec.HowlerAlarmSound");
        if (soundLabel.equals("msg.ec.HowlerAlarmSound"))
            soundLabel = com.tommytek.ttalarm.utils.TTAlarmLangHelper.translate("msg.ec.HowlerAlarmSound");
        fontRenderer.drawString(soundLabel, 12, 53, 0x404040);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int which) {
        super.mouseReleased(mouseX, mouseY, which);
        if ((which == 0 || which == 1) && (slider.dragging || listBox.dragging)) {
            slider.mouseReleased(mouseX, mouseY);
            listBox.mouseReleased(mouseX, mouseY);
        }
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws java.io.IOException {
        super.keyTyped(typedChar, keyCode);
        listBox.keyPressed(typedChar, keyCode);
    }

    @Override
    public void handleMouseInput() throws java.io.IOException {
        super.handleMouseInput();
        int wheel = org.lwjgl.input.Mouse.getEventDWheel();
        if (wheel != 0) {
            int i = org.lwjgl.input.Mouse.getEventX() * this.width / this.mc.displayWidth;
            int j = this.height - org.lwjgl.input.Mouse.getEventY() * this.height / this.mc.displayHeight - 1;
            listBox.mouseScrolled(i, j, wheel);
        }
    }
}
