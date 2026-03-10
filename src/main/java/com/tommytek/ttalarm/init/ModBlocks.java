package com.tommytek.ttalarm.init;

import com.tommytek.ttalarm.TTAlarm;
import com.tommytek.ttalarm.blocks.HowlerAlarm;
import com.tommytek.ttalarm.tileentities.TileEntityHowlerAlarm;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

@EventBusSubscriber
public class ModBlocks {
    public static Block blockHowlerAlarm;

    @SubscribeEvent
    public static void onBlockRegistry(Register<Block> event) {
        blockHowlerAlarm = register(event, new HowlerAlarm(), "howler_alarm");
    }

    @SubscribeEvent
    public static void onItemRegistry(Register<Item> event) {
        event.getRegistry().register(new ItemBlock(blockHowlerAlarm).setRegistryName("howler_alarm"));
    }

    public static Block register(Register<Block> event, Block block, String name) {
        block.setRegistryName(name);
        block.setTranslationKey(name);
        event.getRegistry().register(block);
        return block;
    }

    @SubscribeEvent
    public static void onModelRegister(ModelRegistryEvent event) {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(blockHowlerAlarm), 0, new ModelResourceLocation(TTAlarm.MODID + ":howler_alarm", "inventory"));
    }

    @SuppressWarnings("deprecation")
    public static void registerTileEntities() {
        GameRegistry.registerTileEntity(TileEntityHowlerAlarm.class, TTAlarm.MODID + ":howler_alarm");
    }
}
