package com.gmail.zendarva.proxy;

import com.gmail.zendarva.ModBlocks;
import com.gmail.zendarva.RecipeResearch;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Created by James on 4/1/2018.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
    }

    @Override
    public void init(FMLInitializationEvent event) {
        super.init(event);

    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event){
        ModBlocks.scanner.initModel();
        ModBlocks.researchItem.initModel();
    }

    @Override
    public EntityPlayer getPlayer() {
        EntityPlayer player = Minecraft.getMinecraft().player;
        return player;
    }
}
