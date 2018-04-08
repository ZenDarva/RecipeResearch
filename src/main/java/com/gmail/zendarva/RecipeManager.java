package com.gmail.zendarva;

import com.gmail.zendarva.domain.Research;
import com.gmail.zendarva.recipes.LockableRecipe;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.registries.GameData;
import net.minecraftforge.registries.RegistryManager;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by James on 3/31/2018.
 */
public class RecipeManager {
    public void applyRestrictions(){
        RecipeResearch.configManager.researchList.forEach(this::applyResearch);
    }

    public static List<LockableRecipe> lockedRecipes = new LinkedList<>();


    private void applyResearch(Research research){
        List<IRecipe> recipesToWrap = new LinkedList<>();

        for (String controlledRecipe : research.controlledRecipes) {
            IRecipe target = ForgeRegistries.RECIPES.getValue(new ResourceLocation(controlledRecipe));
            if (target == null){
                RecipeResearch.logger.error("Attempting to modify recipe {}, which does not exist.", controlledRecipe);
                continue;
            }
            recipesToWrap.add(target);
        }

        for (IRecipe recipe : recipesToWrap) {
            RegistryManager.ACTIVE.getRegistry(GameData.RECIPES).remove(recipe.getRegistryName());
            LockableRecipe newRecipe = new LockableRecipe(recipe, research);
            lockedRecipes.add(newRecipe);
            ForgeRegistries.RECIPES.register(newRecipe);
        }

    }

    public static Research getResearchForItem(ItemStack target) {
        for (Research research : RecipeResearch.configManager.researchList) {
            Item item = Item.getByNameOrId(research.itemToScan);
            if (item == null)
                return null;
            ItemStack stack = new ItemStack(item);
            if (stack.isItemEqual(target))
                return research;
        }
        return null;
    }

    public static ItemStack getStackForName(String name) {
        Item item = Item.getByNameOrId(name);
        if (item == null)
            return ItemStack.EMPTY;
        return new ItemStack(item);
    }

    public static Research getResearchByName(String myResearch) {
        for (Research research : RecipeResearch.configManager.researchList) {
            if (research.researchName.equals(myResearch))
                return research;
        }
        return null;
    }
}
