package com.gmail.zendarva.jei;

import com.gmail.zendarva.recipes.LockableRecipe;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.wrapper.IShapedCraftingRecipeWrapper;
import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Arrays;
import java.util.stream.Collectors;

/**
 * Created by James on 4/1/2018.
 */
public class LockableWrapper implements IShapedCraftingRecipeWrapper {
    private final LockableRecipe recipe;

    @Override
    public int getWidth() {
        return 3;
    }

    @Override
    public int getHeight() {
        return 3;
    }

    @Override
    public void getIngredients(IIngredients iIngredients) {
        iIngredients.setOutput(ItemStack.class, recipe.getRecipeOutput());
        iIngredients.setInputs(ItemStack.class,recipe.getIngredients().stream().map(Ingredient::getMatchingStacks).map(Arrays::asList).collect(Collectors.toList()));
    }

    public LockableWrapper(LockableRecipe recipe) {
        this.recipe = recipe;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void drawInfo(Minecraft minecraft, int recipeWidth, int recipeHeight, int mouseX, int mouseY) {
        minecraft.currentScreen.drawHoveringText("Restricted!",-7,70);
        Item item = Item.getByNameOrId(recipe.lockedBehind.itemToScan);
        ItemStack stack = new ItemStack(item);
        minecraft.currentScreen.drawHoveringText("Research "+ stack.getDisplayName(),-7,90 );
    }
}
