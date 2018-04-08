package com.gmail.zendarva;

import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipesStorage;
import com.gmail.zendarva.client.LanguageSource;
import com.gmail.zendarva.command.LearnedCommand;
import com.gmail.zendarva.handlers.CapabilityHandler;
import com.gmail.zendarva.proxy.CommonProxy;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.UUID;

/**
 * Created by James on 3/31/2018.
 */
@Mod( modid = RecipeResearch.MODID, version = RecipeResearch.VERSION)
public class RecipeResearch {
    public static final String MODID = "reciperesearch";
    public static final String VERSION = "1.0";
    public static Logger logger;
    public static ConfigManager configManager;
    public static RecipeManager recipeManager;
    public static EntityPlayer fakePlayer;

    @Mod.Instance
    public static RecipeResearch instance;

    @SidedProxy(clientSide="com.gmail.zendarva.proxy.ClientProxy",serverSide="com.gmail.zendarva.proxy.CommonProxy")
    public static CommonProxy proxy;

    public static Object languageSource;


    public RecipeResearch() {
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
            //noinspection MethodCallSideOnly
            registerLanguageHandler();
        }
    }
    @SideOnly(Side.CLIENT)
    private void registerLanguageHandler(){
        languageSource = new LanguageSource();
        ((List<IResourcePack>) ReflectionHelper.getPrivateValue(Minecraft.class, Minecraft.getMinecraft(), "field_110449_ao", "defaultResourcePacks")).add((IResourcePack) languageSource);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        recipeManager.applyRestrictions();
        proxy.init(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        configManager = new ConfigManager(event.getSuggestedConfigurationFile().getParentFile());
        recipeManager = new RecipeManager();

        MinecraftForge.EVENT_BUS.register(new CapabilityHandler());

        CapabilityManager.INSTANCE.register(ILearnedRecipes.class, new LearnedRecipesStorage(), LearnedRecipes.class);

        proxy.preInit(event);

    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }


    @Mod.EventHandler
    public void serverStart(FMLServerStartingEvent event) {
        MinecraftServer server = event.getServer();
        ((ServerCommandManager) server.getCommandManager()).registerCommand(new LearnedCommand());
        GameProfile profile = new GameProfile(UUID.fromString("4408bd02-361f-11e8-b467-0ed5f89f718b"), "[reciperesearch]");
        fakePlayer = new CommandableFakePlayer(server.getWorld(0),profile);
    }

}
