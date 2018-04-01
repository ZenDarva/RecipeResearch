package com.gmail.zendarva;

import com.gmail.zendarva.block.Scanner;
import com.gmail.zendarva.item.ResearchItem;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * Created by James on 4/1/2018.
 */
public class ModBlocks {

    @GameRegistry.ObjectHolder("reciperesearch:scanner")
    public static Scanner scanner;

    @GameRegistry.ObjectHolder("reciperesearch:research")
    public static ResearchItem researchItem;
}
