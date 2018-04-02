package com.gmail.zendarva.proxy;

import com.gmail.zendarva.ModBlocks;
import com.gmail.zendarva.block.Scanner;
import com.gmail.zendarva.command.LearnedCommand;
import com.gmail.zendarva.handlers.GuiHandler;
import com.gmail.zendarva.item.ResearchItem;
import net.minecraft.block.Block;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import static com.gmail.zendarva.RecipeResearch.instance;

/**
 * Created by James on 4/1/2018.
 */
@Mod.EventBusSubscriber
public class CommonProxy {


    public void preInit(FMLPreInitializationEvent event){

    }

    public void init(FMLInitializationEvent event){
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());
    }

    public void postInit(FMLPostInitializationEvent event){

    }


    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {

        event.getRegistry().register(new Scanner());


    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.scanner).setRegistryName(ModBlocks.scanner.getRegistryName()));
        event.getRegistry().register(new ResearchItem());
    }

    public EntityPlayer getPlayer(){
        return null;
    }
}
