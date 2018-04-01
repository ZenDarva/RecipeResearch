package com.gmail.zendarva;

import jdk.nashorn.internal.runtime.regexp.joni.Config;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by James on 3/31/2018.
 */
@Mod( modid = RecipeResearch.MODID, version = RecipeResearch.VERSION)
public class RecipeResearch {
    public static final String MODID = "reciperesearch";
    public static final String VERSION = "1.0";
    public static Logger logger;
    public static ConfigManager configManager;

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        System.out.println("path:" + event.getSuggestedConfigurationFile().getParentFile());
        logger = event.getModLog();
        configManager = new ConfigManager(event.getSuggestedConfigurationFile().getParentFile());

        RecipeManager manager = new RecipeManager();
        manager.applyRestrictions();
        RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).getEntries().stream().forEach(f->System.out.println(f.getKey()));
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }


    private void printData(String stage){
        System.out.println("Active in stage: " + stage);
        RegistryManager.ACTIVE.takeSnapshot(false).keySet().stream().forEach(f->System.out.println(f));
        System.out.println("Frozen in stage: " + stage);
        RegistryManager.FROZEN.takeSnapshot(false).keySet().stream().forEach(f->System.out.println(f));
        System.out.println("Vanilla in stage: " + stage);
        RegistryManager.VANILLA.takeSnapshot(false).keySet().stream().forEach(f->System.out.println(f));
    }
}
