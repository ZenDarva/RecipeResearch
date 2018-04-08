package com.gmail.zendarva.proxy;

import com.gmail.zendarva.ModBlocks;
import com.gmail.zendarva.RecipeResearch;
import com.gmail.zendarva.client.LanguageSource;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.IResourceManagerReloadListener;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by James on 4/1/2018.
 */
@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @SuppressWarnings("MethodCallSideOnly")
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        ((SimpleReloadableResourceManager) Minecraft.getMinecraft().getResourceManager()).registerReloadListener((IResourceManagerReloadListener) RecipeResearch.languageSource);
        //In theory, this could happen automatically.  In practice, it doesn't.  So we'll do it manually.  sorry for the
        //extra 2 seconds of load time.
        Minecraft.getMinecraft().getLanguageManager().onResourceManagerReload(Minecraft.getMinecraft().getResourceManager());
    }

    @SuppressWarnings("MethodCallSideOnly")
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
        return Minecraft.getMinecraft().player;
    }
}
