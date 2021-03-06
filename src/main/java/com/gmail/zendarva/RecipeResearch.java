package com.gmail.zendarva;

import com.gmail.zendarva.capabilities.ILearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipes;
import com.gmail.zendarva.capabilities.LearnedRecipesStorage;
import com.gmail.zendarva.command.LearnedCommand;
import com.gmail.zendarva.handlers.CapabilityHandler;
import com.gmail.zendarva.proxy.CommonProxy;
import com.mojang.authlib.GameProfile;
import net.minecraft.command.ServerCommandManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.util.FakePlayer;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.Logger;

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


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        recipeManager.applyRestrictions();
        proxy.init(event);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("path:" + event.getSuggestedConfigurationFile().getParentFile());
        logger = event.getModLog();
        configManager = new ConfigManager(event.getSuggestedConfigurationFile().getParentFile());

        recipeManager = new RecipeManager();

//        RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).getEntries().stream().forEach(f->System.out.println(f.getKey()));

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
