package com.gmail.zendarva.domain;

import net.minecraft.util.ResourceLocation;

import java.util.List;

/**
 * Created by James on 3/31/2018.
 */
public class Research {
    public List<String> controlledRecipes;
    public String itemToScan;
    public int requiredPower;
    public String commandToRun= null;
    public int successChance = 100;
}
