package com.gmail.zendarva.domain;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.List;
import java.util.Map;

/**
 * Created by James on 3/31/2018.
 */
public class Research {
    public String researchName;
    public Map<String, String> localizedDisplayName;
    public List<String> controlledRecipes;
    public List<String> itemToScan;
    public int requiredPower;
    public String commandToRun= null;
    public int successChance = 100;
    public List<String> requiredResearch;


    public String getDisplayName(){
        Minecraft.getMinecraft().gameSettings.language
    }
}
