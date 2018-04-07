package com.gmail.zendarva.domain;

import net.minecraft.client.Minecraft;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by James on 3/31/2018.
 */
public class Research {
    public String researchName;
    public List<LanguageData> displayName;
    public List<String> controlledRecipes;
    public String itemToScan;
    public int requiredPower;
    public String commandToRun= null;
    public int successChance = 100;
    public List<String> requiredResearch;

    public String getDiscoveryString(){
        return researchName+".discovery.string";
    }
}
