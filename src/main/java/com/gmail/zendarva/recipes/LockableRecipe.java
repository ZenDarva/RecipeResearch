package com.gmail.zendarva.recipes;

import com.gmail.zendarva.domain.Research;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.registries.IForgeRegistryEntry;

/**
 * Created by James on 3/31/2018.
 */
public class LockableRecipe extends IForgeRegistryEntry.Impl<IRecipe> implements IRecipe {


    private final IRecipe recipe;

    public LockableRecipe(IRecipe recipe, Research lockedBehind) {
        this.recipe = recipe;
        this.setRegistryName("reciperesearch", recipe.getRegistryName().getResourcePath());
    }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return recipe.getIngredients();
    }

    @Override
    public boolean matches(InventoryCrafting inv, World world) {
        return recipe.matches(inv,world);
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inv) {
        //return null;
        return recipe.getCraftingResult(inv);
    }

    @Override
    public boolean canFit(int width, int height) {
        //return false;
        return recipe.canFit(width,height);
    }

    @Override
    public ItemStack getRecipeOutput() {
        return recipe.getRecipeOutput();
    }
}
